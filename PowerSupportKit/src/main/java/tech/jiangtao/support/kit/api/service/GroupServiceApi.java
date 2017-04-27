package tech.jiangtao.support.kit.api.service;

import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.model.group.Authority;

/**
 * Class: GroupServiceApi </br>
 * Description: 群组api </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 16/04/2017 4:32 PM</br>
 * Update: 16/04/2017 4:32 PM </br>
 **/

public interface GroupServiceApi {

  /**
   * 获取我加入的所有的群
   */
  @POST("group/groups") @FormUrlEncoded Observable<List<GroupRealm>> groups(
      @Field("userId") String userId);

  /**
   * 获取群用户列表
   */
  @POST("group/groupMember") @FormUrlEncoded Observable<List<ContactRealm>> selectGroupMembers(
      @Field("groupId") String groupId, @Field("userId") String userId);

  /**
   * 创建群组
   */
  @POST("group/create") @FormUrlEncoded Observable<GroupRealm> createGroup(
      @Field("groupId") String groupId, @Field("userId") String userId, @Field("name") String name,
      @Field("avatar") String avatar, @Field("description") String description);

  /**
   * 查询是否接受群消息
   */
  @POST("group/isReceived") @FormUrlEncoded Observable<Authority> isReceived(
      @Field("groupId") String groupId, @Field("userId") String userId);

  /**
   * 修改是否接受群消息
   */
  @POST("group/updateIsReceived") @FormUrlEncoded Observable<Authority> updateIsReceived(
      @Field("groupId") String groupId, @Field("userId") String userId);

  /**
   * 添加用户入群
   */
  @POST("group/addMembers") @FormUrlEncoded Observable<List<ContactRealm>> addMembers(
      @Field("userInvitedId") List<String> tigGroupMembers, @Field("userId") String userId,
      @Field("groupId") String groupId);

  /**
   * 删除用户
   * @param userIds
   * @param userId
   * @param groupId
   * @return
   */
  @POST("group/addMembers") @FormUrlEncoded Observable<ContactRealm> deleteGroupMember(
      @Field("userIds") List<String> userIds, @Field("userId") String userId,
      @Field("groupId") String groupId);


}
