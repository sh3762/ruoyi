package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysNavigation;

/**
 * 综合导航Service接口
 * 
 * @author ruoyi
 * @date 2024-05-22
 */
public interface ISysNavigationService 
{
    /**
     * 查询综合导航
     * 
     * @param id 综合导航主键
     * @return 综合导航
     */
    public SysNavigation selectSysNavigationById(Long id);

    /**
     * 查询综合导航列表
     * 
     * @param sysNavigation 综合导航
     * @return 综合导航集合
     */
    public List<SysNavigation> selectSysNavigationList(SysNavigation sysNavigation);

    /**
     * 新增综合导航
     * 
     * @param sysNavigation 综合导航
     * @return 结果
     */
    public int insertSysNavigation(SysNavigation sysNavigation);

    /**
     * 修改综合导航
     * 
     * @param sysNavigation 综合导航
     * @return 结果
     */
    public int updateSysNavigation(SysNavigation sysNavigation);

    /**
     * 批量删除综合导航
     * 
     * @param ids 需要删除的综合导航主键集合
     * @return 结果
     */
    public int deleteSysNavigationByIds(Long[] ids);

    /**
     * 删除综合导航信息
     * 
     * @param id 综合导航主键
     * @return 结果
     */
    public int deleteSysNavigationById(Long id);
}
