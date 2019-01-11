package push.notify.platform.query;

public interface Callback {
    void onSuccess(Response response) throws Exception;

    void onFailed(Response response) throws Exception;
}
