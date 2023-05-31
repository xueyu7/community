package com.xy.community.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer sticky;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    //逻辑删除
    @TableLogic
    private Integer deleted;
}
