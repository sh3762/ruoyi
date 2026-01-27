package com.ruoyi.zbh.mapper;

import com.ruoyi.zbh.domain.NonGenuineSoftware;
import com.ruoyi.zbh.domain.YunLeiServer;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ZbhMapper {
    // Queries for SystemStatus
    Map<String, Object> selectBigDataIndex();
    Map<String, Object> selectCollectorOffline();
    List<Map<String, Object>> selectErrorMessages();
    Map<String, Object> selectNetworkWarningAndDataLake();
    Map<String, Object> selectNonGenuineStats();
    int selectSoftwareCount();

    List<YunLeiServer> selectYunLeiServers();
    
    void executeSp360Zbh();
    List<NonGenuineSoftware> selectNonGenuineSoftwareList();
}
