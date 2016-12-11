package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.ChatItems;
import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.ContactViewHolder;

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
    EasyViewHolder.OnItemLeftScrollListener {

  public static final String TAG = Fragment.class.getSimpleName();
  @BindView(R2.id.chat_list) RecyclerView mChatList;
  private BaseEasyAdapter mBaseEasyAdapter;
  private List<ChatItems> mChatItemses;

  public static ChatListFragment newInstance() {
    return new ChatListFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater,container,savedInstanceState);
    setAdapter();
    return getView();
  }

  @Override
  public int layout() {
    return R.layout.fragment_single_chat;
  }

  public void setAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
    mBaseEasyAdapter.setOnClickListener(this);
    mBaseEasyAdapter.setOnLongClickListener(this);
    mBaseEasyAdapter.setOnLeftClickListener(this);
    mBaseEasyAdapter.bind(ChatItems.class, ContactViewHolder.class);
    Log.d(TAG, "setAdapter: "+mChatList);
    mChatList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mChatList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mChatItemses = new ArrayList<>();
    mChatList.setAdapter(mBaseEasyAdapter);
    mBaseEasyAdapter.addAll(mChatItemses);
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  @Override public void onItemClick(int position, View view) {
    Log.d(TAG, "onItemClick: ");
    mChatItemses.get(position);
  }

  @Override public void onItemLeftClick(int position, View view) {
    Log.d(TAG, "onItemLeftClick: ");
  }

  @Override public boolean onItemLongClick(int position, View view) {
    Log.d(TAG, "onItemLongClick: ");
    return false;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessage(RecieveMessage message) {

  }
}
