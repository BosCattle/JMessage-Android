package com.china.epower.chat.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
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

import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import tech.jiangtao.support.kit.callback.DisconnectCallBack;
import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.eventbus.LocalVCardEvent;
import tech.jiangtao.support.kit.eventbus.UnRegisterEvent;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleVCard;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.service.XMPPService;
import work.wanghao.simplehud.SimpleHUD;
import xiaofei.library.hermeseventbus.HermesEventBus;

import static xiaofei.library.hermes.Hermes.getContext;

/**
 * Class: PersonalFragment </br>
 * Description: 个人页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 3:07 PM</br>
 * Update: 10/11/2016 3:07 PM </br>
 **/
public class PersonalFragment extends Fragment implements EasyViewHolder.OnItemClickListener {

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
  private List<ConstructListData> mDatas;
  private VCardRealm mVCardRealm;
  private Realm mRealm;
  private SimpleVCard mSimpleVCard;
  private LocalVCardEvent mLocalVCardEvent;

  public static PersonalFragment newInstance() {
    return new PersonalFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_personal, container, false);
    ButterKnife.bind(this, view);
    setAdapter();
    return view;
  }

  private void setAdapter() {
    mDatas = new ArrayList<>();
    mDataAdapter = new PersonalDataAdapter(getContext(), buildData());
    mDataAdapter.setOnClickListener(this);
    mPersonalList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mPersonalList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mPersonalList.setAdapter(mDataAdapter);
  }


  @Override public void onResume() {
    super.onResume();
    recieveVCardRealm();
  }

  public List<ConstructListData> buildData() {
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_HEAD)
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
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_NOTIFICATION)
        .title("通知")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_CACHE)
        .title("清除缓存")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_UPDATE_PASSWORD)
        .title("修改密码")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_UPDATE)
        .title("版本更新")
        .subtitle("当前是最新版本")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
        .tag(TAG_ABOUT)
        .title("关于我们")
        .arrowIcon(R.mipmap.ic_arrow)
        .build());
    return mDatas;
  }

  @Override public void onItemClick(int position, View view) {
    switch (mDatas.get(position).getmTag()) {
      case TAG_HEAD:
        PersonalDetailActivity.startPersonalDetail(getActivity());
        break;
    }
  }

  //点击发送回调退出
  @OnClick(R.id.login_button) public void onClick(View v) {
    XMPPService.disConnect(() -> LoginActivity.startLogin(getActivity()));
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  public void recieveVCardRealm() {
    mRealm = Realm.getDefaultInstance();
    mSimpleVCard = new SimpleVCard();
    mLocalVCardEvent = new LocalVCardEvent();
    RealmQuery<VCardRealm> realmQuery = mRealm.where(VCardRealm.class);
    String userJid = null;
    final AppPreferences appPreferences = new AppPreferences(getContext());
    try {
      userJid = appPreferences.getString("userJid");
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    RealmResults<VCardRealm> realmResult = realmQuery.equalTo("jid", userJid).findAll();
    if (realmResult.size() != 0) {
      mVCardRealm = realmResult.first();
    }else {
      try {
        //测试1@dc-a4b8eb92-xmpp.jiangtao.tech./jiangtao,获取和更新VcARD
        mLocalVCardEvent.setJid(StringSplitUtil.splitDivider(appPreferences.getString("userJid")));
        Log.d(TAG, "getLocalVCardRealm: " + appPreferences.getString("userJid"));
        RealmResults<VCardRealm> realmResult1 =
            realmQuery.equalTo("jid", StringSplitUtil.splitDivider(appPreferences.getString("userJid"))).findAll();
        if (realmResult1.size() != 0) {
          mVCardRealm = realmResult1.first();
          mLocalVCardEvent.setNickName(mVCardRealm.getNickName());
          mLocalVCardEvent.setAvatar(mVCardRealm.getAvatar());
        } else {
          mSimpleVCard.startUpdate(mLocalVCardEvent, null);
        }
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
      }
    }
    mDataAdapter.clear();
    buildData();
    mDataAdapter.notifyDataSetChanged();
  }
}
