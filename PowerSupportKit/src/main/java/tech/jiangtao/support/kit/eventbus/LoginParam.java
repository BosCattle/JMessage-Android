package tech.jiangtao.support.kit.eventbus;

/**
 * Created by jiang on 2016/12/30.
 */

public class LoginParam {
    public String username;
    public String password;
    public boolean needAutoLogin;

    public LoginParam(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginParam(String username, String password, boolean needAutoLogin) {
        this.username = username;
        this.password = password;
        this.needAutoLogin = needAutoLogin;
    }
}
