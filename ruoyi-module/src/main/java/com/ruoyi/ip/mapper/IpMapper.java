package com.ruoyi.ip.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.ruoyi.ip.domain.IpInfo;

/**
 * IP管理 Mapper 接口
 */
@Mapper
public interface IpMapper
{
    /**
     * 查询IP列表
     * 
     * @param ipInfo IP信息
     * @return IP集合
     */
    @Select({
        "<script>",
        "SELECT TOP 100000 COALESCE(a.ip, i.ip) as ip, a.mac, c.swip, i.note, a.sj, COALESCE(c.vlan, a.vlan) as vlan, COALESCE(c.port, a.port) as port",
        "FROM (",
        "  SELECT a.ip, a.mac, a.sj, a.vlan, a.port",
        "  FROM hw_arp a",
        "  INNER JOIN (SELECT ip, MAX(sj) as max_sj FROM hw_arp GROUP BY ip) b ON a.ip = b.ip AND a.sj = b.max_sj",
        ") a",
        "FULL OUTER JOIN HW_IP i ON a.ip = i.ip",
        "LEFT JOIN hw_mac c ON a.mac = c.mac",
        "WHERE 1=1",
        " AND COALESCE(a.ip, i.ip) IS NOT NULL",
        " AND (",
        "   (i.note IS NOT NULL AND DATALENGTH(i.note) > 0)",
        "   OR (a.mac IS NOT NULL AND DATALENGTH(a.mac) > 0)",
        "   OR (c.swip IS NOT NULL AND DATALENGTH(c.swip) > 0)",
        "   OR (COALESCE(c.vlan, a.vlan) IS NOT NULL AND DATALENGTH(COALESCE(c.vlan, a.vlan)) > 0)",
        "   OR (COALESCE(c.port, a.port) IS NOT NULL AND DATALENGTH(COALESCE(c.port, a.port)) > 0)",
        " )",
        "<if test='ip != null and ip != \"\"'>",
        " AND COALESCE(a.ip, i.ip) like '%'+#{ip}+'%'",
        " AND (",
        "   (i.note IS NOT NULL AND DATALENGTH(i.note) > 0)",
        "   OR (a.mac IS NOT NULL AND DATALENGTH(a.mac) > 0)",
        "   OR (c.swip IS NOT NULL AND DATALENGTH(c.swip) > 0)",
        "   OR (COALESCE(c.vlan, a.vlan) IS NOT NULL AND DATALENGTH(COALESCE(c.vlan, a.vlan)) > 0)",
        "   OR (COALESCE(c.port, a.port) IS NOT NULL AND DATALENGTH(COALESCE(c.port, a.port)) > 0)",
        " )",
        "</if>",
        "<if test='note != null and note != \"\"'>",
        " AND i.note like N'%'+#{note}+'%'",
        " AND (",
        "   (i.note IS NOT NULL AND DATALENGTH(i.note) > 0)",
        "   OR (a.mac IS NOT NULL AND DATALENGTH(a.mac) > 0)",
        "   OR (c.swip IS NOT NULL AND DATALENGTH(c.swip) > 0)",
        "   OR (COALESCE(c.vlan, a.vlan) IS NOT NULL AND DATALENGTH(COALESCE(c.vlan, a.vlan)) > 0)",
        "   OR (COALESCE(c.port, a.port) IS NOT NULL AND DATALENGTH(COALESCE(c.port, a.port)) > 0)",
        " )",
        "</if>",
        "<if test='swip != null and swip != \"\"'>",
        " AND c.swip = #{swip}",
        "</if>",
        "<if test='mac != null and mac != \"\"'>",
        " AND a.mac like '%'+#{mac}+'%'",
        "</if>",
        "ORDER BY (CASE WHEN COALESCE(a.ip, i.ip) = #{ip} THEN 0 ELSE 1 END), (CASE WHEN i.note IS NOT NULL AND DATALENGTH(i.note) > 0 THEN 0 ELSE 1 END), a.sj DESC",
        "</script>"
    })
    public List<IpInfo> selectIpList(IpInfo ipInfo);

    /**
     * 更新备注 (存在则更新，不存在则插入)
     */
    @Update({
        "IF EXISTS (SELECT 1 FROM HW_IP WHERE ip = #{ip})",
        "  UPDATE HW_IP SET note = #{note} WHERE ip = #{ip}",
        "ELSE",
        "  INSERT INTO HW_IP (ip, note) VALUES (#{ip}, #{note})"
    })
    public int updateIpNote(@Param("ip") String ip, @Param("note") String note);

    /**
     * 删除 ARP 记录
     */
    @Delete("DELETE FROM hw_arp WHERE ip = #{ip} AND mac = #{mac}")
    public int deleteArp(@Param("ip") String ip, @Param("mac") String mac);

    /**
     * 删除 MAC 记录
     */
    @Delete("DELETE FROM hw_mac WHERE swip = #{swip} AND mac = #{mac}")
    public int deleteMac(@Param("swip") String swip, @Param("mac") String mac);

    /**
     * 统计信息 (按SWIP分组，取前20个活跃的 - 仅统计最近2000条记录以优化性能)
     */
    @Select({
        "SELECT TOP 20 c.swip as name, COUNT(*) as value",
        "FROM (SELECT TOP 2000 mac FROM hw_arp ORDER BY sj DESC) a",
        "LEFT JOIN hw_mac c ON a.mac = c.mac",
        "WHERE c.swip IS NOT NULL",
        "GROUP BY c.swip",
        "ORDER BY COUNT(*) DESC"
    })
    public List<Map<String, Object>> selectIpStats();
}
