package com.ruoyi.ip.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.ip.domain.IpInfo;
import com.ruoyi.ip.service.IIpService;

/**
 * IP管理 Controller
 */
@RestController
@RequestMapping("/module/ip")
public class IpController extends BaseController
{
    @Autowired
    private IIpService ipService;

    /**
     * 查询IP列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:ip:list')")
    @GetMapping("/list")
    public TableDataInfo list(IpInfo ipInfo)
    {
        // PageHelper 在 SQL Server 2000 上可能存在兼容性问题
        // startPage(); 
        try {
            List<IpInfo> list = ipService.selectIpList(ipInfo);
            return getDataTable(list);
        } catch (Exception e) {
            logger.error("查询IP列表失败", e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(500);
            rspData.setMsg("查询失败：" + e.getMessage());
            return rspData;
        }
    }

    /**
     * 导出IP列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:ip:export')")
    @Log(title = "IP管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(IpInfo ipInfo)
    {
        List<IpInfo> list = ipService.selectIpList(ipInfo);
        ExcelUtil<IpInfo> util = new ExcelUtil<IpInfo>(IpInfo.class);
        return util.exportExcel(list, "IP数据");
    }

    /**
     * 获取统计信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:ip:list')")
    @GetMapping("/stats")
    public AjaxResult stats()
    {
        List<Map<String, Object>> stats = ipService.selectIpStats();
        return AjaxResult.success(stats);
    }

    /**
     * 修改备注
     */
    @PreAuthorize("@ss.hasPermi('monitor:ip:edit')")
    @Log(title = "IP管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IpInfo ipInfo)
    {
        return toAjax(ipService.updateIpNote(ipInfo.getIp(), ipInfo.getNote()));
    }

    /**
     * 删除冗余
     */
    @PreAuthorize("@ss.hasPermi('monitor:ip:remove')")
    @Log(title = "IP管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody IpInfo ipInfo)
    {
        return toAjax(ipService.deleteIp(ipInfo));
    }
}
