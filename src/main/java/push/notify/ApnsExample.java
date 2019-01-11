package push.notify;

import push.notify.apns.Apns;
import push.notify.apns.ApnsSendStatus;
import push.notify.apns.config.ApnsNotificationsConfig;
import push.notify.apns.config.Message;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ApnsExample {
    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException, ExecutionException {

        ClassLoader classLoader = ApnsExample.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("bundle.p8")).getFile());

        ApnsNotificationsConfig apnsNotificationsConfig = new ApnsNotificationsConfig();
        apnsNotificationsConfig.setKeyId("7MQ2CY2876");
        apnsNotificationsConfig.setTeamId("SPWJM4DSDE");
        apnsNotificationsConfig.setP8(file);
        apnsNotificationsConfig.setTopic("com.tomych.itls.stage");

        Message message = new Message();
        message.setApsAlertBody("HELLO!");
        message.setApsAlertTitle("OMG!");

        Apns apns = new Apns(apnsNotificationsConfig);
        apns.setMessage(message);

        if (apns.send("B251F2B8DB70A15511032BFDFE1D5BA04462359B3E1E64A19A5809B8C34CC29A") == ApnsSendStatus.ACCEPTED) {
            System.out.print("OK");
        }

        apns.close();

    }
}
