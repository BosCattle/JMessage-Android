package tech.jiangtao.support.kit.api.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import tech.jiangtao.support.kit.model.IMFilePath;

/**
 * Class: UpLoadServiceApi </br>
 * Description: 上传api </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 15/01/2017 11:07 PM</br>
 * Update: 15/01/2017 11:07 PM </br>
 **/

public interface UpLoadServiceApi {

  /**
   * 单个文件上传
   */
  @Multipart @POST("file/upload") Observable<IMFilePath> upload(@Part MultipartBody.Part file,
      @Part("type") RequestBody type);

  /**
   * 多个文件上传
   */
  @Multipart @POST("file/uploadMultipleFile") Observable<IMFilePath> uploadMulti(
      @Part MultipartBody.Part[] files, @Part("types") String[] types);
}
