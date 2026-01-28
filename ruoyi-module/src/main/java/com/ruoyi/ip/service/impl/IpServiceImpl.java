package com.ruoyi.ip.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.ip.domain.IpInfo;
import com.ruoyi.ip.mapper.IpMapper;
import com.ruoyi.ip.service.IIpService;

/**
 * IP管理 Service 实现
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class IpServiceImpl implements IIpService
{
    @Autowired
    private IpMapper ipMapper;

    /**
     * 查询IP列表
     */
    @Override
    public List<IpInfo> selectIpList(IpInfo ipInfo)
    {
        return ipMapper.selectIpList(ipInfo);
    }

    /**
     * 更新备注
     */
    @Override
    public int updateIpNote(String ip, String note)
    {
        return ipMapper.updateIpNote(ip, note);
    }

    /**
     * 删除 IP/MAC 记录
     */
    @Override
    public int deleteIp(IpInfo ipInfo)
    {
        int rows = 0;
        if (ipInfo.getIp() != null && ipInfo.getMac() != null) {
            rows += ipMapper.deleteArp(ipInfo.getIp(), ipInfo.getMac());
        }
        if (ipInfo.getSwip() != null && ipInfo.getMac() != null) {
            rows += ipMapper.deleteMac(ipInfo.getSwip(), ipInfo.getMac());
        }
        return rows;
    }

    /**
     * 获取IP统计信息
     */
    @Override
    public List<Map<String, Object>> selectIpStats()
    {
        return ipMapper.selectIpStats();
    }
}
