package tech.jiangtao.support.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.kevin.library.widget.CleanDialog;
import com.kevin.library.widget.builder.IconFlag;

import io.realm.Realm;
import io.realm.RealmResults;

import java.util.Iterator;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

import java.util.ArrayList;
import java.util.List;

import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.activity.ChatActivity;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.adapter.SessionAdapter;
import tech.jiangtao.support.ui.linstener.ContactItemCallback;
import tech.jiangtao.support.ui.model.type.TransportType;
import tech.jiangtao.support.ui.pattern.SessionListMessage;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.utils.ResourceAddress;

/**
 * Class: ChatListFragment </br>
 * Description: 聊天列表 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:38 AM</br>
 * Update: 02/12/2016 11:38 AM </br>
 **/
public class ChatListFragment extends BaseFragment
    implements EasyViewHolder.OnItemClickListener, EasyViewHolder.OnItemLongClickListener,
    SwipeRefreshLayout.OnRefreshListener {

  public static final String TAG = Fragment.class.getSimpleName();
  @BindView(R2.id.chat_list) RecyclerView mChatList;
  @BindView(R2.id.chat_swift_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R2.id.chat_frame) ImageView mImageView;
  private SessionAdapter mSessionAdapter;
  private List<SessionListMessage> mSessionMessage;
  private Realm mRealm;
  private RealmResults<SessionRealm> mSessionRealm;
  private ContactItemCallback mContactItemCallback;
  private Drawable mDrawable;
  private AppPreferences mAppPreferences;

  public static ChatListFragment newInstance() {
    return new ChatListFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    LogUtils.d(TAG, "onCreateView: 创建view");
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mAppPreferences = new AppPreferences(getContext());
    setRefresh();
    setAdapter();
    return getView();
  }

  private void setRefresh() {
    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
    mSwipeRefreshLayout.setDistanceToTriggerSync(300);
    mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
    mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
    mSwipeRefreshLayout.setOnRefreshListener(this);
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
      String userId = null;
      try {
        userId = mAppPreferences.getString(SupportIM.USER_ID);
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
      }
      mSessionRealm =
          realm.where(SessionRealm.class).notEqualTo(SupportIM.SENDERFRIENDID, userId).findAll();
      Iterator<SessionRealm> it = mSessionRealm.iterator();
      LogUtils.d(TAG, "getChatList: 数量为" + mSessionRealm.size());
      while (it.hasNext()) {
        frameImage(false);
        SessionRealm sessionRealms = it.next();
        RealmResults<ContactRealm> contactRealms = realm.where(ContactRealm.class)
            .equalTo(SupportIM.USER_ID,
                StringSplitUtil.splitDivider(sessionRealms.getSenderFriendId()))
            .findAll();
        ContactRealm contactRealm = new ContactRealm();
        if (contactRealms.size() != 0) {
          contactRealm = contactRealms.first();
        }
        RealmResults<MessageRealm> message = realm.where(MessageRealm.class)
            .equalTo(SupportIM.MESSAGE_ID, sessionRealms.getMessageId())
            .findAll();
        if (message.first().getMessageType().equals(DataExtensionType.TEXT.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(sessionRealms.unReadCount)
                  .sessionId(sessionRealms.getSessionId())
                  .userJid(sessionRealms.getSenderFriendId())
                  .username(contactRealm.getNickName())
                  .unReadMessage(message.first().textMessage)
                  .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                  .build());
        }
        if (message.first().getMessageType().equals(DataExtensionType.IMAGE.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(sessionRealms.unReadCount)
                  .sessionId(sessionRealms.getSessionId())
                  .userJid(sessionRealms.getSenderFriendId())
                  .username(contactRealm.getNickName())
                  .unReadMessage("图片")
                  .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                  .build());
        }
        if (message.first().getMessageType().equals(DataExtensionType.AUDIO.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(sessionRealms.unReadCount)
                  .sessionId(sessionRealms.getSessionId())
                  .userJid(sessionRealms.getSenderFriendId())
                  .username(contactRealm.getNickName())
                  .unReadMessage("语音")
                  .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                  .build());
        }
        if (message.first().getMessageType().equals(DataExtensionType.VIDEO.toString())) {
          mSessionMessage.add(
              new SessionListMessage.Builder().unReadMessageCount(sessionRealms.unReadCount)
                  .sessionId(sessionRealms.getSessionId())
                  .userJid(sessionRealms.getSenderFriendId())
                  .username(contactRealm.getNickName())
                  .unReadMessage("视频")
                  .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                  .build());
        }
        mSessionAdapter.notifyDataSetChanged();
      }
      mSessionRealm.addChangeListener(element -> {
        Iterator<SessionRealm> iterator = element.iterator();
        LogUtils.d(TAG, "getChatList: 会话数量" + element.size());
        mSessionMessage.clear();
        while (iterator.hasNext()) {
          frameImage(false);
          SessionRealm sessionRealm = iterator.next();
          //这儿需要查一下MessageRealm和VCardRealm;
          RealmResults<ContactRealm> contactRealms = realm.where(ContactRealm.class)
              .equalTo("userId", StringSplitUtil.splitDivider(sessionRealm.getSenderFriendId()))
              .findAll();
          ContactRealm contactRealm = new ContactRealm();
          if (contactRealms.size() != 0) {
            contactRealm = contactRealms.first();
          }
          RealmResults<MessageRealm> message =
              realm.where(MessageRealm.class).equalTo("id", sessionRealm.getMessageId()).findAll();
          if (message.first().getMessageType().equals(DataExtensionType.TEXT.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(sessionRealm.unReadCount)
                    .sessionId(sessionRealm.getSessionId())
                    .userJid(sessionRealm.getSenderFriendId())
                    .username(contactRealm.getNickName())
                    .unReadMessage(message.first().textMessage)
                    .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                    .build());
          }
          if (message.first().getMessageType().equals(DataExtensionType.IMAGE.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(sessionRealm.unReadCount)
                    .sessionId(sessionRealm.getSessionId())
                    .userJid(sessionRealm.getSenderFriendId())
                    .username(contactRealm.getNickName())
                    .unReadMessage("图片")
                    .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                    .build());
          }
          if (message.first().getMessageType().equals(DataExtensionType.AUDIO.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(sessionRealm.unReadCount)
                    .sessionId(sessionRealm.getSessionId())
                    .userJid(sessionRealm.getSenderFriendId())
                    .username(contactRealm.getNickName())
                    .unReadMessage("语音")
                    .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                    .build());
          }
          if (message.first().getMessageType().equals(DataExtensionType.VIDEO.toString())) {
            mSessionMessage.add(
                new SessionListMessage.Builder().unReadMessageCount(sessionRealm.unReadCount)
                    .sessionId(sessionRealm.getSessionId())
                    .userJid(sessionRealm.getSenderFriendId())
                    .username(contactRealm.getNickName())
                    .unReadMessage("视频")
                    .avatar(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
                    .build());
          }
          mSessionAdapter.notifyDataSetChanged();
        }
      });
    });
  }

  @Deprecated
  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(RecieveMessage message) {

  }

  @Override public void onItemClick(int position, View view) {
    //获得每一项的用户信息,
    LogUtils.d(TAG, "onItemClick: 点击了第" + position + "项");
    SessionListMessage messageRealm = mSessionMessage.get(position);
    RealmResults<ContactRealm> vCardRealms = mRealm.where(ContactRealm.class)
        .equalTo(SupportIM.USER_ID, StringSplitUtil.splitDivider(messageRealm.userJid))
        .findAll();
    ContactRealm vCardRealm = new ContactRealm();
    if (vCardRealms.size() != 0) {
      vCardRealm = vCardRealms.first();
    }
    ChatActivity.startChat((Activity) getContext(), vCardRealm);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    LogUtils.d(TAG, "onDestroyView: 销毁view");
    mRealm.close();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContactItemCallback = (ContactItemCallback) context;
  }

  @Override public boolean onItemLongClick(int position, View view) {
    final CleanDialog dialog = new CleanDialog.Builder(getContext()).iconFlag(IconFlag.WARN)
        .negativeButton("取消", cleanDialog -> cleanDialog.dismiss())
        .positiveButton("删除", dialog1 -> {
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
        })
        .title("确认删除当前消息吗?")
        .negativeTextColor(Color.WHITE)
        .positiveTextColor(Color.WHITE)
        .builder();
    dialog.showDialog();
    return false;
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    }, 3000);
  }

  /**
   * 设置背景
   */
  public void frameImage(boolean isShow) {
    if (isShow) {
      if (mDrawable != null) {
        mImageView.setImageDrawable(mDrawable);
      }
      mImageView.setVisibility(View.VISIBLE);
    } else {
      mImageView.setVisibility(View.GONE);
    }
  }

  public void setmDrawable(Drawable mDrawable) {
    this.mDrawable = mDrawable;
  }
}
