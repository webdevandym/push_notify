package push.notify.fcm;

import org.json.JSONObject;
import push.notify.fcm.config.FcmMessage;

public class FcmMessageBuilder {

    private FcmMessage fcmMessage;

    FcmMessageBuilder(FcmMessage fcmMessage) {
        this.fcmMessage = fcmMessage;
    }

    String build() {

        JSONObject request = new JSONObject();
        request.put("registration_ids", fcmMessage.getDevices());
        request.put("priority", fcmMessage.getPriority());

        JSONObject notification = new JSONObject();
        notification.put("title", fcmMessage.getNotifyTitle());
        notification.put("body", fcmMessage.getNotifyBody());

        request.put("notification", notification);

        if (!fcmMessage.getData().isEmpty()) {
            JSONObject data = new JSONObject();
            fcmMessage.getData().forEach(data::put);

            request.put("data", data);
        }

        return request.toString();
    }
}
