package tech.jiangtao.support.kit.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import java.io.IOException;

/**
 * Class: AudioCaptureUtils </br>
 * Description: 音频捕获</br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 16/12/2016 11:43 PM</br>
 * Update: 16/12/2016 11:43 PM </br>
 **/

public class AudioCaptureUtils {

  private static final String LOG_TAG = "AudioCaptureUtils";
  private static String mFileName = null;
  private MediaRecorder mRecorder = null;
  private MediaPlayer mPlayer = null;

  /**
   * 开始录音
   */
  private void startRecording() {
    mFileName = FileUtil.createAudioDic()+System.currentTimeMillis()+".3gp";
    mRecorder = new MediaRecorder();
    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    mRecorder.setOutputFile(mFileName);
    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    try {
      mRecorder.prepare();
    } catch (IOException e) {
      Log.e(LOG_TAG, "prepare() failed");
    }
    mRecorder.start();
  }

  /**
   * 停止录音
   */
  private void stopRecording() {
    mRecorder.stop();
    mRecorder.release();
    mRecorder = null;
  }

  /**
   * 播放
   */
  private void startPlaying() {
    mPlayer = new MediaPlayer();
    try {
      mPlayer.setDataSource(mFileName);
      mPlayer.prepare();
      mPlayer.start();
    } catch (IOException e) {
      Log.e(LOG_TAG, "prepare() failed");
    }
  }

  /**
   * 停止播放
   */
  private void stopPlaying() {
    mPlayer.release();
    mPlayer = null;
  }

  /**
   * 销毁，避免内存溢出
   */
  public void ondestory(){
    if (mRecorder != null) {
      mRecorder.release();
      mRecorder = null;
    }

    if (mPlayer != null) {
      mPlayer.release();
      mPlayer = null;
    }
  }

}
