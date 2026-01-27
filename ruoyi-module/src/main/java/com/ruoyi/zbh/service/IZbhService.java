package com.ruoyi.zbh.service;

import com.ruoyi.zbh.domain.NonGenuineSoftware;
import com.ruoyi.zbh.domain.SystemStatus;
import com.ruoyi.zbh.domain.YunLeiServer;
import java.util.List;

public interface IZbhService {
    SystemStatus getSystemStatus();
    List<YunLeiServer> getYunLeiServers();
    List<NonGenuineSoftware> getNonGenuineSoftwareList();
}
