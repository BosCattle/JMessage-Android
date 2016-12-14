package tech.jiangtao.support.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import tech.jiangtao.support.kit.eventbus.MessageTest;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleVCard;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.linstener.ContactItemCallback;
import tech.jiangtao.support.ui.model.Contacts;
import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.ContactsViewHolder;

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
  public static final String TAG = ContactFragment.class.getSimpleName();
  private ContactAdapter mBaseEasyAdapter;
  private ContactItemCallback mContactItemCallback;
  private List<ConstrutContact> mConstrutContact;

  public static ContactFragment newInstance() {
    return new ContactFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    setAdapter();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_contact;
  }

  public void setAdapter() {
    mConstrutContact = new ArrayList<>();
    buildHeadView();
    mBaseEasyAdapter = new ContactAdapter(getContext(), mConstrutContact);
    mBaseEasyAdapter.setOnClickListener(this);
    mContactList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mContactList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mContactList.setAdapter(mBaseEasyAdapter);
    updateContact();
  }

  @Override public void onResume() {
    super.onResume();
    getContact();
  }

  private void getContact() {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<VCardRealm> realmQuery = realm.where(VCardRealm.class);
    RealmResults<VCardRealm> realmResult = realmQuery.equalTo("friend",true).findAll();
    mBaseEasyAdapter.clear();
    buildHeadView();
    for (VCardRealm entry : realmResult) {
      Log.d(TAG, "getContact:打印出本地数据库获取的通讯录的数量"+realmResult.size());
      mConstrutContact.add(
          new ConstrutContact.Builder().type(ContactType.TYPE_NORMAL).vCardRealm(entry).build());
    } mBaseEasyAdapter.notifyDataSetChanged();
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
    mContactItemCallback.onItemClick(position, view,mConstrutContact.get(position));
  }

  @Override public void onItemLeftClick(int position, View view) {
    Log.d(TAG, "onItemLeftClick: ");
  }

  @Override public boolean onItemLongClick(int position, View view) {
    Log.d(TAG, "onItemLongClick: ");
    return false;
  }

  public void updateContact() {
    Roster roster = Roster.getInstanceFor(SupportService.getmXMPPConnection());
    Collection<RosterEntry> entries = roster.getEntries();
    Log.d(TAG, "getContact:获取到我的好友数量" + entries.size());
    Set<RosterEntry> set = new HashSet<>();
    set.addAll(entries);
    for (RosterEntry en : set) {
      SimpleVCard sVCard = new SimpleVCard(en.getUser(),true);
      sVCard.getVCard();
    }
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContactItemCallback = (ContactItemCallback) context;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessage(MessageTest message) {
    Log.d("----------->", "onMessage: " + message);

  }
}
