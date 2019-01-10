package push.notify.apns.config;

import push.notify.platform.config.PlatformNotificationsConfig;

import java.io.File;

public class ApnsNotificationsConfig implements PlatformNotificationsConfig {

    private String  teamId;
    private String  keyId;
    private String  topic;
    private Boolean sandbox = false;
    private File    p8;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public Boolean getSandbox() {
        return sandbox;
    }

    public void setSandbox(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    public File getP8() {
        return p8;
    }

    public void setP8(File p8) {
        this.p8 = p8;
    }
}
