package tech.jiangtao.support.ui.api.service;

import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.ui.model.group.Group;

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
   * @param groupId
   * @param userId
   * @param name
   * @param avatar
   * @param description
   * @return
   */
  @POST("group/create") @FormUrlEncoded Observable<GroupRealm> createGroup(
      @Field("groupId") String groupId, @Field("userId") String userId, @Field("name") String name,
      @Field("avatar") String avatar, @Field("description") String description);
}
