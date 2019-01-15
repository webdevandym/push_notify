package com.neijel.push.notify.apns.query;

import com.neijel.push.notify.apns.ApnsSendStatus;

public class DeviceResponse {

    private ApnsSendStatus apnsSendStatus;
    private String         token;

    public DeviceResponse() {
    }

    public DeviceResponse(ApnsSendStatus apnsSendStatus, String token) {
        this.apnsSendStatus = apnsSendStatus;
        this.token = token;
    }

    public ApnsSendStatus getApnsSendStatus() {
        return apnsSendStatus;
    }

    public void setApnsSendStatus(ApnsSendStatus apnsSendStatus) {
        this.apnsSendStatus = apnsSendStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
