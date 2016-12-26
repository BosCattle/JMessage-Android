package tech.jiangtao.support.ui.view;

import android.media.*;
import android.media.AudioManager;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class: MediaManager </br>
 * Description: 语音播放功能 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 5:42 PM</br>
 * Update: 24/12/2016 5:42 PM </br>
 **/

public class MediaManager {

  private static MediaPlayer mMediaPlayer;
  private static boolean isPause;

  public static void playSound(String filePath,
      MediaPlayer.OnCompletionListener onCompletionListener) {
    Log.d("------", "playSound: "+filePath);
    if (mMediaPlayer == null) {
      mMediaPlayer = new MediaPlayer();
    } else {
      mMediaPlayer.reset();
    }
    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mMediaPlayer.setOnCompletionListener(onCompletionListener);
    try {
      mMediaPlayer.setDataSource(filePath);
      mMediaPlayer.prepare();
      mMediaPlayer.setOnPreparedListener(mp -> mMediaPlayer.start());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void pause() {
    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
      mMediaPlayer.pause();
      isPause = true;
    }
  }

  public static void resume() {
    if (mMediaPlayer != null && isPause) {
      mMediaPlayer.start();
      isPause = false;
    }
  }

  public static void release() {
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }
}
