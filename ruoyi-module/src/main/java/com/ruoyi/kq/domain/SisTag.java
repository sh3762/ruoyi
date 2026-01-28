package com.ruoyi.kq.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * SIS测点对象 sis_tags
 * 
 * @author ruoyi
 */
public class SisTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 测点名称 */
    @Excel(name = "测点名称")
    private String tagname;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;

    /** 值 */
    @Excel(name = "值")
    private Double value;

    /** 时间 */
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date tm;

    /** 序号 */
    private Integer idx;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTagname(String tagname) 
    {
        this.tagname = tagname;
    }

    public String getTagname() 
    {
        return tagname;
    }
    public void setDescribe(String describe) 
    {
        this.describe = describe;
    }

    public String getDescribe() 
    {
        return describe;
    }
    public void setValue(Double value) 
    {
        this.value = value;
    }

    public Double getValue() 
    {
        return value;
    }
    public void setTm(Date tm) 
    {
        this.tm = tm;
    }

    public Date getTm() 
    {
        return tm;
    }
    public void setIdx(Integer idx) 
    {
        this.idx = idx;
    }

    public Integer getIdx() 
    {
        return idx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tagname", getTagname())
            .append("describe", getDescribe())
            .append("value", getValue())
            .append("tm", getTm())
            .toString();
    }
}
