package com.xy.community.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String type;
    private String name;
    private String accountId;
    private String token;
    private String avatarUrl;

    //字段填充内容
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    //逻辑删除
    @TableLogic
    private Integer deleted;
}
