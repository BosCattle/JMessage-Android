package tech.jiangtao.support.kit.util;

import android.util.Log;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Class: DateUtils </br>
 * Description: 时间解析工具类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 07/12/2016 10:38 AM</br>
 * Update: 07/12/2016 10:38 AM </br>
 **/

public class DateUtils {

  public static final String TAG = DateUtils.class.getSimpleName();

  // 首先保证timestamp正确性
  public static String getDefaultUTCTimeZone(long timestamp){
    Calendar calendar = Calendar.getInstance();
    TimeZone tz = TimeZone.getDefault();
    calendar.setTimeInMillis(timestamp*1000);
    calendar.add(Calendar.MILLISECOND,-tz.getOffset(calendar.getTimeInMillis()));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA);
    java.util.Date currentTime = calendar.getTime();
    Log.d(TAG, "getDefaultUTCTimeZone: 上传的时间轴"+sdf.format(currentTime));
    return sdf.format(currentTime);
  }

  /**
   *
   * @param date 读取date值,网络获取历史消息时需要从本地数据库中获取
   * @return {@link String}
   */
  public static String getDefaultUTCTimeZone(Date date){
    Calendar calendar = Calendar.getInstance();
    TimeZone tz = TimeZone.getDefault();
    calendar.setTimeInMillis(date.getTime());
    calendar.add(Calendar.MILLISECOND,-tz.getOffset(calendar.getTimeInMillis()));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
    java.util.Date currentTime = calendar.getTime();
    String time = currentTime.toString();
    Log.e(TAG, "getDefaultUTCTimeZone: "+time);
    return sdf.format(currentTime);
  }

  /**
   * 获取数据时间,转化为{@link Date},缓存历史消息到本地时需要
   * @param currentTime
   * @param startTime  代表的是消息体中具体发送消息的时间
   * @return {@link Date}
   */
  public static java.util.Date getSumUTCTimeZone(long currentTime,long startTime){
    return new java.util.Date(startTime*1000);
  }

  /**
   *
   * @param utc 标准格式的utc
   * @return timestamp
   */
  public static long UTCConvertToLong(String utc){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
    long time = 0;
    try {
      time = sdf.parse(utc).getTime();
      Log.d(TAG, "UTCConvertToLong: 解析出来的我们发送请求时的时间"+time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return time;
  }
}
