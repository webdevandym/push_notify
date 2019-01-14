package push.notify.apns.config;

import push.notify.platform.config.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApnsPayload implements Message {

    private String              apsAlertTitle;
    private String              apsAlertBody;
    private String              sound;
    private Map<String, Object> additionFields = new HashMap<>();
    private List<String>        devices;

    public String getApsAlertTitle() {
        return apsAlertTitle;
    }

    public void setApsAlertTitle(String apsAlertTitle) {
        this.apsAlertTitle = apsAlertTitle;
    }

    public String getApsAlertBody() {
        return apsAlertBody;
    }

    public void setApsAlertBody(String apsAlertBody) {
        this.apsAlertBody = apsAlertBody;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Map<String, Object> getAdditionFields() {
        return additionFields;
    }

    public void setAdditionFields(Map<String, Object> additionFields) {
        this.additionFields = additionFields;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }
}
