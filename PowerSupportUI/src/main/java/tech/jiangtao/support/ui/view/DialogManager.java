package tech.jiangtao.support.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import tech.jiangtao.support.ui.R;

/**
 * Class: DialogManager </br>
 * Description: 聊天语音对话框管理 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 2:33 PM</br>
 * Update: 24/12/2016 2:33 PM </br>
 **/

public class DialogManager {

  private Dialog mDialog;
  private ImageView mImageRecord;
  private ImageView mImageVoice;
  private TextView mTextViewLabel;
  private Context mContext;

  public DialogManager(Context context) {
    mContext = context;
  }

  public void showRecordingDialog() {
    mDialog = new Dialog(mContext, R.style.AppTheme_AudioDialog);
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.dialog_audio_record, null);
    mDialog.setContentView(view);
    mImageRecord = (ImageView) view.findViewById(R.id.dialog_audio);
    mImageVoice = (ImageView) view.findViewById(R.id.dialog_audio_level);
    mTextViewLabel = (TextView) view.findViewById(R.id.dialog_text);
    mDialog.show();
  }

  public void recording() {
    if (mDialog != null && mDialog.isShowing()) {
      mImageRecord.setVisibility(View.VISIBLE);
      mImageVoice.setVisibility(View.VISIBLE);
      mTextViewLabel.setVisibility(View.VISIBLE);
      mImageRecord.setImageResource(R.mipmap.ic_recorder);
      mTextViewLabel.setText(R.string.dialog_up_cancel);
    }
  }

  public void wantToCancel() {
    if (mDialog != null && mDialog.isShowing()) {
      mImageRecord.setVisibility(View.VISIBLE);
      mImageVoice.setVisibility(View.GONE);
      mTextViewLabel.setVisibility(View.VISIBLE);
      mImageRecord.setImageResource(R.mipmap.ic_audio_cancel);
      mTextViewLabel.setText(R.string.dialog_dismiss_cancel);
    }
  }

  public void tooShort() {
    if (mDialog != null && mDialog.isShowing()) {
      mImageRecord.setVisibility(View.VISIBLE);
      mImageVoice.setVisibility(View.GONE);
      mTextViewLabel.setVisibility(View.VISIBLE);
      mImageRecord.setImageResource(R.mipmap.ic_voice_to_short);
      mTextViewLabel.setText(R.string.dialog_too_short);
    }
  }

  public void dismissDialog() {
    if (mDialog != null && mDialog.isShowing()) {
      mDialog.dismiss();
      mDialog = null;
    }
  }

  public void updateVoiceLevel(int level) {
    if (mDialog != null && mDialog.isShowing()) {
      mImageRecord.setVisibility(View.VISIBLE);
      mImageVoice.setVisibility(View.VISIBLE);
      mTextViewLabel.setVisibility(View.VISIBLE);
      int resId = mContext.getResources()
          .getIdentifier("ic_audio_v" + level, "mipmap", mContext.getPackageName());
      mImageVoice.setImageResource(resId);
    }
  }
}
