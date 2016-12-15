package tech.jiangtao.support.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.melink.bqmmsdk.ui.keyboard.BQMMKeyboard;
import com.melink.bqmmsdk.widget.BQMMEditView;
import com.melink.bqmmsdk.widget.BQMMSendButton;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleArchiveMessage;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ChatMessageAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;

import static android.content.ContentValues.TAG;
import static tech.jiangtao.support.kit.service.SupportService.requestAllMessageArchive;

/**
 * Class: ChatFragment </br>
 * Description: 聊天页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:40 AM</br>
 * Update: 02/12/2016 11:40 AM </br>
 **/
public class ChatFragment extends BaseFragment
    implements TextWatcher, View.OnClickListener, EasyViewHolder.OnItemClickListener,
    View.OnLongClickListener {

  @BindView(R2.id.recycler) RecyclerView mRecycler;
  @BindView(R2.id.swift_refresh) SwipeRefreshLayout mSwiftRefresh;
  @BindView(R2.id.chat_speak) ImageView mChatSpeak;
  @BindView(R2.id.chat_add_other_information) ImageView mChatAddOtherInformation;
  @BindView(R2.id.chat_send_message) BQMMSendButton mChatSendMessage;
  @BindView(R2.id.container_send) FrameLayout mContainerSend;
  @BindView(R2.id.add_smile) CheckBox mAddSmile;
  @BindView(R2.id.chat_input) BQMMEditView mChatInput;
  @BindView(R2.id.chat_inline_container) RelativeLayout mChatInlineContainer;
  @BindView(R2.id.chat_msg_input_box) BQMMKeyboard mChatMsgInputBox;
  @BindView(R2.id.chat_send_other) RecyclerView mChatSendOther;
  @BindView(R2.id.chat_bottom) RelativeLayout mChatBottom;
  private ChatMessageAdapter mChatMessageAdapter;
  private List<ConstructMessage> mMessages;
  private VCardRealm mVCardRealm;
  private VCardRealm mOwnVCardRealm;
  private Realm mRealm;

  public static ChatFragment newInstance() {
    return new ChatFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    init();
    mChatSendMessage.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String message = mChatInput.getText().toString();
        TextMessage message1 =
            new TextMessage(org.jivesoftware.smack.packet.Message.Type.chat, mVCardRealm.getJid(),
                message);
        EventBus.getDefault().post(message1);
        Message message2 = new Message();
        message2.paramContent = mChatInput.getText().toString();
        mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
            .avatar(mOwnVCardRealm != null ? mOwnVCardRealm.getAvatar() : null)
            .message(message2)
            .build());
        mChatMessageAdapter.notifyDataSetChanged();
        mChatInput.setText("");
        mChatInput.clearFocus();
      }
    });
    ButterKnife.bind(this, getView());
    return getView();
  }

  public void loadOwnRealm() {
    mRealm = Realm.getDefaultInstance();
    RealmResults<VCardRealm> realms = mRealm.where(VCardRealm.class)
        .equalTo("jid", StringSplitUtil.splitDivider(SupportService.getmXMPPConnection().getUser()))
        .findAll();
    if (realms.size() != 0) {
      mOwnVCardRealm = realms.first();
    }
  }

  @Override public int layout() {
    return R.layout.fragment_chat;
  }

  private void init() {
    mVCardRealm = getArguments().getParcelable("vCard");
    loadOwnRealm();
    setViewListener();
    setAdapter();
    loadArchiveMessage();
  }

  private void setViewListener() {
    mChatInput.addTextChangedListener(this);
    mChatSpeak.setOnLongClickListener(this);
    mChatAddOtherInformation.setOnClickListener(this);
    mChatSendMessage.setOnClickListener(this);
    mAddSmile.setOnClickListener(this);
  }

  public void setAdapter() {
    mMessages = new ArrayList<>();
    mChatMessageAdapter = new ChatMessageAdapter(getContext(), mMessages);
    mRecycler.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mRecycler.setAdapter(mChatMessageAdapter);
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (s == null || Objects.equals(s.toString(), "")) {
      Log.e(TAG, "onTextChanged: ");
      mChatSendMessage.setVisibility(View.GONE);
      mChatAddOtherInformation.setVisibility(View.VISIBLE);
    } else {
      mChatAddOtherInformation.setVisibility(View.GONE);
      mChatSendMessage.setVisibility(View.VISIBLE);
    }
  }

  public void loadArchiveMessage() {
    if (mVCardRealm != null) {
      SimpleArchiveMessage mSimpleArchiveMessage = new SimpleArchiveMessage();
      MessageRealm messageRealm = null;
      RealmResults<MessageRealm> messageDatas =
          mSimpleArchiveMessage.loadArchiveMessage(mVCardRealm.getJid());
      int size = messageDatas.size();
      Log.d(TAG, "loadArchiveMessage: " + size);
      if (size > 0) {
        for (int i = 0; i < size; i++) {
          //根据数据判断，然后加载
          messageRealm = messageDatas.get(i);
          addMessageToAdapter(messageRealm);
        }
      }
    }
  }

  @Override public void afterTextChanged(Editable s) {

  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(RecieveMessage message) {
    Log.d("----------->", "onMessage: " + message);
    // 根据消息类型，作出调转服务
    if (message.message != null && !String.valueOf(message.message).equals("")) {
      Message message1 = new Message();
      message1.paramContent = (String) message.message;
      mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
          .avatar(mVCardRealm.getAvatar())
          .message(message1)
          .build());
      mChatMessageAdapter.notifyDataSetChanged();
    }
  }

  @Override public void onStop() {
    super.onStop();
    mRealm.close();
  }

  public void addMessageToAdapter(MessageRealm realm) {
    Message message1 = new Message();
    message1.paramContent = realm.getTextMessage();
    Log.d(TAG, "addMessageToAdapter: " + realm.getMainJID());
    Log.d(TAG, "addMessageToAdapter-----: " + StringSplitUtil.splitDivider(mVCardRealm.getJid()));
    if (StringSplitUtil.splitDivider(realm.getMainJID())
        .equals(StringSplitUtil.splitDivider(mVCardRealm.getJid()))) {
      mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
          .avatar(mVCardRealm != null ? mVCardRealm.getAvatar() : null)
          .message(message1)
          .build());
    } else {
      mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
          .avatar(mOwnVCardRealm != null ? mOwnVCardRealm.getAvatar() : null)
          .message(message1)
          .build());
    }
    mChatMessageAdapter.notifyDataSetChanged();
  }

  @Override public void onPause() {
    super.onPause();
    SimpleArchiveMessage message = new SimpleArchiveMessage();
    requestAllMessageArchive(message.getLastUpdateTime());
  }

  @Override public void onItemClick(int position, View view) {

  }

  @Override public boolean onLongClick(View v) {
    switch (v.getId()){
      case R2.id.chat_speak:
        break;
    }
    return true;
  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R2.id.chat_add_other_information:
        break;
      case R2.id.chat_send_message:
        break;
      case R2.id.add_smile:
        break;
    }
  }
}
