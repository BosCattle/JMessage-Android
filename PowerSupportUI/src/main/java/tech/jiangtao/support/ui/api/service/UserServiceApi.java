package tech.jiangtao.support.ui.api.service;

import java.util.HashMap;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Vurtex on 2017/3/29.
 */

public interface UserServiceApi {
    @POST("ajax.mobileSword")
    Observable<String> post(@QueryMap HashMap<String,String> paramsMap);


}
