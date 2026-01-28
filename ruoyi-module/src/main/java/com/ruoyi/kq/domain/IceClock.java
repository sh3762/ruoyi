package com.ruoyi.kq.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * ICE签到对象 ice_clock
 * 
 * @author ruoyi
 */
public class IceClock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 日期 */
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date srq;

    /** 签到时间 */
    @Excel(name = "签到时间")
    private String sc1;

    /** 签退时间 */
    @Excel(name = "签退时间")
    private String sc2;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date tm;

    /** 距今天数 (业务计算字段) */
    private Long dayDiff;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSrq(Date srq) 
    {
        this.srq = srq;
    }

    public Date getSrq() 
    {
        return srq;
    }
    public void setSc1(String sc1) 
    {
        this.sc1 = sc1;
    }

    public String getSc1() 
    {
        return sc1;
    }
    public void setSc2(String sc2) 
    {
        this.sc2 = sc2;
    }

    public String getSc2() 
    {
        return sc2;
    }
    public void setTm(Date tm) 
    {
        this.tm = tm;
    }

    public Date getTm() 
    {
        return tm;
    }

    public Long getDayDiff() {
        return dayDiff;
    }

    public void setDayDiff(Long dayDiff) {
        this.dayDiff = dayDiff;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("srq", getSrq())
            .append("sc1", getSc1())
            .append("sc2", getSc2())
            .append("tm", getTm())
            .toString();
    }
}
