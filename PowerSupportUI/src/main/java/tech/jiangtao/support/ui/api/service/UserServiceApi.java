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
    Observable<List<Friends>> queryUserList(@Field("userId") String userId);

    /**
     * 获取我加入的所有群组
     * @param uid
     * @return
     */
    @GET("group/joined")
    Observable<List<Groups>> getOwnGroup(@Query("uid") String uid);
    /**
     * 查找用户
     * @param nickname
     * @return
     */
    @GET("user/queryAccount")
    Observable<List<User>> getQueryAccount(@Query("nickname") String nickname);
    /**
     * 查找群组
     * @param roomName
     * @return
     */
    @GET("group/search")
    Observable<List<Groups>> getQueryGroup(@Query("roomName") String roomName);
    /**
     * 查找群组
     * @param userId
     * @return
     */
    @GET("user/allInvite")
    Observable<List<Groups>> getAllInvite(@Query("userId") String userId);
    //1 4 16 21 24 25 26

}
