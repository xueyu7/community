package com.xy.community_admin.dto;

import lombok.Data;

@Data
public class AddDTO {
    private Integer id;
    private String name;
    private String content;
    private Integer receiver;
}
