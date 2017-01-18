package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.melink.bqmmsdk.ui.keyboard.BQMMKeyboard;
import com.melink.bqmmsdk.widget.BQMMEditView;
import com.melink.bqmmsdk.widget.BQMMSendButton;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.view.AudioRecordButton;

/**
 * Class: GroupChatActivity </br>
 * Description: 群聊页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/01/2017 11:54 PM</br>
 * Update: 18/01/2017 11:54 PM </br>
 **/
public class GroupChatActivity extends BaseActivity {

  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.group_recycler) RecyclerView mGroupRecycler;
  @BindView(R2.id.group_swift_refresh) SwipeRefreshLayout mGroupSwiftRefresh;
  @BindView(R2.id.group_chat_speak) ImageView mGroupChatSpeak;
  @BindView(R2.id.group_chat_add_other_information) ImageView mGroupChatAddOtherInformation;
  @BindView(R2.id.group_chat_send_message) BQMMSendButton mGroupChatSendMessage;
  @BindView(R2.id.group_container_send) FrameLayout mGroupContainerSend;
  @BindView(R2.id.group_add_smile) CheckBox mGroupAddSmile;
  @BindView(R2.id.group_chat_audio_record) AudioRecordButton mGroupChatAudioRecord;
  @BindView(R2.id.group_chat_input) BQMMEditView mGroupChatInput;
  @BindView(R2.id.group_chat_inline_container) RelativeLayout mGroupChatInlineContainer;
  @BindView(R2.id.group_chat_msg_input_box) BQMMKeyboard mGroupChatMsgInputBox;
  @BindView(R2.id.group_chat_send_other) RecyclerView mGroupChatSendOther;
  @BindView(R2.id.group_chat_bottom) RelativeLayout mGroupChatBottom;
  @BindView(R2.id.activity_group_chat) RelativeLayout mActivityGroupChat;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_chat);
    ButterKnife.bind(this);
    setUpToolbar();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("某某人的群聊");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(
        v -> ActivityCompat.finishAfterTransition(GroupChatActivity.this));
  }

  public static void startGroupChat(Context context) {
    Intent intent = new Intent(context, GroupChatActivity.class);
    context.startActivity(intent);
  }
}
