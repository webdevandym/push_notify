package com.neijel.push.notify.fcm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neijel.push.notify.fcm.config.FcmNotificationsConfig;
import com.neijel.push.notify.fcm.config.FcmPayload;
import com.neijel.push.notify.fcm.query.AndroidResponse;
import com.neijel.push.notify.platform.Platform;
import com.neijel.push.notify.platform.query.Callback;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Fcm implements Platform {

    private static final String                 HTTPS_FCM_GOOGLEAPIS_COM_FCM_SEND = "https://fcm.googleapis.com/fcm/send";
    private              FcmNotificationsConfig fcmNotificationsConfig;

    public Fcm(FcmNotificationsConfig fcmNotificationsConfig) {
        this.fcmNotificationsConfig = fcmNotificationsConfig;
    }

    public HttpResponse send(FcmPayload fcmPayload) throws IOException {
        String request = new FcmMessageBuilder(fcmPayload).build();

        HttpPost httpPost = initHttpPost(fcmNotificationsConfig);
        httpPost.setEntity(new StringEntity(request, "UTF-8"));
        return HttpClientBuilder.create().build().execute(httpPost);
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

    private void handleRequest(HttpResponse httpResponse, Callback callback) throws IOException {

        HttpEntity entity = httpResponse.getEntity();

        AndroidResponse response = new ObjectMapper().readValue(EntityUtils.toString(entity), AndroidResponse.class);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            callback.onSuccess(response);
        } else {
            callback.onFailed(response);
        }
    }

}
