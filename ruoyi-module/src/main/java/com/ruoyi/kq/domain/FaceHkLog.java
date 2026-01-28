package com.ruoyi.kq.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 海康刷脸记录对象 face_hk_log
 * 
 * @author ruoyi
 */
public class FaceHkLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 事件时间 */
    @Excel(name = "事件时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setEventTime(Date eventTime) 
    {
        this.eventTime = eventTime;
    }

    public Date getEventTime() 
    {
        return eventTime;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("eventTime", getEventTime())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
