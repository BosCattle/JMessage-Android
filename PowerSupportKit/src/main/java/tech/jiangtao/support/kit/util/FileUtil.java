package tech.jiangtao.support.kit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class: FileUtil </br>
 * Description: 文件操作 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 1:08 PM</br>
 * Update: 04/12/2016 1:08 PM </br>
 **/

public class FileUtil {

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

  public static Bitmap drawableToBytes(byte[] bytes){
    Bitmap bitmap = null;
    if (bytes!=null){
      bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

      return bitmap;
    }else {
      throw new NullPointerException("bytes can not be null");
    }
  }
}
