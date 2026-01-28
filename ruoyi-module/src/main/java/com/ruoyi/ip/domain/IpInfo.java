package com.ruoyi.ip.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * IP信息对象
 */
public class IpInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "IP地址")
    private String ip;

    @Excel(name = "MAC地址")
    private String mac;

    @Excel(name = "管理IP")
    private String swip;

    @Excel(name = "VLAN")
    private String vlan;

    @Excel(name = "端口")
    private String port;

    @Excel(name = "备注")
    private String note;

    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date sj;

    /** 是否显示重复IP (默认false, 即只显示最新一条) */
    private boolean showDuplicate;

    public void setIp(String ip) { this.ip = ip; }
    public String getIp() { return ip; }

    public void setMac(String mac) { this.mac = mac; }
    public String getMac() { return mac; }

    public void setSwip(String swip) { this.swip = swip; }
    public String getSwip() { return swip; }

    public void setVlan(String vlan) { this.vlan = vlan; }
    public String getVlan() { return vlan; }

    public void setPort(String port) { this.port = port; }
    public String getPort() { return port; }

    public void setNote(String note) { this.note = note; }
    public String getNote() { return note; }

    public void setSj(java.util.Date sj) { this.sj = sj; }
    public java.util.Date getSj() { return sj; }

    public void setShowDuplicate(boolean showDuplicate) { this.showDuplicate = showDuplicate; }
    public boolean isShowDuplicate() { return showDuplicate; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("ip", getIp())
            .append("mac", getMac())
            .append("swip", getSwip())
            .append("vlan", getVlan())
            .append("port", getPort())
            .append("note", getNote())
            .append("sj", getSj())
            .toString();
    }
}
