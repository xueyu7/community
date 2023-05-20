package com.xy.community.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xy.community.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private String content;
    private Integer commentCount;
    private User user;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
