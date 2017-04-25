package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.model.User;
import tech.jiangtao.support.kit.model.group.Groups;
import tech.jiangtao.support.kit.model.group.InvitedInfo;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.NewFriendViewHolder;

/**
 * Class: InvitedFragment </br>
 * Description: 所有的邀请 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/04/2017 1:55 AM</br>
 * Update: 18/04/2017 1:55 AM </br>
 **/
public class InvitedFragment extends BaseFragment implements EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.new_friend_page) RecyclerView mNewFriendPage;
  private BaseEasyAdapter mBaseEasyAdapter;
  private UserServiceApi mUserServiceApi;

  public static InvitedFragment newInstance() {
    return new InvitedFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    setUpAdapter();
    updateRequest();
    ButterKnife.bind(this, getView());
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_new_friend;
  }

  public void updateRequest() {
    AppPreferences mAppPreferences = new AppPreferences(getContext());
    String userId = null;
    try {
      userId = mAppPreferences.getString(SupportIM.USER_ID);
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    mUserServiceApi.getAllInvite(StringSplitUtil.splitDivider(userId))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(list -> {
          mBaseEasyAdapter.addAll(list);
          mBaseEasyAdapter.notifyDataSetChanged();
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            LogUtils.d(TAG, throwable.getLocalizedMessage());
          }
        });
  }

  private void setUpAdapter() {
    mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
    mBaseEasyAdapter.bind(InvitedInfo.class, NewFriendViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mNewFriendPage.setHasFixedSize(true);
    mNewFriendPage.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mNewFriendPage.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mNewFriendPage.setAdapter(mBaseEasyAdapter);
    mBaseEasyAdapter.add(new InvitedInfo(new User(), new Groups()));
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  @Override public void onItemClick(int position, View view) {

  }
}
