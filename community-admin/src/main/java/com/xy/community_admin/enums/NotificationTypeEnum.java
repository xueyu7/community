package com.xy.community_admin.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"问题通知"),
    REPLY_COMMENT(2,"评论通知"),
    REPLY_SYS(3,"系统通知"),
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOf(Integer type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
