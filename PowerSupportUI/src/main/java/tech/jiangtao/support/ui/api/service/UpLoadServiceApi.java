package tech.jiangtao.support.ui.api.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import tech.jiangtao.support.ui.model.FilePath;
/**
 * Class: UpLoadServiceApi </br>
 * Description: 文件上传 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 29/12/2016 2:54 PM</br>
 * Update: 29/12/2016 2:54 PM </br>
 **/

public interface UpLoadServiceApi {
  @Multipart @POST("file/fileupload") Observable<FilePath> upload(@Part MultipartBody.Part file);

  @Multipart @POST("file/fileupload") Observable<FilePath> upload(
      @Part MultipartBody.Part file, @Part("type") RequestBody type);
}
