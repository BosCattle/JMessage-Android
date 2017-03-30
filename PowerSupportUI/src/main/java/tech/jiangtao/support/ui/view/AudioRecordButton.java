package tech.jiangtao.support.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import tech.jiangtao.support.ui.R;

/**
 * Class: AudioRecordButton </br>
 * Description: 自定义Button </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 11:58 AM</br>
 * Update: 24/12/2016 11:58 AM </br>
 **/

public class AudioRecordButton extends Button{

  private static final int MOVE_DISTANCE_Y = 50;
  private static final int STATE_NORMAL = 0X01;
  private static final int STATE_RECORDING = 0X02;
  private static final int STATE_CANCEL = 0X03;
  private static final int MSG_AUDIO_PREPARED = 0X04;
  private static final int MSG_VOICE_CHANG = 0X05;
  private static final int MSG_DIALOG_DISMISS = 0X06;
  private int mCurrentState = STATE_NORMAL;
  private boolean isRecording = false;
  //是否触发onLongClick
  private boolean isReady;
  private DialogManager mDialogManager;
  private AudioManager mAudioManager;
  private float mInTime;
  public onAudioFinishRecordListener  monAudioFinishRecordListener;
  private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case MSG_AUDIO_PREPARED:
          mDialogManager.showRecordingDialog();
          isRecording = true;
          //获取音量大小
          new Thread(new Runnable() {
            @Override public void run() {
              while (isRecording) {
                try {
                  Thread.sleep(100);
                  mInTime += 0.1f;
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_VOICE_CHANG);
              }
            }
          }).start();
          break;
        case MSG_VOICE_CHANG:
          mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
          break;
        case MSG_DIALOG_DISMISS:
          break;
      }
    }
  };

  public AudioRecordButton(Context context) {
    super(context, null);
  }

  public AudioRecordButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    mDialogManager = new DialogManager(getContext());
    mAudioManager = AudioManager.getInstance();
    mAudioManager.setmAudioStateListener(() -> mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED));
    setOnLongClickListener(v -> {
      isReady = true;
      mAudioManager.prepareAudio();
      return false;
    });
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        changeState(STATE_RECORDING);
        break;
      case MotionEvent.ACTION_MOVE:
        if (isRecording) {
          if (wantToCancel(x, y)) {
            changeState(STATE_CANCEL);
          } else {
            changeState(STATE_RECORDING);
          }
        }
        break;
      case MotionEvent.ACTION_UP:
        if (!isReady) {
          reset();
          return super.onTouchEvent(event);
        }
        if (!isRecording||mInTime<0.6f) {
          mDialogManager.tooShort();
          mAudioManager.cancel();
          mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS,1000);
        }
        if (mCurrentState == STATE_RECORDING) {
          //正常录制结束
          mDialogManager.dismissDialog();
          mAudioManager.release();
          if (monAudioFinishRecordListener!=null){
            monAudioFinishRecordListener.onFinishRecord(mInTime,mAudioManager.getmCurrentFilePath());
          }
        } else if (mCurrentState==STATE_CANCEL){
          mDialogManager.dismissDialog();
          mAudioManager.cancel();
        }
        reset();
        break;
    }
    return super.onTouchEvent(event);
  }

  private void reset() {
    isRecording = false;
    changeState(STATE_NORMAL);
    mInTime = 0;
    isReady = false;
  }

  private boolean wantToCancel(int x, int y) {
    Log.d("------>", "wantToCancel: " + y);
    return y < -MOVE_DISTANCE_Y || y > getHeight() + MOVE_DISTANCE_Y;
  }

  private void changeState(int state) {
    if (mCurrentState != state) {
      mCurrentState = state;
      switch (state) {
        case STATE_NORMAL:
          setText(R.string.audio_record);
          break;
        case STATE_RECORDING:
          setText(R.string.audio_recording);
          if (isRecording) {
            mDialogManager.recording();
          }
          break;
        case STATE_CANCEL:
          setText(R.string.audio_cancel);
          mDialogManager.wantToCancel();
          break;
      }
    }
  }

  //录音完成回调
  public interface  onAudioFinishRecordListener{
    void onFinishRecord(float seconds,String filePath);
  }

  public void setMonAudioFinishRecordListener(
      onAudioFinishRecordListener monAudioFinishRecordListener) {
    this.monAudioFinishRecordListener = monAudioFinishRecordListener;
  }
}
