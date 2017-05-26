package tech.jiangtao.support.kit.eventbus;

/**
 * Class: IMLoginRequestModel </br>
 * Description: 登录参数 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 07:10</br>
 * Update: 27/05/2017 07:10 </br>
 **/
public class IMLoginRequestModel {
    public String username;
    public String password;
    public boolean needAutoLogin;

    public IMLoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public IMLoginRequestModel(String username, String password, boolean needAutoLogin) {
        this.username = username;
        this.password = password;
        this.needAutoLogin = needAutoLogin;
    }
}
