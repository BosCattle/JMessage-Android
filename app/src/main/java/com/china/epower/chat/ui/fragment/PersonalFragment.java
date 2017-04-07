package com.china.epower.chat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.OnClick;

import com.china.epower.chat.R;
import com.china.epower.chat.model.type.ListDataType;
import com.china.epower.chat.ui.activity.LoginActivity;
import com.china.epower.chat.ui.activity.PersonalDetailActivity;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import com.china.epower.chat.ui.adapter.PersonalDataAdapter;
import com.china.epower.chat.ui.pattern.ConstructListData;
import com.china.epower.chat.utils.RecyclerViewUtils;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tech.jiangtao.support.kit.callback.DisconnectCallBack;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.fragment.BaseFragment;
import tech.jiangtao.support.ui.service.XMPPService;

/**
 * Class: PersonalFragment </br>
 * Description: 个人页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 3:07 PM</br>
 * Update: 10/11/2016 3:07 PM </br>
 **/
public class PersonalFragment extends BaseFragment implements EasyViewHolder.OnItemClickListener {

  public static final int TAG_HEAD = 200;
  public static final int TAG_NOTIFICATION = 300;
  public static final int TAG_CACHE = 400;
  public static final int TAG_UPDATE_PASSWORD = 500;
  public static final int TAG_UPDATE = 600;
  public static final int TAG_ABOUT = 700;
  public static final String TAG = PersonalFragment.class.getSimpleName();

  @BindView(R.id.personal_list) RecyclerView mPersonalList;
  @BindView(R.id.login_button) AppCompatButton mLoginButton;
  private PersonalDataAdapter mDataAdapter;
  private Realm mRealm;
  private List<ConstructListData> mData = new ArrayList<>();

  public static PersonalFragment newInstance() {
    return new PersonalFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    setAdapter();
    recieveVCardRealm();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_personal;
  }

  private void setAdapter() {
    mDataAdapter = new PersonalDataAdapter(getContext(), buildData(null));
    mDataAdapter.setOnClickListener(this);
    mPersonalList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mPersonalList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mPersonalList.setAdapter(mDataAdapter);
  }

  @Override public void onResume() {
    super.onResume();
  }

  public List<ConstructListData> buildData( VCardRealm mVCardRealm) {
    mData.clear();
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_HEAD)
        .tag(TAG_HEAD)
        .image(
            mVCardRealm != null && mVCardRealm.getAvatar() != null ? mVCardRealm.getAvatar() : null)
        .username(
            mVCardRealm != null && mVCardRealm.getNickName() != null ? mVCardRealm.getNickName()
                : "用户名")
        .nickname(mVCardRealm != null && mVCardRealm.getSubject() != null ? mVCardRealm.getSubject()
            : "部门")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_NOTIFICATION)
        .title("通知")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_CACHE)
        .title("清除缓存")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_UPDATE_PASSWORD)
        .title("修改密码")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_UPDATE)
        .title("版本更新")
        .subtitle("当前是最新版本")
        .build());
    mData.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_ABOUT)
        .title("关于我们")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    return mData;
  }

  @Override public void onItemClick(int position, View view) {
    switch ((mData.get(position)).getmTag()) {
      case TAG_HEAD:
        PersonalDetailActivity.startPersonalDetail(getActivity());
        break;
    }
  }

  //点击发送回调退出
  @OnClick(R.id.login_button) public void onClick(View v) {
    XMPPService.disConnect(() -> LoginActivity.startLogin(getActivity()));
    XMPPService.disConnect(new DisconnectCallBack() {
      @Override
      public void disconnectFinish() {

      }
    });
  }

  public void recieveVCardRealm() {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    String userJid = null;
    final AppPreferences appPreferences = new AppPreferences(getContext());
    try {
      userJid = StringSplitUtil.splitDivider(appPreferences.getString("userJid"));
      String finalUserJid = userJid;
      mRealm.executeTransaction(realm -> {
        RealmResults<VCardRealm> realmQuery =
            realm.where(VCardRealm.class).equalTo("jid", finalUserJid).findAll();
        VCardRealm mVCardRealm= null;
        if (realmQuery.size() != 0) {
          mVCardRealm = realmQuery.first();
        }
        if (mVCardRealm != null && mVCardRealm.isValid()) {
          mDataAdapter.clear();
          buildData(mVCardRealm);
          mDataAdapter.notifyDataSetChanged();
        }
      });
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override public void onDestroyView() {
    mRealm.close();
    super.onDestroyView();
  }
}
