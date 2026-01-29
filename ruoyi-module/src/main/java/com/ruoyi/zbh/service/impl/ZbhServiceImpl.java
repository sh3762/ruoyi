package com.ruoyi.zbh.service.impl;

import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.zbh.domain.NonGenuineSoftware;
import com.ruoyi.zbh.domain.SystemStatus;
import com.ruoyi.zbh.domain.YunLeiServer;
import com.ruoyi.zbh.mapper.ZbhMapper;
import com.ruoyi.zbh.service.IZbhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@DataSource(DataSourceType.SLAVE)
public class ZbhServiceImpl implements IZbhService {

    @Autowired
    private ZbhMapper zbhMapper;

    @Override
    public SystemStatus getSystemStatus() {
        SystemStatus status = new SystemStatus();

        // 1. Big Data Index
        Map<String, Object> res1 = zbhMapper.selectBigDataIndex();
        if (res1 != null) status.setBigDataIndex((String) res1.get("info"));

        // 2. Collector Offline
        Map<String, Object> res2 = zbhMapper.selectCollectorOffline();
        if (res2 != null) status.setCollectorOffline((String) res2.get("info"));

        // 3. Error Messages
        List<Map<String, Object>> res3 = zbhMapper.selectErrorMessagesV2();
        StringBuilder errMsg = new StringBuilder();
        if (res3 != null) {
            for (Map<String, Object> row : res3) {
                errMsg.append(row.get("info")).append("<br>");
            }
        }
        status.setErrorMessages(errMsg.toString());

        // 4. Network Warning & Data Lake
        Map<String, Object> res4 = zbhMapper.selectNetworkWarningAndDataLake();
        if (res4 != null) {
            status.setNetworkWarning((String) res4.get("dwg_info"));
            
            String lakeTm = (String) res4.get("lake_tm");
            Object lakeNumObj = res4.get("lake_num");
            String lakeNum = lakeNumObj != null ? String.valueOf(lakeNumObj) : "0";
            
            Object lakeDtObj = res4.get("lake_dt");
            Integer lakeDt = lakeDtObj instanceof Integer ? (Integer) lakeDtObj : 0;
            status.setDataLakeDiff(lakeDt);
            
            String dataLake = lakeTm + ": 发送 " + lakeNum + " 点";
            status.setDataLakeStatus(dataLake);
        }

        // 5. Stats
        Map<String, Object> res5 = zbhMapper.selectNonGenuineStats();
        if (res5 != null) {
            status.setPcCount((Integer) res5.get("pc_count"));
            status.setUpdateTime((String) res5.get("update_time"));
            status.setCheckTime((String) res5.get("check_time"));
        }

        // 6. Software Count
        status.setSoftwareCount(zbhMapper.selectSoftwareCount());

        return status;
    }

    @Override
    public List<YunLeiServer> getYunLeiServers() {
        return zbhMapper.selectYunLeiServers();
    }

    @Override
    public List<NonGenuineSoftware> getNonGenuineSoftwareList() {
        // zbhMapper.executeSp360Zbh(); // 暂时注释，避免频繁调用存储过程影响数据库
        return zbhMapper.selectNonGenuineSoftwareList();
    }
}
