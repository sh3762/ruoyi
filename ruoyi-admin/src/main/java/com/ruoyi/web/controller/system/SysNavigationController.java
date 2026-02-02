package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysNavigation;
import com.ruoyi.system.service.ISysNavigationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 综合导航Controller
 * 
 * @author ruoyi
 * @date 2024-05-22
 */
@RestController
@RequestMapping("/system/navigation")
public class SysNavigationController extends BaseController
{
    @Autowired
    private ISysNavigationService sysNavigationService;

    /**
     * 查询综合导航列表
     */
    // @PreAuthorize("@ss.hasPermi('system:navigation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNavigation sysNavigation)
    {
        startPage();
        List<SysNavigation> list = sysNavigationService.selectSysNavigationList(sysNavigation);
        return getDataTable(list);
    }

    /**
     * 导出综合导航列表
     */
    @PreAuthorize("@ss.hasPermi('system:navigation:export')")
    @Log(title = "综合导航", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysNavigation sysNavigation)
    {
        List<SysNavigation> list = sysNavigationService.selectSysNavigationList(sysNavigation);
        ExcelUtil<SysNavigation> util = new ExcelUtil<SysNavigation>(SysNavigation.class);
        util.exportExcel(response, list, "综合导航数据");
    }

    /**
     * 获取综合导航详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:navigation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysNavigationService.selectSysNavigationById(id));
    }

    /**
     * 新增综合导航
     */
    @PreAuthorize("@ss.hasPermi('system:navigation:add')")
    @Log(title = "综合导航", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNavigation sysNavigation)
    {
        sysNavigation.setCreateBy(getUsername());
        return toAjax(sysNavigationService.insertSysNavigation(sysNavigation));
    }

    /**
     * 修改综合导航
     */
    @PreAuthorize("@ss.hasPermi('system:navigation:edit')")
    @Log(title = "综合导航", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNavigation sysNavigation)
    {
        sysNavigation.setUpdateBy(getUsername());
        return toAjax(sysNavigationService.updateSysNavigation(sysNavigation));
    }

    /**
     * 删除综合导航
     */
    @PreAuthorize("@ss.hasPermi('system:navigation:remove')")
    @Log(title = "综合导航", businessType = BusinessType.DELETE)
	@DeleteMapping(value = "/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysNavigationService.deleteSysNavigationByIds(ids));
    }
}
