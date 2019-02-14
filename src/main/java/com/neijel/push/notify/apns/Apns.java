package com.neijel.push.notify.apns;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import io.netty.util.concurrent.Future;
import com.neijel.push.notify.apns.config.ApnsNotificationsConfig;
import com.neijel.push.notify.apns.config.ApnsPayload;
import com.neijel.push.notify.apns.exceptions.BadCredentialException;
import com.neijel.push.notify.apns.query.DeviceResponse;
import com.neijel.push.notify.apns.query.PayloadResponse;
import com.neijel.push.notify.platform.Platform;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Apns implements Platform {

    private final ApnsNotificationsConfig apnsNotificationsConfig;
    private final ApnsClient              apnsClient;
    private       ExecutorService         executorService;
    private       int                     poolSize = 100;

    public Apns(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException, BadCredentialException {
        this.apnsNotificationsConfig = apnsNotificationsConfig;
        this.apnsClient = new ApnsClientManager(apnsNotificationsConfig).getApnsClient();
    }

    public Apns(ApnsNotificationsConfig apnsNotificationsConfig, int poolSize) throws NoSuchAlgorithmException, InvalidKeyException, IOException, BadCredentialException {
        this(apnsNotificationsConfig);
        this.poolSize = poolSize;
    }

    public List<DeviceResponse> send(ApnsPayload apnsPayload) {
        return sendBulkDevices(apnsPayload);
    }

    public List<PayloadResponse> sendBulk(List<ApnsPayload> apnsPayloads) {
        return apnsPayloads.parallelStream()
                           .map(apnsPayload -> new PayloadResponse(send(apnsPayload)))
                           .collect(Collectors.toList());
    }

    public void close() throws InterruptedException {
        shutdownThreadPool();
        Future<Void> close = apnsClient.close();
        close.await();
    }

    public void shutdownThreadPool() {
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
    }

    private void initTreadPool() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(poolSize);
        }
    }

    private String createMessage(ApnsPayload apnsPayload) {
        return new ApnsMessageBuilder(apnsPayload).build();
    }

    private List<DeviceResponse> sendBulkDevices(ApnsPayload apnsPayload) {

        initTreadPool();

        String message = createMessage(apnsPayload);

        return apnsPayload.getDevices()
                          .parallelStream()
                          .map(token -> runDeliveryInThread(message, token))
                          .collect(Collectors.toList());
    }

    private DeviceResponse runDeliveryInThread(String message, String token) {


        ApnsSendStatus apnsSendStatus = ApnsSendStatus.UNKNOWN;

        try {
            apnsSendStatus = executorService.submit(() -> sendToDevice(token, message)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new DeviceResponse(apnsSendStatus, token);
    }

    private ApnsSendStatus sendToDevice(String token, String message) {

        final String tokenSanitize = TokenUtil.sanitizeTokenString(token);
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(tokenSanitize, apnsNotificationsConfig.getTopic(), message);
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return apnsSendStatus;
    }
}
