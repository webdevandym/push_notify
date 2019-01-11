package push.notify;


import push.notify.fcm.Fcm;
import push.notify.fcm.config.FcmNotificationsConfig;
import push.notify.fcm.config.FcmMessage;
import push.notify.platform.query.Callback;
import push.notify.platform.query.Response;

import java.util.ArrayList;

public class FCMExample {
    public static void main(String[] args) throws Exception {

        FcmNotificationsConfig fcmNotificationsConfig = new FcmNotificationsConfig();
        fcmNotificationsConfig.setApiKey("AAAAh58-0Oo:APA91bHGen4BLgqslTm2fNO-XTf_kueRRBWgVBkfjcyVeebUWIqJnMni-DmszvavTNgOavY8wr74dslmvLasU5JN1dOJt6jUdhzO4cgkxNPRCqf7wyywYPFznfDlX1VKlkyH7mXHh0-j");

        Fcm fcm = new Fcm(fcmNotificationsConfig);

        FcmMessage fcmMessage1 = new FcmMessage();
        ArrayList<String> devices = new ArrayList<>();
        devices.add("c0WSB3fpNqg" +
                            ":APA91bHDh25a_8g7Qe9JGgmihqhnnOtQOthFNrhADHcvOWrrFFD7Rbbx7uKwH9dQVAsqlCwq9TNwLs7i7Dmns888SIm2dXFRYLnp1rsbXdXkHhSzdszI26w6apaZF_KPoKdOAQLYpE77");
        fcmMessage1.setDevices(devices);
        fcmMessage1.setNotifyBody("Sany");
        fcmMessage1.setNotifyTitle("inini");

        fcm.send(fcmMessage1, new Callback() {
            @Override
            public void onSuccess(Response response) {
                System.out.println(response);
            }

            @Override
            public void onFailed(Response response) {

            }
        });
    }
}
