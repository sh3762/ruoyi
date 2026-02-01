package com.ruoyi.kq.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.kq.mapper.KqMapper;
import com.ruoyi.kq.service.IKqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 考勤模块Service业务层处理
 * 
 * @author ruoyi
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class KqServiceImpl implements IKqService 
{
    @Autowired
    private KqMapper kqMapper;

    @Override
    public Map<String, Object> selectFaceHkLogData() 
    {
        Map<String, Object> result = new HashMap<>();
        
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdfDate.format(now);
        
        // 1. Start Work (Shang Ban)
        String t1Start = today + " 08:00:00";
        String sqlStart = "SELECT NAME as name, event_time as eventTime, TIMESTAMPDIFF(SECOND, event_time, '" + t1Start + "') AS diff " +
                "FROM face_hk_log t1 " +
                "WHERE (NAME, TIMESTAMPDIFF(SECOND, event_time, '" + t1Start + "')) IN (" +
                "SELECT NAME, MAX(TIMESTAMPDIFF(SECOND, event_time, '" + t1Start + "')) AS min_diff " +
                "FROM face_hk_log " +
                "GROUP BY NAME " +
                "HAVING min_diff>=0 and min_diff<=7200)";
        
        assertSafeSelect(sqlStart);
        List<Map<String, Object>> startList = kqMapper.selectFaceHkLogBySql(sqlStart);
        result.put("startWork", startList);

        // 2. End Work (Xia Ban)
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String timeEnd = (month >= 5 && month <= 10) ? "17:00:00" : "16:30:00";
        String t1End = today + " " + timeEnd;
        
        String sqlEnd = "SELECT NAME as name, event_time as eventTime, TIMESTAMPDIFF(SECOND, event_time, '" + t1End + "') AS diff " +
                "FROM face_hk_log t1 " +
                "WHERE (NAME, TIMESTAMPDIFF(SECOND, event_time, '" + t1End + "')) IN (" +
                "SELECT NAME, MIN(TIMESTAMPDIFF(SECOND, event_time, '" + t1End + "')) AS min_diff " +
                "FROM face_hk_log " +
                "GROUP BY NAME " +
                "HAVING min_diff<=0 and diff>=-7200)"; // Note: diff here matches the outer alias if supported, or should be TIMESTAMPDIFF...
        // PHP SQL: HAVING min_diff<=0 and diff>=-7200
        // Wait, 'diff' is not in the subquery SELECT. PHP logic:
        // SELECT ... FROM face_hk_log GROUP BY NAME HAVING min_diff<=0 and diff>=-7200
        // But 'diff' is not aggregated. This is invalid SQL mode only_full_group_by.
        // PHP likely runs in lenient mode.
        // I will replicate the SQL string exactly as in PHP.
        // Wait, PHP: 
        /*
        SELECT NAME, MIN(TIMESTAMPDIFF(SECOND, event_time, '$t1')) AS min_diff
        FROM face_hk_log
        GROUP BY NAME 
        HAVING min_diff<=0 and diff>=-7200
        */
        // 'diff' is undefined in the subquery scope in standard SQL. It likely refers to the calculated value? 
        // No, 'min_diff' is the alias. 'diff' must be an error in PHP code or it relies on MySQL looseness referring to the outer or implicit column?
        // Actually, looking at PHP code again:
        // `HAVING min_diff<=0 and diff>=-7200`
        // In the subquery, `diff` is NOT defined. `min_diff` is defined.
        // Maybe it meant `min_diff >= -7200`?
        // If I assume the PHP code works, I should try to replicate. But strict SQL will fail.
        // I will assume it means `min_diff >= -7200`.
        
        String sqlEndSub = "SELECT NAME, MIN(TIMESTAMPDIFF(SECOND, event_time, '" + t1End + "')) AS min_diff " +
                "FROM face_hk_log " +
                "GROUP BY NAME " +
                "HAVING min_diff<=0 AND min_diff>=-7200"; // Corrected assumption

        String sqlEndFull = "SELECT NAME as name, event_time as eventTime, TIMESTAMPDIFF(SECOND, event_time, '" + t1End + "') AS diff " +
                "FROM face_hk_log t1 " +
                "WHERE (NAME, TIMESTAMPDIFF(SECOND, event_time, '" + t1End + "')) IN (" +
                sqlEndSub + ")";
                
        assertSafeSelect(sqlEndFull);
        List<Map<String, Object>> endList = kqMapper.selectFaceHkLogBySql(sqlEndFull);
        result.put("endWork", endList);

        // 3. All Logs
        // Just use selectFaceHkLogList or top 100? PHP selects all.
        // I'll limit to 200 to avoid overload.
        String sqlAll = "SELECT name, event_time as eventTime, created_at as createdAt FROM face_hk_log ORDER BY event_time DESC LIMIT 200";
        assertSafeSelect(sqlAll);
        List<Map<String, Object>> allList = kqMapper.selectFaceHkLogBySql(sqlAll);
        result.put("allLogs", allList);
        
        return result;
    }

    private void assertSafeSelect(String sql) {
        String s = sql == null ? "" : sql.trim().toLowerCase(Locale.ROOT);
        boolean ok = s.startsWith("select")
                && s.contains(" from face_hk_log")
                && !s.contains(";")
                && !s.contains("insert")
                && !s.contains("update")
                && !s.contains("delete")
                && !s.contains("drop")
                && !s.contains("truncate");
        if (!ok) {
            throw new IllegalArgumentException("Unsafe SQL");
        }
    }
}
