package com.neijel.push.notify.fcm.config;

import com.neijel.push.notify.platform.config.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FcmPayload implements Payload {

    private String              notifyTitle;
    private String              notifyBody;
    private String              notifySound;
    private String              notifyIcon;
    private String              notifyTag;
    private String              notifyColor;
    private String              notifyClickAction;
    private String              priority;
    private String              androidChannelId;
    private List<String>        devices;
    private Map<String, Object> data = new HashMap<>();

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyBody() {
        return notifyBody;
    }

    public void setNotifyBody(String notifyBody) {
        this.notifyBody = notifyBody;
    }

    public String getNotifySound() {
        return notifySound;
    }

    public void setNotifySound(String notifySound) {
        this.notifySound = notifySound;
    }

    public String getNotifyIcon() {
        return notifyIcon;
    }

    public void setNotifyIcon(String notifyIcon) {
        this.notifyIcon = notifyIcon;
    }

    public String getNotifyTag() {
        return notifyTag;
    }

    public void setNotifyTag(String notifyTag) {
        this.notifyTag = notifyTag;
    }

    public String getNotifyColor() {
        return notifyColor;
    }

    public void setNotifyColor(String notifyColor) {
        this.notifyColor = notifyColor;
    }

    public String getNotifyClickAction() {
        return notifyClickAction;
    }

    public void setNotifyClickAction(String notifyClickAction) {
        this.notifyClickAction = notifyClickAction;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAndroidChannelId() {
        return androidChannelId;
    }

    public void setAndroidChannelId(String androidChannelId) {
        this.androidChannelId = androidChannelId;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
