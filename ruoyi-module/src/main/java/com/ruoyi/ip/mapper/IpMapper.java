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
        "SELECT t_base.ip, t_arp.mac, t_mac.swip, t_ip.note, t_arp.sj, COALESCE(t_mac.vlan, t_arp.vlan) as vlan, COALESCE(t_mac.port, t_arp.port) as port",
        "FROM (",
        "  SELECT ip FROM HW_IP",
        "  UNION",
        "  SELECT ip FROM hw_arp GROUP BY ip",
        ") t_base",
        "LEFT JOIN (",
        "  SELECT a.ip, a.mac, a.sj, a.vlan, a.port",
        "  FROM hw_arp a",
        "  INNER JOIN (SELECT ip, MAX(sj) as max_sj FROM hw_arp GROUP BY ip) b ON a.ip = b.ip AND a.sj = b.max_sj",
        ") t_arp ON t_base.ip = t_arp.ip",
        "LEFT JOIN HW_IP t_ip ON t_base.ip = t_ip.ip",
        "LEFT JOIN hw_mac t_mac ON t_arp.mac = t_mac.mac",
        "WHERE 1=1",
        " AND t_base.ip IS NOT NULL",
        " AND (",
        "   (t_ip.note IS NOT NULL AND CHAR_LENGTH(t_ip.note) > 0)",
        "   OR (t_arp.mac IS NOT NULL AND CHAR_LENGTH(t_arp.mac) > 0)",
        "   OR (t_mac.swip IS NOT NULL AND CHAR_LENGTH(t_mac.swip) > 0)",
        "   OR (COALESCE(t_mac.vlan, t_arp.vlan) IS NOT NULL AND CHAR_LENGTH(COALESCE(t_mac.vlan, t_arp.vlan)) > 0)",
        "   OR (COALESCE(t_mac.port, t_arp.port) IS NOT NULL AND CHAR_LENGTH(COALESCE(t_mac.port, t_arp.port)) > 0)",
        " )",
        "<if test='ip != null and ip != \"\"'>",
        " AND t_base.ip like concat('%', #{ip}, '%')",
        " AND (",
        "   (t_ip.note IS NOT NULL AND CHAR_LENGTH(t_ip.note) > 0)",
        "   OR (t_arp.mac IS NOT NULL AND CHAR_LENGTH(t_arp.mac) > 0)",
        "   OR (t_mac.swip IS NOT NULL AND CHAR_LENGTH(t_mac.swip) > 0)",
        "   OR (COALESCE(t_mac.vlan, t_arp.vlan) IS NOT NULL AND CHAR_LENGTH(COALESCE(t_mac.vlan, t_arp.vlan)) > 0)",
        "   OR (COALESCE(t_mac.port, t_arp.port) IS NOT NULL AND CHAR_LENGTH(COALESCE(t_mac.port, t_arp.port)) > 0)",
        " )",
        "</if>",
        "<if test='note != null and note != \"\"'>",
        " AND t_ip.note like concat('%', #{note}, '%')",
        " AND (",
        "   (t_ip.note IS NOT NULL AND CHAR_LENGTH(t_ip.note) > 0)",
        "   OR (t_arp.mac IS NOT NULL AND CHAR_LENGTH(t_arp.mac) > 0)",
        "   OR (t_mac.swip IS NOT NULL AND CHAR_LENGTH(t_mac.swip) > 0)",
        "   OR (COALESCE(t_mac.vlan, t_arp.vlan) IS NOT NULL AND CHAR_LENGTH(COALESCE(t_mac.vlan, t_arp.vlan)) > 0)",
        "   OR (COALESCE(t_mac.port, t_arp.port) IS NOT NULL AND CHAR_LENGTH(COALESCE(t_mac.port, t_arp.port)) > 0)",
        " )",
        "</if>",
        "<if test='swip != null and swip != \"\"'>",
        " AND t_mac.swip = #{swip}",
        "</if>",
        "<if test='mac != null and mac != \"\"'>",
        " AND t_arp.mac like concat('%', #{mac}, '%')",
        "</if>",
        "ORDER BY (CASE WHEN t_base.ip = #{ip} THEN 0 ELSE 1 END), (CASE WHEN t_ip.note IS NOT NULL AND CHAR_LENGTH(t_ip.note) > 0 THEN 0 ELSE 1 END), t_arp.sj DESC",
        "</script>"
    })
    public List<IpInfo> selectIpList(IpInfo ipInfo);

    /**
     * 更新备注 (存在则更新，不存在则插入)
     */
    @Update({
        "INSERT INTO hw_ip (ip, note) VALUES (#{ip}, #{note})",
        "ON DUPLICATE KEY UPDATE note = VALUES(note)"
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
        "SELECT c.swip as name, COUNT(*) as value",
        "FROM (SELECT mac FROM hw_arp ORDER BY sj DESC LIMIT 2000) a",
        "LEFT JOIN hw_mac c ON a.mac = c.mac",
        "WHERE c.swip IS NOT NULL",
        "GROUP BY c.swip",
        "ORDER BY COUNT(*) DESC",
        "LIMIT 20"
    })
    public List<Map<String, Object>> selectIpStats();
}
