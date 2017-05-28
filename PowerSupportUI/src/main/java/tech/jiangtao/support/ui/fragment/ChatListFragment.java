package tech.jiangtao.support.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import java.lang.annotation.Annotation;

import butterknife.BindView;

import java.util.ArrayList;
import java.util.List;

import tech.jiangtao.support.kit.annotation.ChatRouter;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.manager.IMConversationManager;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.adapter.SessionAdapter;
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
    implements EasyViewHolder.OnItemClickListener, EasyViewHolder.OnItemLongClickListener,
    SwipeRefreshLayout.OnRefreshListener, IMListenerCollection.IMConversationChangeListener {

  public static final String TAG = Fragment.class.getSimpleName();
  @BindView(R2.id.chat_list) RecyclerView mChatList;
  @BindView(R2.id.chat_swift_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R2.id.chat_frame) ImageView mImageView;
  private SessionAdapter mSessionAdapter;
  private List<SessionRealm> mSessionMessage;
  private Realm mRealm;
  private Drawable mDrawable;
  private Class mGroupClazz;
  private Class mChatClass;

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
    setRefresh();
    setAdapter();
    IMConversationManager.geInstance().queryConversations(ChatListFragment.this);
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
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onItemClick(int position, View view) {
    //获得每一项的用户信息,
    Class<?> activity = getActivity().getClass();
    Annotation[] annotation = activity.getAnnotations();
    for (int i = 0; i < annotation.length; i++) {
      if (annotation[i] instanceof GroupChatRouter) {
        GroupChatRouter annomation = (GroupChatRouter) annotation[i];
        mGroupClazz = annomation.router();
      }
      if (annotation[i] instanceof ChatRouter) {
        ChatRouter chatRouter = (ChatRouter) annotation[i];
        mChatClass = chatRouter.router();
      }
    }
    SessionRealm sessionRealm = mSessionMessage.get(position);
    // 单聊
    if (sessionRealm.getMessageType() == 0) {
      Intent intent = new Intent(getContext(), mChatClass);
      intent.putExtra(SupportIM.VCARD, sessionRealm.getContactRealm());
      startActivity(intent);
    } else if (sessionRealm.getMessageType() == 1) {
      Intent intent = new Intent(getContext(), mGroupClazz);
      intent.putExtra(SupportIM.GROUP, sessionRealm.getGroupRealm());
      startActivity(intent);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    LogUtils.d(TAG, "onDestroyView: 销毁view");
    mRealm.close();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override public boolean onItemLongClick(int position, View view) {
    final CleanDialog dialog = new CleanDialog.Builder(getContext()).iconFlag(IconFlag.WARN)
        .negativeButton("取消", Dialog::dismiss)
        .positiveButton("删除", dialog1 -> {
          IMConversationManager.geInstance().deleteConversation(mSessionMessage.get(position), new IMListenerCollection.IMConversationDeleteListener() {
            @Override public void success() {
              LogUtils.d("------->","删除成功");
              dialog1.dismiss();
            }

            @Override public void error(Result result) {

            }
          });
        })
        .title("确认删除当前消息吗?")
        .negativeTextColor(Color.WHITE)
        .positiveTextColor(Color.WHITE)
        .builder();
    dialog.showDialog();
    return false;
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 3000);
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

  @Override public void change(List<SessionRealm> sessionRealms) {
    frameImage(false);
    mSessionAdapter.clear();
    mSessionMessage.clear();
    mSessionMessage.addAll(sessionRealms);
    mSessionAdapter.notifyDataSetChanged();
  }
}
