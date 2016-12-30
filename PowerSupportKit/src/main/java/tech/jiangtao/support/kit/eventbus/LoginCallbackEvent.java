package tech.jiangtao.support.kit.eventbus;

/**
 * Created by jiang on 2016/12/30.
 */

public class LoginCallbackEvent {
    public String success;
    public String error;

    public LoginCallbackEvent(String success, String error) {
        this.success = success;
        this.error = error;
    }
}
