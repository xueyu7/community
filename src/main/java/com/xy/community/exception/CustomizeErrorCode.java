package com.xy.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "问题不存在或已被删除，换一个试试吧"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "没登录吧你，先登录再评论"),
    SYS_ERROR(2004, "服务冒烟了，你一会再试试？"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在啊，换个试试？"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
