package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.GroupServiceApi;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.model.type.TransportType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.utils.ResourceAddress;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: GroupDetailFragment </br>
 * Description: 群组详情页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/04/2017 3:15 PM</br>
 * Update: 18/04/2017 3:15 PM </br>
 **/
public class GroupDetailFragment extends BaseFragment
    implements EasyViewHolder.OnItemClickListener {

  public static final String TAG = GroupDetailFragment.class.getSimpleName();
  @BindView(R2.id.group_detail_recycle) RecyclerView mGroupDetailRecycle;
  @BindView(R2.id.delete_group_button) AppCompatButton mDeleteGroupButton;
  @BindView(R2.id.delete_member_button) AppCompatButton mDeleteMemberButton;
  private ContactAdapter mContactAdapter;
  private ArrayList<ConstrutContact> mConstrutContact;
  private List<ContactRealm> mContact = new ArrayList<>();
  private GroupRealm mGroupRealm;
  protected boolean isChecked = true;
  private GroupServiceApi mGroupServiceApi;
  private String userId = null;

  public static GroupDetailFragment newInstance() {
    return new GroupDetailFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, getView());
    setUpAdapter();
    updateGroupData();
    loadGroupMember();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_group_detail;
  }

  private void updateGroupData() {
    // 查询自己的contact
    AppPreferences appPreferences = new AppPreferences(getContext());
    ContactRealm contactRealm = null;
    Realm realm = Realm.getDefaultInstance();
    try {
      String userId = appPreferences.getString(SupportIM.USER_ID);
      RealmResults<ContactRealm> realms =
          realm.where(ContactRealm.class).equalTo(SupportIM.USER_ID, userId).findAll();
      if (realms.size() > 0) {
        contactRealm = realms.first();
      }
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    realm.close();
    mGroupRealm = getArguments().getParcelable(SupportIM.GROUP);
    mConstrutContact.clear();
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_DETAIL_HEAD)
        .title(mGroupRealm.getName())
        .subtitle(mGroupRealm.getDescription())
        .img(ResourceAddress.url(mGroupRealm.getAvatar(), TransportType.AVATAR))
        .build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_MEMBER)
        .title(mGroupRealm.getGroupId())
        .datas(mContact)
        .build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_RADIO).build());
    mConstrutContact.add(
        new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_VALUE).title("清空历史记录").build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_VALUE)
        .title("昵称")
        .subtitle(contactRealm != null ? contactRealm.getNickName() : null)
        .build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_VALUE)
        .title("添加用户")
        .subtitle(mContact.size() + "人")
        .build());
    mContactAdapter.notifyDataSetChanged();
  }

  /**
   * 更新和完善群信息的。
   */
  private void setUpAdapter() {
    mConstrutContact = new ArrayList<>();
    mContactAdapter = new ContactAdapter(getContext(), mConstrutContact);
    mContactAdapter.setOnClickListener(this);
    mGroupDetailRecycle.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mGroupDetailRecycle.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mGroupDetailRecycle.setAdapter(mContactAdapter);
  }

  public void loadGroupMember() {
    mGroupServiceApi = ApiService.getInstance().createApiService(GroupServiceApi.class);
    AppPreferences sharpener = new AppPreferences(getContext());
    try {
      userId = sharpener.getString(SupportIM.USER_ID);
      mGroupServiceApi.selectGroupMembers(mGroupRealm.groupId, userId)
          .subscribeOn(Schedulers.io())
          .doOnSubscribe(() -> SimpleHUD.showLoadingMessage(getContext(), "正在加载..", true))
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(contactRealms -> {
            SimpleHUD.dismiss();
            mContact = contactRealms;
            for (int i = 0; i < contactRealms.size(); i++) {
              ContactRealm contactRealm = contactRealms.get(i);
              if (contactRealm.getUserId().equals(StringSplitUtil.splitDivider(userId))) {
                mDeleteMemberButton.setVisibility(View.VISIBLE);
              }
            }
            updateGroupData();
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.e(TAG, "获取群组成员错误");
              SimpleHUD.dismiss();
            }
          });
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    mGroupServiceApi.isReceived(mGroupRealm.getGroupId(), userId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(authority -> {
          isChecked = authority.isReceived > 0;
          updateGroupData();
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
          }
        });
  }

  @Override public void onItemClick(int position, View view) {
    if (position == 2) {
      mGroupServiceApi.updateIsReceived(mGroupRealm.groupId, userId)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(authority -> {
            isChecked = authority.isReceived > 0;
            updateGroupData();
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
            }
          });
    } else if (position == 3) {
      LogUtils.d(TAG, "点击了第三项");
    } else if (position == 4) {

    }
  }

  @OnClick({ R2.id.delete_group_button, R2.id.delete_member_button }) public void onClick(View v) {

  }

}
