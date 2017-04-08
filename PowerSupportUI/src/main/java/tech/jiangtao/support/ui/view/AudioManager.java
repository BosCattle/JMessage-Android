package tech.jiangtao.support.ui.view;

import android.media.MediaRecorder;
import java.io.File;
import java.io.IOException;
import tech.jiangtao.support.kit.util.FileUtil;

/**
 * Class: AudioManager </br>
 * Description: 语音记录实现 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 3:30 PM</br>
 * Update: 24/12/2016 3:30 PM </br>
 **/

public class AudioManager {

  private MediaRecorder mMediaRecorder;
  private String mSaveFileDictionary;
  private String mCurrentFilePath;
  private static AudioManager mInstance;
  private AudioStateListener mAudioStateListener;
  private boolean isPrepared;

  public void setmAudioStateListener(AudioStateListener mAudioStateListener) {
    this.mAudioStateListener = mAudioStateListener;
  }

  private AudioManager(){}

  public static AudioManager getInstance(){
    if (mInstance==null){
      synchronized (AudioManager.class){
        if (mInstance==null){
          mInstance = new AudioManager();
        }
      }
    }
    return mInstance;
  }

  public void prepareAudio(){
    isPrepared = false;
    mSaveFileDictionary = FileUtil.createAudioDic();
    if (mMediaRecorder==null) {
      mMediaRecorder = new MediaRecorder();
    }
    mCurrentFilePath = FileUtil.generatedAudioFile();
    mMediaRecorder.setOutputFile(mCurrentFilePath);
    //设置音频源为麦克风
    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    //设置音频格式
    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
    //设置音频编码
    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
    try {
      mMediaRecorder.prepare();
      mMediaRecorder.start();
      isPrepared = true;
      if (mAudioStateListener!=null) {
        mAudioStateListener.wellPrepared();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getVoiceLevel( int maxLevel){
    if (isPrepared&&mMediaRecorder!=null){
      return maxLevel*mMediaRecorder.getMaxAmplitude()/32768+1;
    }
    return 1;
  }

  public void  release(){
    if (mMediaRecorder!=null) {
      mMediaRecorder.stop();
      mMediaRecorder.release();
      mMediaRecorder = null;
    }
  }

  public void cancel(){
    release();
    if (mCurrentFilePath!=null){
      File file = new File(mCurrentFilePath);
      file.deleteOnExit();
    }
  }

  public void onDestroy(){
    mAudioStateListener = null;
  }

  public interface AudioStateListener{
    void wellPrepared();
  }

  public String getmCurrentFilePath() {
    return mCurrentFilePath;
  }
}
