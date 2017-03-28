// Generated code from Butter Knife. Do not modify!
package tech.jiangtao.support.ui.activity;

import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.melink.bqmmsdk.ui.keyboard.BQMMKeyboard;
import com.melink.bqmmsdk.widget.BQMMEditView;
import com.melink.bqmmsdk.widget.BQMMSendButton;
import java.lang.IllegalStateException;
import java.lang.Override;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.view.AudioRecordButton;

public class GroupChatActivity_ViewBinding extends BaseActivity_ViewBinding {
  private GroupChatActivity target;

  @UiThread
  public GroupChatActivity_ViewBinding(GroupChatActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupChatActivity_ViewBinding(GroupChatActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mGroupRecycler = Utils.findRequiredViewAsType(source, R.id.group_recycler, "field 'mGroupRecycler'", RecyclerView.class);
    target.mGroupSwiftRefresh = Utils.findRequiredViewAsType(source, R.id.group_swift_refresh, "field 'mGroupSwiftRefresh'", SwipeRefreshLayout.class);
    target.mGroupChatSpeak = Utils.findRequiredViewAsType(source, R.id.group_chat_speak, "field 'mGroupChatSpeak'", ImageView.class);
    target.mGroupChatAddOtherInformation = Utils.findRequiredViewAsType(source, R.id.group_chat_add_other_information, "field 'mGroupChatAddOtherInformation'", ImageView.class);
    target.mGroupChatSendMessage = Utils.findRequiredViewAsType(source, R.id.group_chat_send_message, "field 'mGroupChatSendMessage'", BQMMSendButton.class);
    target.mGroupContainerSend = Utils.findRequiredViewAsType(source, R.id.group_container_send, "field 'mGroupContainerSend'", FrameLayout.class);
    target.mGroupAddSmile = Utils.findRequiredViewAsType(source, R.id.group_add_smile, "field 'mGroupAddSmile'", CheckBox.class);
    target.mGroupChatAudioRecord = Utils.findRequiredViewAsType(source, R.id.group_chat_audio_record, "field 'mGroupChatAudioRecord'", AudioRecordButton.class);
    target.mGroupChatInput = Utils.findRequiredViewAsType(source, R.id.group_chat_input, "field 'mGroupChatInput'", BQMMEditView.class);
    target.mGroupChatInlineContainer = Utils.findRequiredViewAsType(source, R.id.group_chat_inline_container, "field 'mGroupChatInlineContainer'", RelativeLayout.class);
    target.mGroupChatMsgInputBox = Utils.findRequiredViewAsType(source, R.id.group_chat_msg_input_box, "field 'mGroupChatMsgInputBox'", BQMMKeyboard.class);
    target.mGroupChatSendOther = Utils.findRequiredViewAsType(source, R.id.group_chat_send_other, "field 'mGroupChatSendOther'", RecyclerView.class);
    target.mGroupChatBottom = Utils.findRequiredViewAsType(source, R.id.group_chat_bottom, "field 'mGroupChatBottom'", RelativeLayout.class);
    target.mActivityGroupChat = Utils.findRequiredViewAsType(source, R.id.activity_group_chat, "field 'mActivityGroupChat'", RelativeLayout.class);
  }

  @Override
  public void unbind() {
    GroupChatActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mGroupRecycler = null;
    target.mGroupSwiftRefresh = null;
    target.mGroupChatSpeak = null;
    target.mGroupChatAddOtherInformation = null;
    target.mGroupChatSendMessage = null;
    target.mGroupContainerSend = null;
    target.mGroupAddSmile = null;
    target.mGroupChatAudioRecord = null;
    target.mGroupChatInput = null;
    target.mGroupChatInlineContainer = null;
    target.mGroupChatMsgInputBox = null;
    target.mGroupChatSendOther = null;
    target.mGroupChatBottom = null;
    target.mActivityGroupChat = null;

    super.unbind();
  }
}
