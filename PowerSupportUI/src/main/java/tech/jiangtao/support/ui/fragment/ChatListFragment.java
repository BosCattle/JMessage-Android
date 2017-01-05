package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;

import java.util.Iterator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.adapter.SessionAdapter;
import tech.jiangtao.support.ui.model.ChatItems;
import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.pattern.SessionListMessage;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.ContactViewHolder;
import xiaofei.library.hermes.annotation.ClassId;

/**
 * Class: ChatListFragment </br>
 * Description: 聊天列表 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:38 AM</br>
 * Update: 02/12/2016 11:38 AM </br>
 **/
public class ChatListFragment extends BaseFragment implements EasyViewHolder.OnItemClickListener {

    public static final String TAG = Fragment.class.getSimpleName();
    @BindView(R2.id.chat_list)
    RecyclerView mChatList;
    private SessionAdapter mSessionAdapter;
    private List<SessionListMessage> mSessionMessage;
    private Realm mRealm;
    private RealmResults<SessionRealm> mSessionRealm;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        setAdapter();
        return getView();
    }

    @Override
    public int layout() {
        return R.layout.fragment_single_chat;
    }

    public void setAdapter() {
        mSessionMessage = new ArrayList<>();
        mSessionAdapter = new SessionAdapter(getContext(), mSessionMessage);
        mSessionAdapter.setOnClickListener(this);
        mChatList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
        mChatList.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mChatList.setAdapter(mSessionAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getChatList();
    }

    private void getChatList() {
        mRealm.executeTransaction(realm -> {
            mSessionRealm = realm.where(SessionRealm.class).findAll();
            mSessionRealm.addChangeListener(element -> {
                mSessionMessage.clear();
                Iterator<SessionRealm> it = element.iterator();
                while (it.hasNext()) {
                    SessionRealm messageRealm = it.next();
                    //这儿需要查一下MessageRealm和VCardRealm;
                    RealmResults<VCardRealm> vCardRealms = realm.where(VCardRealm.class).equalTo("jid", StringSplitUtil.splitDivider(messageRealm.getVcard_id())).findAll();
                    VCardRealm vCardRealm = new VCardRealm();
                    if (vCardRealms.size() != 0) {
                        vCardRealm = vCardRealms
                                .first();
                    }
                    RealmResults<MessageRealm> message = realm.where(MessageRealm.class).equalTo("id", messageRealm.getMessage_id()).findAll();
                    mSessionMessage.add(
                            new SessionListMessage.Builder().unReadMessageCount(messageRealm.unReadCount)
                                    .username(vCardRealm.getNickName()).unReadMessage(message.first().textMessage)
                                    .avatar(vCardRealm.getAvatar()).build());

                }
                mSessionAdapter.notifyDataSetChanged();
            });
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(RecieveMessage message) {

    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
    }
}
