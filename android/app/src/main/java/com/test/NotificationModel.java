package com.test;

public class NotificationModel {
    private String appName;
    private String content;
    private String sender;


    public NotificationModel(String appName, String content, String sender) {
        this.content = content;
        this.appName = appName;
        this.sender = sender;

    }

    public String getAppName() {
        return appName;
    }
}
