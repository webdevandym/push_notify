package com.neijel.push.notify.apns.query;

import java.util.List;

public class PayloadResponse {
    private List<DeviceResponse> deviceResponses;

    public PayloadResponse() {
    }

    public PayloadResponse(List<DeviceResponse> deviceResponses) {
        this.deviceResponses = deviceResponses;
    }

    public List<DeviceResponse> getDeviceResponses() {
        return deviceResponses;
    }

    public void setDeviceResponses(List<DeviceResponse> deviceResponses) {
        this.deviceResponses = deviceResponses;
    }
}
