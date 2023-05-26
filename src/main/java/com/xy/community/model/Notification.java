package com.xy.community.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class Notification {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer notifier;
    private String notifierName;
    private Integer receiver;
    private Integer outerId;
    private String outerTitle;
    private Integer type;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //逻辑删除
    @TableLogic
    private Integer deleted;
}
