package push.notify.fcm.config;

import push.notify.platform.config.PlatformNotificationsConfig;

public class FcmNotificationsConfig implements PlatformNotificationsConfig {

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
