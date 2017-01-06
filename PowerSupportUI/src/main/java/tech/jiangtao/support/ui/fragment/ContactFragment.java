package tech.jiangtao.support.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.kevin.library.widget.CleanDialog;
import com.kevin.library.widget.SideBar;
import com.kevin.library.widget.builder.IconFlag;
import com.kevin.library.widget.builder.NegativeClickListener;
import com.kevin.library.widget.builder.PositiveClickListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tech.jiangtao.support.kit.eventbus.ContactEvent;
import tech.jiangtao.support.kit.eventbus.MessageTest;
import tech.jiangtao.support.kit.eventbus.RosterEntryBus;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.linstener.ContactItemCallback;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: ContactFragment </br>
 * Description: 通讯录页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:42 AM</br>
 * Update: 02/12/2016 11:42 AM </br>
 **/
public class ContactFragment extends BaseFragment
    implements EasyViewHolder.OnItemClickListener, EasyViewHolder.OnItemLongClickListener,
    EasyViewHolder.OnItemLeftScrollListener {

  @BindView(R2.id.contact_list) RecyclerView mContactList;
  @BindView(R2.id.sidebar) SideBar mSideBar;
  @BindView(R2.id.ui_view_bubble) TextView mUiViewBuddle;
  public static final String TAG = ContactFragment.class.getSimpleName();
  private ContactAdapter mBaseEasyAdapter;
  private ContactItemCallback mContactItemCallback;
  private List<ConstrutContact> mConstrutContact;
  private Realm mRealm;
  private RealmResults<VCardRealm> mVCardRealmRealmResults;

  public static ContactFragment newInstance() {
    return new ContactFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    setAdapter();
    getContact();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_contact;
  }

  public void setAdapter() {
    mConstrutContact = new ArrayList<>();
    mBaseEasyAdapter = new ContactAdapter(getContext(), mConstrutContact);
    mBaseEasyAdapter.setOnClickListener(this);
    mBaseEasyAdapter.setOnLongClickListener(this);
    mContactList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mContactList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mContactList.setAdapter(mBaseEasyAdapter);
    HermesEventBus.getDefault().post(new ContactEvent());
  }

  @Override public void onResume() {
    super.onResume();
  }

  private void getContact() {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.executeTransaction(realm -> {
      RealmQuery<VCardRealm> realmQuery = realm.where(VCardRealm.class);
      mVCardRealmRealmResults = realmQuery.equalTo("friend",true).findAll();
      buildHeadView();
      Iterator it = mVCardRealmRealmResults.iterator();
      Log.d(TAG, "getContact: 打印出好友的数量:" + mVCardRealmRealmResults.size());
      while (it.hasNext()) {
        mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_NORMAL)
            .vCardRealm((VCardRealm) it.next())
            .build());
      }
      mBaseEasyAdapter.notifyDataSetChanged();
      mVCardRealmRealmResults.addChangeListener(element -> {
        mConstrutContact.clear();
        buildHeadView();
        Iterator iterator = element.iterator();
        Log.d(TAG, "getContact: 打印出好友的数量:" + element.size());
        while (iterator.hasNext()) {
          mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_NORMAL)
              .vCardRealm((VCardRealm) iterator.next())
              .build());
        }
        mBaseEasyAdapter.notifyDataSetChanged();
      });
    });
    buildSideBar();
  }

  public void  buildSideBar(){
    mSideBar.setBubble(mUiViewBuddle);
    List<String> list = Arrays.asList(SideBar.b);
    mSideBar.setUpCharList(list);
    mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
      @Override public void onTouchingLetterChanged(String s) {
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mRealm.close();
  }

  public void buildHeadView() {
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP)
        .id(R.mipmap.iconfont_qun)
        .title("群聊")
        .build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP)
        .id(R.mipmap.iconfont_pengyou)
        .title("新朋友")
        .build());
  }

  @Override public void onItemClick(int position, View view) {
    Log.d(TAG, "onItemClick: ");
    mContactItemCallback.onItemClick(position, view, mConstrutContact.get(position));
  }

  @Override public void onItemLeftClick(int position, View view) {
    Log.d(TAG, "onItemLeftClick: ");
  }

  @Override public boolean onItemLongClick(int position, View view) {
    Log.d(TAG, "onItemLongClick: ");
    ConstrutContact construtContact = mConstrutContact.get(position);
    if (position>=2){
      deleteFriends(construtContact.mVCardRealm.getJid(),construtContact.mVCardRealm.getNickName());
    }
    return false;
  }

  public void deleteFriends(String userjid,String username){
    final CleanDialog dialog =
        new CleanDialog.Builder(getContext()).iconFlag(IconFlag.WARN)
            .negativeButton("取消", Dialog::dismiss)
            .positiveButton("删除", new PositiveClickListener() {
              @Override public void onPositiveClickListener(CleanDialog dialog1) {
                //删除用户,远程删除用户，成功后，从会话中列表中，删除用户
                HermesEventBus.getDefault().post(new RosterEntryBus(userjid));
                dialog1.dismiss();
              }
            })
            .title("确认删除好友"+username+"吗?")
            .negativeTextColor(Color.WHITE)
            .positiveTextColor(Color.WHITE)
            .builder();
    dialog.showDialog();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContactItemCallback = (ContactItemCallback) context;
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }
}
