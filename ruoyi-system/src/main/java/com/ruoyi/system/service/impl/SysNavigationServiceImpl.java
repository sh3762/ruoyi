package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.mapper.SysNavigationMapper;
import com.ruoyi.system.domain.SysNavigation;
import com.ruoyi.system.service.ISysNavigationService;

/**
 * 综合导航Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-22
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class SysNavigationServiceImpl implements ISysNavigationService 
{
    @Autowired
    private SysNavigationMapper sysNavigationMapper;

    /**
     * 查询综合导航
     * 
     * @param id 综合导航主键
     * @return 综合导航
     */
    @Override
    public SysNavigation selectSysNavigationById(Long id)
    {
        return sysNavigationMapper.selectSysNavigationById(id);
    }

    /**
     * 查询综合导航列表
     * 
     * @param sysNavigation 综合导航
     * @return 综合导航
     */
    @Override
    public List<SysNavigation> selectSysNavigationList(SysNavigation sysNavigation)
    {
        return sysNavigationMapper.selectSysNavigationList(sysNavigation);
    }

    /**
     * 新增综合导航
     * 
     * @param sysNavigation 综合导航
     * @return 结果
     */
    @Override
    public int insertSysNavigation(SysNavigation sysNavigation)
    {
        sysNavigation.setCreateTime(DateUtils.getNowDate());
        return sysNavigationMapper.insertSysNavigation(sysNavigation);
    }

    /**
     * 修改综合导航
     * 
     * @param sysNavigation 综合导航
     * @return 结果
     */
    @Override
    public int updateSysNavigation(SysNavigation sysNavigation)
    {
        sysNavigation.setUpdateTime(DateUtils.getNowDate());
        return sysNavigationMapper.updateSysNavigation(sysNavigation);
    }

    /**
     * 批量删除综合导航
     * 
     * @param ids 需要删除的综合导航主键
     * @return 结果
     */
    @Override
    public int deleteSysNavigationByIds(Long[] ids)
    {
        return sysNavigationMapper.deleteSysNavigationByIds(ids);
    }

    /**
     * 删除综合导航信息
     * 
     * @param id 综合导航主键
     * @return 结果
     */
    @Override
    public int deleteSysNavigationById(Long id)
    {
        return sysNavigationMapper.deleteSysNavigationById(id);
    }
}
