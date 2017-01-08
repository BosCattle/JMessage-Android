package tech.jiangtao.support.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevin.library.widget.CleanDialog;
import com.kevin.library.widget.builder.IconFlag;
import com.kevin.library.widget.builder.PositiveClickListener;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.Iterator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

import java.util.ArrayList;
import java.util.List;

import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.RosterEntryBus;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.activity.ChatActivity;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.adapter.SessionAdapter;
import tech.jiangtao.support.ui.linstener.ContactItemCallback;
import tech.jiangtao.support.ui.pattern.SessionListMessage;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;

/**
 * Class: ChatListFragment </br>
 * Description: 聊天列表 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:38 AM</br>
 * Update: 02/12/2016 11:38 AM </br>
 **/
public class ChatListFragment extends BaseFragment
    implements EasyViewHolder.OnItemClickListener, EasyViewHolder.OnItemLongClickListener {

  public static final String TAG = Fragment.class.getSimpleName();
  @BindView(R2.id.chat_list) RecyclerView mChatList;
  private SessionAdapter mSessionAdapter;
  private List<SessionListMessage> mSessionMessage;
  private Realm mRealm;
  private RealmResults<SessionRealm> mSessionRealm;
  private ContactItemCallback mContactItemCallback;

  public static ChatListFragment newInstance() {
    return new ChatListFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    Log.d(TAG, "onCreateView: 创建view");
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    setAdapter();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_single_chat;
  }

  public void setAdapter() {
    mSessionMessage = new ArrayList<>();
    mSessionAdapter = new SessionAdapter(getContext(), mSessionMessage);
    mSessionAdapter.setOnClickListener(this);
    mSessionAdapter.setOnLongClickListener(this);
    mChatList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mChatList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mChatList.setAdapter(mSessionAdapter);
    getChatList();
  }

  @Override public void onResume() {
    super.onResume();
  }

  private void getChatList() {
    mRealm.executeTransaction(realm -> {
      mSessionRealm = realm.where(SessionRealm.class).findAll();
      Iterator<SessionRealm> it = mSessionRealm.iterator();
      Log.d(TAG, "getChatList: 数量为" + mSessionRealm.size());
      while (it.hasNext()) {
        SessionRealm messageRealm = it.next();
        //这儿需要查一下MessageRealm和VCardRealm;
        RealmResults<VCardRealm> vCardRealms = realm.where(VCardRealm.class)
            .equalTo("jid", StringSplitUtil.splitDivider(messageRealm.getVcard_id()))
            .findAll();
        VCardRealm vCardRealm = new VCardRealm();
        if (vCardRealms.size() != 0) {
          vCardRealm = vCardRealms.first();
        }
        RealmResults<MessageRealm> message =
            realm.where(MessageRealm.class).equalTo("id", messageRealm.getMessage_id()).findAll();
        if (message.first().getMessageType().equals(MessageExtensionType.TEXT.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                  .sessionId(messageRealm.getSession_id())
                  .userJid(messageRealm.getVcard_id())
                  .username(vCardRealm.getNickName())
                  .unReadMessage(message.first().textMessage)
                  .avatar(vCardRealm.getAvatar())
                  .build());
        }
        if (message.first().getMessageType().equals(MessageExtensionType.IMAGE.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                  .sessionId(messageRealm.getSession_id())
                  .userJid(messageRealm.getVcard_id())
                  .username(vCardRealm.getNickName())
                  .unReadMessage("图片")
                  .avatar(vCardRealm.getAvatar())
                  .build());
        }
        if (message.first().getMessageType().equals(MessageExtensionType.AUDIO.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                  .sessionId(messageRealm.getSession_id())
                  .userJid(messageRealm.getVcard_id())
                  .username(vCardRealm.getNickName())
                  .unReadMessage("语音")
                  .avatar(vCardRealm.getAvatar())
                  .build());
        }
        if (message.first().getMessageType().equals(MessageExtensionType.VIDEO.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                  .sessionId(messageRealm.getSession_id())
                  .userJid(messageRealm.getVcard_id())
                  .username(vCardRealm.getNickName())
                  .unReadMessage("视频")
                  .avatar(vCardRealm.getAvatar())
                  .build());
        }
        mSessionAdapter.notifyDataSetChanged();
      }
      mSessionRealm.addChangeListener(element -> {
        Iterator<SessionRealm> iterator = element.iterator();
        Log.d(TAG, "getChatList: 会话数量" + element.size());
        mSessionMessage.clear();
        while (iterator.hasNext()) {
          SessionRealm messageRealm = iterator.next();
          //这儿需要查一下MessageRealm和VCardRealm;
          RealmResults<VCardRealm> vCardRealms = realm.where(VCardRealm.class)
              .equalTo("jid", StringSplitUtil.splitDivider(messageRealm.getVcard_id()))
              .findAll();
          VCardRealm vCardRealm = new VCardRealm();
          if (vCardRealms.size() != 0) {
            vCardRealm = vCardRealms.first();
          }
          RealmResults<MessageRealm> message =
              realm.where(MessageRealm.class).equalTo("id", messageRealm.getMessage_id()).findAll();
          if (message.first().getMessageType().equals(MessageExtensionType.TEXT.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                    .sessionId(messageRealm.getSession_id())
                    .userJid(messageRealm.getVcard_id())
                    .username(vCardRealm.getNickName())
                    .unReadMessage(message.first().textMessage)
                    .avatar(vCardRealm.getAvatar())
                    .build());
          }
          if (message.first().getMessageType().equals(MessageExtensionType.IMAGE.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                    .sessionId(messageRealm.getSession_id())
                    .userJid(messageRealm.getVcard_id())
                    .username(vCardRealm.getNickName())
                    .unReadMessage("图片")
                    .avatar(vCardRealm.getAvatar())
                    .build());
          }
          if (message.first().getMessageType().equals(MessageExtensionType.AUDIO.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                    .sessionId(messageRealm.getSession_id())
                    .userJid(messageRealm.getVcard_id())
                    .username(vCardRealm.getNickName())
                    .unReadMessage("语音")
                    .avatar(vCardRealm.getAvatar())
                    .build());
          }
          if (message.first().getMessageType().equals(MessageExtensionType.VIDEO.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                    .sessionId(messageRealm.getSession_id())
                    .userJid(messageRealm.getVcard_id())
                    .username(vCardRealm.getNickName())
                    .unReadMessage("视频")
                    .avatar(vCardRealm.getAvatar())
                    .build());
          }
          mSessionAdapter.notifyDataSetChanged();
        }
      });
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(RecieveMessage message) {

  }

  @Override public void onItemClick(int position, View view) {
    //获得每一项的用户信息,
    Log.d(TAG, "onItemClick: 点击了第" + position + "项");
    SessionListMessage messageRealm = mSessionMessage.get(position);
    RealmResults<VCardRealm> vCardRealms = mRealm.where(VCardRealm.class)
        .equalTo("jid", StringSplitUtil.splitDivider(messageRealm.userJid))
        .findAll();
    VCardRealm vCardRealm = new VCardRealm();
    if (vCardRealms.size() != 0) {
      vCardRealm = vCardRealms.first();
    }
    ChatActivity.startChat((Activity) getContext(),vCardRealm);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    Log.d(TAG, "onDestroyView: 销毁view");
    mRealm.close();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContactItemCallback = (ContactItemCallback) context;
  }

  @Override public boolean onItemLongClick(int position, View view) {
    final CleanDialog dialog = new CleanDialog.Builder(getContext()).iconFlag(IconFlag.WARN)
        .negativeButton("取消", Dialog::dismiss)
        .positiveButton("删除", new PositiveClickListener() {
          @Override public void onPositiveClickListener(CleanDialog dialog1) {
            //删除会话
            mRealm.executeTransactionAsync(realm -> {
              SessionListMessage message = mSessionMessage.get(position);
              RealmResults<SessionRealm> sessionRealms =
                  realm.where(SessionRealm.class).equalTo("session_id", message.sessionId).findAll();
              if (sessionRealms.size() != 0) {
                sessionRealms.deleteFirstFromRealm();
              }
            });
            dialog1.dismiss();
          }
        })
        .title("确认删除当前消息吗?")
        .negativeTextColor(Color.WHITE)
        .positiveTextColor(Color.WHITE)
        .builder();
    dialog.showDialog();
    return false;
  }
}
