package tech.jiangtao.support.ui.api.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import tech.jiangtao.support.ui.model.FilePath;
import tech.jiangtao.support.ui.model.type.TransportType;

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
   * 单个文件传输
   */
  @Multipart @POST("file/single") Observable<FilePath> uploadSingle(@Part MultipartBody.Part file,
      @Part("type") RequestBody type);

  /**
   *
   * @param file
   * @param type
   * @return
   */
  @Multipart @POST("file/list") Observable<FilePath> upload(@Part MultipartBody.Part file,
      @Part("type") RequestBody type);
}
