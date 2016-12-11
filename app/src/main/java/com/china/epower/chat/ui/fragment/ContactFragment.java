package com.china.epower.chat.ui.fragment;

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
import com.china.epower.chat.R;
import com.china.epower.chat.app.PowerApp;
import com.china.epower.chat.model.Contacts;
import com.china.epower.chat.ui.activity.ChatActivity;
import com.china.epower.chat.ui.adapter.BaseEasyAdapter;
import com.china.epower.chat.ui.adapter.BaseEasyViewHolderFactory;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import com.china.epower.chat.utils.RecyclerViewUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

/**
 * Class: ContactFragment </br>
 * Description: 通讯录界面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 3:08 PM</br>
 * Update: 10/11/2016 3:08 PM </br>
 **/
public class ContactFragment extends Fragment
    implements EasyViewHolder.OnItemClickListener, EasyViewHolder.OnItemLongClickListener,
    EasyViewHolder.OnItemLeftScrollListener {

  @BindView(R.id.contact_list) RecyclerView mContactList;
  public static final String TAG = ContactFragment.class.getSimpleName();
  private BaseEasyAdapter mBaseEasyAdapter;
  private List<Contacts> mChatItemses;

  public static ContactFragment newInstance() {
    return new ContactFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_contact, container, false);
    ButterKnife.bind(this, view);
    setAdapter();
    return view;
  }

  public void setAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
    mBaseEasyAdapter.setOnClickListener(this);
    mBaseEasyAdapter.setOnLongClickListener(this);
    mBaseEasyAdapter.setOnLeftClickListener(this);
    mContactList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mContactList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mChatItemses = new ArrayList<>();
    mContactList.setAdapter(mBaseEasyAdapter);
    getContact();
  }

  @Override public void onItemClick(int position, View view) {
    Log.d(TAG, "onItemClick: ");
    ChatActivity.startChat(getActivity(),null);
  }

  @Override public void onItemLeftClick(int position, View view) {
    Log.d(TAG, "onItemLeftClick: ");
  }

  @Override public boolean onItemLongClick(int position, View view) {
    Log.d(TAG, "onItemLongClick: ");
    return false;
  }

  public void getContact() {
    Roster roster = Roster.getInstanceFor(PowerApp.getmXmppConnect());
    Collection<RosterEntry> entries = roster.getEntries();
    Log.d(TAG, "getContact: " + entries.size());
    mChatItemses.clear();
    Set<RosterEntry> set = new HashSet<>();
    set.addAll(entries);
    for (RosterEntry en : set) {
      Contacts contacts = new Contacts();
      contacts.name = en.getName();
      contacts.user = en.getUser();
      contacts.type = en.getType();
      mChatItemses.add(contacts);
    }
    mBaseEasyAdapter.clear();
    mBaseEasyAdapter.addAll(mChatItemses);
    mBaseEasyAdapter.notifyDataSetChanged();
  }
}
