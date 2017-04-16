package tech.jiangtao.support.ui.api.service;

import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import tech.jiangtao.support.kit.realm.GroupRealm;

/**
 * Class: GroupServiceApi </br>
 * Description: 群组api </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 16/04/2017 4:32 PM</br>
 * Update: 16/04/2017 4:32 PM </br>
 **/

public interface GroupServiceApi {


  @POST("group/groups")
  @FormUrlEncoded Observable<List<GroupRealm>> groups(@Field("userId") String userId);
}
