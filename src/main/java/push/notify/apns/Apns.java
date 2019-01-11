package push.notify.apns;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import io.netty.util.concurrent.Future;
import push.notify.apns.config.ApnsNotificationsConfig;
import push.notify.apns.config.Message;
import push.notify.platform.Platform;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Apns implements Platform {

    private final ApnsNotificationsConfig apnsNotificationsConfig;
    private final ApnsClient              apnsClient;
    private       String                  payload;

    public Apns(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        this.apnsNotificationsConfig = apnsNotificationsConfig;
        this.apnsClient = initClient(apnsNotificationsConfig);
    }

    public void setMessage(Message message) {
        this.payload = new ApnsMessageBuilder(message).build();
    }

    public ApnsSendStatus send(String token) throws ExecutionException {
        final String tokenSanitize = TokenUtil.sanitizeTokenString(token);
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(tokenSanitize, apnsNotificationsConfig.getTopic(), payload);
        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> simpleApnsPushNotificationPushNotificationResponsePushNotificationFuture = apnsClient
                .sendNotification(pushNotification);

        ApnsSendStatus apnsSendStatus = ApnsSendStatus.UNKNOWN;

        try {
            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                    simpleApnsPushNotificationPushNotificationResponsePushNotificationFuture.get();

            if (pushNotificationResponse.isAccepted()) {
                apnsSendStatus = ApnsSendStatus.ACCEPTED;
            } else {
                apnsSendStatus = ApnsSendStatus.REJECTED;

                if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {
                    apnsSendStatus = ApnsSendStatus.INVALID;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return apnsSendStatus;
    }

    public void close() throws InterruptedException {
        Future<Void> close = apnsClient.close();
        close.await();
    }

    private ApnsClient initClient(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        return new ApnsClientBuilder()
                .setApnsServer(apnsNotificationsConfig.getSandbox() ? ApnsClientBuilder.DEVELOPMENT_APNS_HOST : ApnsClientBuilder.PRODUCTION_APNS_HOST)
                .setSigningKey(ApnsSigningKey.loadFromPkcs8File(apnsNotificationsConfig.getP8(), apnsNotificationsConfig.getTeamId(), apnsNotificationsConfig.getKeyId()))
                .build();
    }


}
