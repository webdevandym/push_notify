package push.notify.platform.query;

public interface Callback {
    void onSuccess(Response response);

    void onFailed(Response response);
}
