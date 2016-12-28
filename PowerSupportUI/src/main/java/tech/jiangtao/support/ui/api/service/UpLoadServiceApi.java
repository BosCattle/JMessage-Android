package tech.jiangtao.support.ui.api.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import tech.jiangtao.support.ui.model.FilePath;

/**
 * Created by kevin on 28/12/2016.
 */

public interface UpLoadServiceApi {
  @Multipart @POST("file/fileupload") Observable<FilePath> upload(@Part MultipartBody.Part file);

  @Multipart @POST("file/fileupload") Observable<FilePath> upload(
      @Part MultipartBody.Part file, @Part("type") RequestBody type);
}
