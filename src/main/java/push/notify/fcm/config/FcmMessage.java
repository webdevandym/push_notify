package push.notify.fcm.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message implements push.notify.platform.config.Message {

    private String              notifyTitle;
    private String              notifyBody;
    private String              priority;
    private List<String>        devices;
    private Map<String, Object> data = new HashMap<>();

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyBody() {
        return notifyBody;
    }

    public void setNotifyBody(String notifyBody) {
        this.notifyBody = notifyBody;
    }
}
