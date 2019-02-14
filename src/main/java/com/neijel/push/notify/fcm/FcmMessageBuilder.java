package com.neijel.push.notify.fcm;

import com.neijel.push.notify.fcm.config.FcmPayload;
import org.json.JSONObject;

class FcmMessageBuilder {

    private FcmPayload fcmPayload;

    FcmMessageBuilder(FcmPayload fcmPayload) {
        this.fcmPayload = fcmPayload;
    }

    String build() {

        JSONObject request = new JSONObject();
        request.put("registration_ids", fcmPayload.getDevices());
        request.put("priority", fcmPayload.getPriority());

        JSONObject notification = new JSONObject();
        notification.put("title", fcmPayload.getNotifyTitle());
        notification.put("body", fcmPayload.getNotifyBody());
        notification.put("android_channel_id", fcmPayload.getAndroidChannelId());
        notification.put("icon", fcmPayload.getNotifyIcon());
        notification.put("sound", fcmPayload.getNotifySound());
        notification.put("tag", fcmPayload.getNotifyTag());
        notification.put("color", fcmPayload.getNotifyColor());
        notification.put("click_action", fcmPayload.getNotifyClickAction());

        request.put("notification", notification);

        if (!fcmPayload.getData().isEmpty()) {
            JSONObject data = new JSONObject();
            fcmPayload.getData().forEach(data::put);

            request.put("data", data);
        }

        return request.toString();
    }
}
