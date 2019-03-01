package com.neijel.push.notify.fcm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.neijel.push.notify.fcm.config.FcmNotificationsConfig;
import com.neijel.push.notify.fcm.config.FcmPayload;
import com.neijel.push.notify.fcm.query.AndroidResponse;
import com.neijel.push.notify.platform.Platform;
import com.neijel.push.notify.platform.query.Callback;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Fcm implements Platform {

    private static final String                 HTTPS_FCM_GOOGLEAPIS_COM_FCM_SEND = "https://fcm.googleapis.com/fcm/send";
    private              FcmNotificationsConfig fcmNotificationsConfig;

    public Fcm(FcmNotificationsConfig fcmNotificationsConfig) {
        this.fcmNotificationsConfig = fcmNotificationsConfig;
    }

    private HttpResponse sendWithLimit(FcmPayload fcmPayload) throws IOException {
        String request = new FcmMessageBuilder(fcmPayload).build();

        HttpPost httpPost = initHttpPost(fcmNotificationsConfig);
        httpPost.setEntity(new StringEntity(request, "UTF-8"));
        return HttpClientBuilder.create().build().execute(httpPost);
    }

    public List<HttpResponse> send(FcmPayload fcmPayload) {
        return Lists.partition(fcmPayload.getDevices(), 1000)
                    .stream()
                    .map(strings -> {
                        fcmPayload.setDevices(strings);
                        try {
                            return sendWithLimit(fcmPayload);
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
    }


    public void send(FcmPayload fcmPayload, Callback callback) throws Exception {
        handleRequest(send(fcmPayload), callback);
    }

    private HttpPost initHttpPost(FcmNotificationsConfig fcmNotificationsConfig) {

        HttpPost httpPost = new HttpPost(HTTPS_FCM_GOOGLEAPIS_COM_FCM_SEND);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "key=" + fcmNotificationsConfig.getApiKey());

        return httpPost;
    }

    private void handleRequest(List<HttpResponse> httpResponses, Callback callback) throws IOException {

        httpResponses.forEach(response -> {
            try {

                String stringEntity = EntityUtils.toString(response.getEntity());

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == HttpStatus.SC_OK) {
                    AndroidResponse androidResponse = new ObjectMapper().readValue(stringEntity, AndroidResponse.class);
                    androidResponse.setStatusCode(statusCode);
                    callback.onSuccess(androidResponse);
                } else {
                    AndroidResponse androidResponse = new AndroidResponse();
                    androidResponse.setRequestError(stringEntity);
                    androidResponse.setStatusCode(statusCode);
                    callback.onFailed(androidResponse);
                }
            } catch (IOException ignore) {
            }
        });
    }

}
