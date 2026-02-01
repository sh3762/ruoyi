package com.ruoyi.kq.mapper;

import com.ruoyi.kq.domain.FaceHkLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * 考勤模块Mapper接口
 * 
 * @author ruoyi
 */
@Mapper
public interface KqMapper 
{
    /**
     * 查询海康刷脸全部记录
     * 
     * @return 结果
     */
    @Select("SELECT name, event_time, created_at FROM face_hk_log ORDER BY event_time DESC")
    public List<FaceHkLog> selectFaceHkLogList();

    /**
     * 查询海康刷脸记录（自定义SQL支持复杂逻辑）
     * 
     * @param sql 动态SQL
     * @return 结果
     */
    @Select("${sql}")
    public List<Map<String, Object>> selectFaceHkLogBySql(String sql);
}
