package tech.jiangtao.support.ui.api.service;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import tech.jiangtao.support.ui.model.group.Friends;

/**
 * Created by Vurtex on 2017/3/29.
 */

public interface UserServiceApi {

    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("user/queryUserList")
    Observable<List<Friends>> post(@Field("userId") String userId);


}
