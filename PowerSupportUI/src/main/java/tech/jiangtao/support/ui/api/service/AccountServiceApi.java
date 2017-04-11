package tech.jiangtao.support.ui.api.service;

import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.group.Friends;
import tech.jiangtao.support.ui.model.group.Groups;

/**
 * Class: AccountServiceApi </br>
 * Description: 账户接口 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 11/04/2017 10:18 PM</br>
 * Update: 11/04/2017 10:18 PM </br>
 **/

public interface AccountServiceApi {

    /**
     * 用户注册
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("account/create")
    Observable<List<User>> createAccount(@Field("userJid") String userId,@Field("nickName") String nickName,@Field("password") String password);


}
