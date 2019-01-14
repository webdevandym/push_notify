package push.notify.apns;

import com.jcabi.aspects.Async;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import io.netty.util.concurrent.Future;
import push.notify.apns.config.ApnsNotificationsConfig;
import push.notify.apns.config.ApnsPayload;
import push.notify.platform.Platform;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Apns implements Platform {

    private final ApnsNotificationsConfig apnsNotificationsConfig;
    private final ApnsClient              apnsClient;

    public Apns(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        this.apnsNotificationsConfig = apnsNotificationsConfig;
        this.apnsClient = initClient(apnsNotificationsConfig);
    }

    public void send(ApnsPayload apnsPayload) {
        sendBulkDevices(apnsPayload);
    }

    public void sendBulk(List<ApnsPayload> apnsPayloads) {
        apnsPayloads.parallelStream().forEach(apnsPayload -> {
            send(apnsPayload);
        });
    }

    public void close() throws InterruptedException {
        Future<Void> close = apnsClient.close();
        close.await();
    }

    private String setMessage(ApnsPayload apnsPayload) {
        return new ApnsMessageBuilder(apnsPayload).build();
    }

    @Async
    private void sendToDevice(String token, String message) {

        final String tokenSanitize = TokenUtil.sanitizeTokenString(token);
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(tokenSanitize, apnsNotificationsConfig.getTopic(), message);
        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> simpleApnsPushNotificationPushNotificationResponsePushNotificationFuture = apnsClient
                .sendNotification(pushNotification);

        try {
            simpleApnsPushNotificationPushNotificationResponsePushNotificationFuture.get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void sendBulkDevices(ApnsPayload apnsPayload) {

        String message = setMessage(apnsPayload);

        apnsPayload.getDevices()
                   .parallelStream()
                   .forEach(token -> {
                       sendToDevice(token, message);
                   });
    }

    private ApnsClient initClient(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        return new ApnsClientBuilder()
                .setApnsServer(apnsNotificationsConfig.getSandbox() ? ApnsClientBuilder.DEVELOPMENT_APNS_HOST : ApnsClientBuilder.PRODUCTION_APNS_HOST)
                .setSigningKey(ApnsSigningKey.loadFromPkcs8File(apnsNotificationsConfig.getP8(), apnsNotificationsConfig.getTeamId(), apnsNotificationsConfig.getKeyId()))
                .build();
    }


}
