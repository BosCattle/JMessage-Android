package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.GroupServiceApi;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.GroupAdditionViewHolder;

/**
 * Class: FriendsFragment </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 20/04/2017 5:55 PM</br>
 * Update: 20/04/2017 5:55 PM </br>
 **/
public class FriendsFragment extends BaseFragment implements EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.group_member_addition) RecyclerView mGroupMemberAddition;
  private List<ContactRealm> mContactRealms;
  private BaseEasyAdapter mBaseEasyAdapter;
  private UserServiceApi mUserServiceApi;
  private List<String> mUserInvitedId;
  private String mGroupId;
  private GroupServiceApi mGroupApiService;
  private String mUserId;
  private Realm mRealm;

  public static FriendsFragment newInstance() {
    return new FriendsFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, getView());
    setAdapter();
    updateItems();
    return getView();
  }

  private void setAdapter() {
    mContactRealms = new ArrayList<>();
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.bind(ContactRealm.class, GroupAdditionViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mGroupMemberAddition.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mGroupMemberAddition.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mGroupMemberAddition.setAdapter(mBaseEasyAdapter);
  }

  public void updateItems() {
    mUserInvitedId = new ArrayList<>();
    mGroupId = getArguments().getString(SupportIM.GROUPID);
    AppPreferences appPreferences = new AppPreferences(getContext());
    mGroupApiService = ApiService.getInstance().createApiService(GroupServiceApi.class);
    try {
      mUserId = appPreferences.getString(SupportIM.USER_ID);
      mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
      mUserServiceApi.queryUserFriends(mUserId)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(messageRealm -> {
            // 1. 填充好友信息到界面
            mContactRealms.addAll(messageRealm);
            mBaseEasyAdapter.appendAll(mContactRealms);
            mBaseEasyAdapter.notifyDataSetChanged();
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, "call: " + throwable.getMessage());
              // 从数据库中取数据，放到界面上
            }
          });
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override public int layout() {
    return R.layout.fragment_friends;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mRealm.close();
  }

  @Override public void onItemClick(int position, View view) {
    if (!mUserInvitedId.contains(mContactRealms.get(position).getUserId())) {
      mUserInvitedId.add(mContactRealms.get(position).getUserId());
    } else {
      mUserInvitedId.remove(mContactRealms.get(position).getUserId());
    }
    mGroupApiService.addMembers(mUserInvitedId, mUserId, mGroupId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(contactRealms -> {
          writeGroupMember(contactRealms);
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            LogUtils.e(TAG, "call: " + throwable.getMessage());
          }
        });
  }

  private void writeGroupMember(List<ContactRealm> contactRealm) {
    LogUtils.d(this.getClass().getSimpleName(), "writeGroupMember: ");
    mRealm = Realm.getDefaultInstance();
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(contactRealm);
      }
    });
  }
}
