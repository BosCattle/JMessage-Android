package tech.jiangtao.support.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kevin.library.widget.CleanDialog;
import com.kevin.library.widget.SideBar;
import com.kevin.library.widget.builder.IconFlag;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.realm.RealmResults;
import tech.jiangtao.support.kit.annotation.ChatRouter;
import tech.jiangtao.support.kit.annotation.GroupsRouter;
import tech.jiangtao.support.kit.annotation.InvitedRouter;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.manager.IMContactManager;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.PinYinUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.kit.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;

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
    EasyViewHolder.OnItemLeftScrollListener, SwipeRefreshLayout.OnRefreshListener,IMListenerCollection.IMRealmChangeListener<ContactRealm> {

  @BindView(R2.id.contact_list) RecyclerView mContactList;
  @BindView(R2.id.sidebar) SideBar mSideBar;
  @BindView(R2.id.ui_view_bubble) TextView mUiViewBuddle;
  @BindView(R2.id.contact_swift_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  public static final String TAG = ContactFragment.class.getSimpleName();
  private ContactAdapter mBaseEasyAdapter;
  private List<ConstrutContact> mConstrutContact;
  private Class mGroupClazz;
  private Class mInvitedClass;
  private Class mChatClass;

  public static ContactFragment newInstance() {
    return new ContactFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    setRefresh();
    buildSideBar();
    setAdapter();
    return getView();
  }

  private void pullDatas() {
    IMContactManager.geInstance().readContacts(this);
    IMContactManager.geInstance().readContactsFromHttp(getContext(),this);
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
  }

  @Override public void onResume() {
    super.onResume();
    pullDatas();
  }

  public void buildSideBar() {
    mSideBar.setBubble(mUiViewBuddle);
    List<String> list = Arrays.asList(SideBar.b);
    mSideBar.setUpCharList(list);
    mSideBar.setOnTouchingLetterChangedListener(s -> {
      for (int i = 0; i < mConstrutContact.size(); i++) {
        if (mConstrutContact.get(i).mTitle != null) {
          if (s.equals(mConstrutContact.get(i).mTitle)) {
            mContactList.scrollToPosition(i + 2);
          }
        }
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    IMContactManager.geInstance().destory();
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
    Class<?> activity = getActivity().getClass();
    Annotation[] annotation = activity.getAnnotations();
    LogUtils.d(TAG, annotation.length + "注解个数");
    for (Annotation anAnnotation : annotation) {
      if (anAnnotation instanceof GroupsRouter) {
        GroupsRouter animation = (GroupsRouter) anAnnotation;
        mGroupClazz = animation.router();
      }
      if (anAnnotation instanceof InvitedRouter) {
        InvitedRouter invitedRouter = (InvitedRouter) anAnnotation;
        mInvitedClass = invitedRouter.router();
      }
      if (anAnnotation instanceof ChatRouter) {
        ChatRouter chatRouter = (ChatRouter) anAnnotation;
        mChatClass = chatRouter.router();
      }
    }
    if (position == 0) {
      Intent intent = new Intent(getActivity(), mGroupClazz);
      startActivity(intent);
    } else if (position == 1) {
      Intent intent = new Intent(getActivity(), mInvitedClass);
      startActivity(intent);
    } else {
      Intent intent = new Intent(getActivity(), mChatClass);
      intent.putExtra(SupportIM.VCARD, mConstrutContact.get(position).mContactRealm);
      startActivity(intent);
    }
  }

  @Override public void onItemLeftClick(int position, View view) {
    LogUtils.d(TAG, "onItemLeftClick: ");
  }

  @Override public boolean onItemLongClick(int position, View view) {
    LogUtils.d(TAG, "onItemLongClick: ");
    ConstrutContact construtContact = mConstrutContact.get(position);
    if (position >= 2) {
      deleteFriends(construtContact.mContactRealm);
    }
    return false;
  }

  public void deleteFriends(ContactRealm contactRealm) {
    final CleanDialog dialog = new CleanDialog.Builder(getContext()).iconFlag(IconFlag.WARN)
        .negativeButton("取消", Dialog::dismiss)
        .positiveButton("删除", dialog1 -> {
          //删除用户,远程删除用户，成功后，从会话中列表中，删除用户
          IMContactManager.geInstance().deleteSingleIMContactRealm(contactRealm,ContactFragment.this);
          dialog1.dismiss();
        })
        .title("确认删除好友" + contactRealm.getNickName() + "吗?")
        .negativeTextColor(Color.WHITE)
        .positiveTextColor(Color.WHITE)
        .builder();
    dialog.showDialog();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    }, 3000);
  }

  @Override public void change(RealmResults<ContactRealm> contactRealms) {
    mConstrutContact.clear();
    mBaseEasyAdapter.clear();
    buildHeadView();
    if (contactRealms.size() != 0) {
      //Collections.sort(contactRealms, new ContactComparator());
      for (int i = 0; i < contactRealms.size(); i++) {
        if (contactRealms.get(i) != null && contactRealms.get(i).getNickName() != null) {
          if (i == 0) {
            mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_LETTER)
                .title(PinYinUtils.getPinyinFirstLetter(
                    PinYinUtils.ccs2Pinyin(contactRealms.get(i).getNickName())))
                .build());
          }
          if (i > 0) {
            if (contactRealms.get(i - 1).getNickName() != null
                && !(PinYinUtils.getPinyinFirstLetter(contactRealms.get(i - 1).getNickName())
                .equals(PinYinUtils.getPinyinFirstLetter(contactRealms.get(i).getNickName())))) {
              mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_LETTER)
                  .title(PinYinUtils.getPinyinFirstLetter(
                      PinYinUtils.ccs2Pinyin(contactRealms.get(i).getNickName())))
                  .build());
            }
          }
        }
        mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_NORMAL)
            .contactRealm(contactRealms.get(i))
            .build());
      }
    }
    mBaseEasyAdapter.notifyDataSetChanged();
  }
}
