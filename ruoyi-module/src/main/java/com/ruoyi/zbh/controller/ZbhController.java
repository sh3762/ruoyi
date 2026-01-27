package com.ruoyi.zbh.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.zbh.domain.NonGenuineSoftware;
import com.ruoyi.zbh.domain.SystemStatus;
import com.ruoyi.zbh.domain.YunLeiServer;
import com.ruoyi.zbh.service.IZbhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/zbh")
public class ZbhController extends BaseController {

    @Autowired
    private IZbhService zbhService;

    @GetMapping("/status")
    public AjaxResult getStatus() {
        SystemStatus status = zbhService.getSystemStatus();
        return AjaxResult.success(status);
    }

    @GetMapping("/yunlei")
    public TableDataInfo getYunLei() {
        List<YunLeiServer> list = zbhService.getYunLeiServers();
        return getDataTable(list);
    }

    @GetMapping("/software")
    public TableDataInfo getSoftware() {
        List<NonGenuineSoftware> list = zbhService.getNonGenuineSoftwareList();
        return getDataTable(list);
    }
}
