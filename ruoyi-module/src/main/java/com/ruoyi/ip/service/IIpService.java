package com.ruoyi.ip.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.ip.domain.IpInfo;

/**
 * IP管理 Service 接口
 */
public interface IIpService 
{
    /**
     * 查询IP列表
     * 
     * @param ipInfo IP信息
     * @return IP集合
     */
    public List<IpInfo> selectIpList(IpInfo ipInfo);

    /**
     * 更新备注
     */
    public int updateIpNote(String ip, String note);

    /**
     * 删除 IP/MAC 记录
     */
    public int deleteIp(IpInfo ipInfo);

    /**
     * 获取IP统计信息
     */
    public List<Map<String, Object>> selectIpStats();
}
