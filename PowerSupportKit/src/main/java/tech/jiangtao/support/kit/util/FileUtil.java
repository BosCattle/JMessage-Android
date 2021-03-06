package tech.jiangtao.support.kit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Class: FileUtil </br>
 * Description: 文件操作 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 1:08 PM</br>
 * Update: 04/12/2016 1:08 PM </br>
 **/

public class FileUtil {

  private static final String DICTIONARY = "/powerchat";
  private static int i = 0;

  /**
   * 根据文件路径拿到二进制流
   * @param path
   * @return
   */
  public static byte[] getFileByte(String path){
    File file = new File(path);
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(file));
      int bytes = (int) file.length();
      byte[] buffer = new byte[bytes];
      int readType = bis.read(buffer);
      if (readType!=buffer.length){
        throw new IOException("Entire file not read");
      }
      return buffer;
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if (bis!=null){
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * 根据byte数组拿到bitmap
   * @param bytes
   * @return
   */
  public static Bitmap drawableToBytes(byte[] bytes){
    Bitmap bitmap = null;
    if (bytes!=null){
      bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

      return bitmap;
    }else {
      throw new NullPointerException("bytes can not be null");
    }
  }

  /**
   * 检查外部存储可用以及可读可写
   * @return
   */
  public static boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(state);
  }

  /**
   * 检查外部存储可用并且是否可读
   * @return
   */
  public static boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(
        state);
  }

  /**
   * 根据文件名获取文件
   * @param albumName
   * @return
   */
  public static File getAlbumStorageDir(String albumName) {
    File file = new File(Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
      Log.e("log:", "Directory not created");
    }
    return file;
  }

  /**
   * 创建目录,获得路径
   */
  public static String create(){
    File file = new File(Environment.getExternalStorageDirectory(), DICTIONARY);
    if (!file.mkdirs()) {
      Log.e("log:", "Directory not created");
    }
    return file.getAbsolutePath();
  }

  /**
   * 创建图片存放目录
   * @return
   */
  public static String createImageDic(){
    String name = create();
    File file = new File(new File(name), "/image");
    if (!file.mkdirs()) {
      Log.e("log:", "Directory not created");
    }
    return file.getAbsolutePath();
  }

  /**
   * 创建音频存放目录
   * @return
   */
  public static String createAudioDic(){
    String name = create();
    File file = new File(new File(name), "/audio");
    if (!file.exists()) {
      Log.e("log:", "creating Directory");
      boolean isSuccess = file.mkdirs();
      if (isSuccess){
        Log.e("log:", "Directory create finish");
      }
    }
    return file.getAbsolutePath();
  }

  /**
   * 创建普通文件存放目录
   * @return
   */
  public static String createNormalFileDic(){
    String name = create();
    File file = new File(new File(name), "/files");
    if (!file.mkdirs()) {
      Log.e("log:", "Directory not created");
    }
    return file.getAbsolutePath();
  }

  public static String generatedAudioFile(){
    File file = new File(createAudioDic(),generateFileName());
    return file.getAbsolutePath();
  }

  public static String generateFileName(){
    return UUID.randomUUID().toString()+".mp4";
  }
}
