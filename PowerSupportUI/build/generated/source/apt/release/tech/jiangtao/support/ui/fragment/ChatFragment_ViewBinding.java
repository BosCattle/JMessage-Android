// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.melink.bqmmsdk.ui.keyboard.BQMMKeyboard;
import com.melink.bqmmsdk.widget.BQMMEditView;
import com.melink.bqmmsdk.widget.BQMMSendButton;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.view.AudioRecordButton;

public class ChatFragment_ViewBinding implements Unbinder {
  private ChatFragment target;

  private View view2131558583;

  private View view2131558585;

  private View view2131558586;

  private View view2131558587;

  private View view2131558588;

  @UiThread
  public ChatFragment_ViewBinding(final ChatFragment target, View source) {
    this.target = target;

    View view;
    target.mRecycler = Utils.findRequiredViewAsType(source, R.id.recycler, "field 'mRecycler'", RecyclerView.class);
    target.mSwiftRefresh = Utils.findRequiredViewAsType(source, R.id.swift_refresh, "field 'mSwiftRefresh'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.chat_speak, "field 'mChatSpeak' and method 'onClick'");
    target.mChatSpeak = Utils.castView(view, R.id.chat_speak, "field 'mChatSpeak'", ImageView.class);
    view2131558583 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.chat_add_other_information, "field 'mChatAddOtherInformation' and method 'onClick'");
    target.mChatAddOtherInformation = Utils.castView(view, R.id.chat_add_other_information, "field 'mChatAddOtherInformation'", ImageView.class);
    view2131558585 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.chat_send_message, "field 'mChatSendMessage' and method 'onClick'");
    target.mChatSendMessage = Utils.castView(view, R.id.chat_send_message, "field 'mChatSendMessage'", BQMMSendButton.class);
    view2131558586 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mContainerSend = Utils.findRequiredViewAsType(source, R.id.container_send, "field 'mContainerSend'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.add_smile, "field 'mAddSmile' and method 'onClick'");
    target.mAddSmile = Utils.castView(view, R.id.add_smile, "field 'mAddSmile'", CheckBox.class);
    view2131558587 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mChatInput = Utils.findRequiredViewAsType(source, R.id.chat_input, "field 'mChatInput'", BQMMEditView.class);
    target.mChatInlineContainer = Utils.findRequiredViewAsType(source, R.id.chat_inline_container, "field 'mChatInlineContainer'", RelativeLayout.class);
    target.mChatMsgInputBox = Utils.findRequiredViewAsType(source, R.id.chat_msg_input_box, "field 'mChatMsgInputBox'", BQMMKeyboard.class);
    target.mChatSendOther = Utils.findRequiredViewAsType(source, R.id.chat_send_other, "field 'mChatSendOther'", RecyclerView.class);
    target.mChatBottom = Utils.findRequiredViewAsType(source, R.id.chat_bottom, "field 'mChatBottom'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.chat_audio_record, "field 'mAudioRecord' and method 'onClick'");
    target.mAudioRecord = Utils.castView(view, R.id.chat_audio_record, "field 'mAudioRecord'", AudioRecordButton.class);
    view2131558588 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecycler = null;
    target.mSwiftRefresh = null;
    target.mChatSpeak = null;
    target.mChatAddOtherInformation = null;
    target.mChatSendMessage = null;
    target.mContainerSend = null;
    target.mAddSmile = null;
    target.mChatInput = null;
    target.mChatInlineContainer = null;
    target.mChatMsgInputBox = null;
    target.mChatSendOther = null;
    target.mChatBottom = null;
    target.mAudioRecord = null;

    view2131558583.setOnClickListener(null);
    view2131558583 = null;
    view2131558585.setOnClickListener(null);
    view2131558585 = null;
    view2131558586.setOnClickListener(null);
    view2131558586 = null;
    view2131558587.setOnClickListener(null);
    view2131558587 = null;
    view2131558588.setOnClickListener(null);
    view2131558588 = null;
  }
}
