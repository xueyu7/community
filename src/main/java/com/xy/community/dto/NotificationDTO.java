package com.xy.community.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xy.community.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Integer id;
    private Integer status;
    private Integer notifier;
    private String notifierName;
    private Integer outerId;
    private String outerTitle;
    private String typeName;
    private Integer type;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
