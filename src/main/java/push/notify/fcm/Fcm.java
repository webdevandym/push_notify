package push.notify.fcm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import push.notify.fcm.config.FcmNotificationsConfig;
import push.notify.fcm.config.FcmMessage;
import push.notify.fcm.query.AndroidResponse;
import push.notify.platform.Platform;
import push.notify.platform.query.Callback;

import java.io.IOException;

public class Fcm implements Platform {

    private static final String HTTPS_FCM_GOOGLEAPIS_COM_FCM_SEND = "https://fcm.googleapis.com/fcm/send";
    private HttpPost httpPost;
    private HttpClient httpClient;

    public Fcm(FcmNotificationsConfig fcmNotificationsConfig) {
        initClient(fcmNotificationsConfig);
    }

    public HttpResponse send(FcmMessage fcmMessage) throws IOException {
        String request = new FcmMessageBuilder(fcmMessage).build();


        httpPost.setEntity(new StringEntity(request, "UTF-8"));
        return httpClient.execute(httpPost);
    }

    public void send(FcmMessage fcmMessage, Callback callback) throws Exception {
        handleRequest(send(fcmMessage), callback);
    }

    private void initClient(FcmNotificationsConfig fcmNotificationsConfig) {
        httpClient = HttpClientBuilder.create().build();

        httpPost = new HttpPost(HTTPS_FCM_GOOGLEAPIS_COM_FCM_SEND);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "key=" + fcmNotificationsConfig.getApiKey());
    }

    private void handleRequest(HttpResponse httpResponse, Callback callback) throws Exception {

        HttpEntity entity = httpResponse.getEntity();

        AndroidResponse response = new ObjectMapper().readValue(EntityUtils.toString(entity), AndroidResponse.class);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            callback.onSuccess(response);
        } else {
            callback.onFailed(response);
        }
    }

}
