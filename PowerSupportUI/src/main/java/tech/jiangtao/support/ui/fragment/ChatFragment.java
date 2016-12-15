package tech.jiangtao.support.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.melink.bqmmsdk.bean.Emoji;
import com.melink.bqmmsdk.sdk.BQMM;
import com.melink.bqmmsdk.sdk.IBqmmSendMessageListener;
import com.melink.bqmmsdk.ui.keyboard.BQMMKeyboard;
import com.melink.bqmmsdk.widget.BQMMEditView;
import com.melink.bqmmsdk.widget.BQMMSendButton;

import io.realm.Realm;
import io.realm.RealmResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleArchiveMessage;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.ChatMessageAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.ChatExtraModel;
import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.viewholder.ExtraFuncViewHolder;

import static android.content.ContentValues.TAG;
import static tech.jiangtao.support.kit.service.SupportService.requestAllMessageArchive;

/**
 * Class: ChatFragment </br>
 * Description: 聊天页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:40 AM</br>
 * Update: 02/12/2016 11:40 AM </br>
 **/
public class ChatFragment extends BaseFragment
        implements TextWatcher, View.OnClickListener, EasyViewHolder.OnItemClickListener,
        View.OnLongClickListener {

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.swift_refresh)
    SwipeRefreshLayout mSwiftRefresh;
    @BindView(R2.id.chat_speak)
    ImageView mChatSpeak;
    @BindView(R2.id.chat_add_other_information)
    ImageView mChatAddOtherInformation;
    @BindView(R2.id.chat_send_message)
    BQMMSendButton mChatSendMessage;
    @BindView(R2.id.container_send)
    FrameLayout mContainerSend;
    @BindView(R2.id.add_smile)
    CheckBox mAddSmile;
    @BindView(R2.id.chat_input)
    BQMMEditView mChatInput;
    @BindView(R2.id.chat_inline_container)
    RelativeLayout mChatInlineContainer;
    @BindView(R2.id.chat_msg_input_box)
    BQMMKeyboard mChatMsgInputBox;
    @BindView(R2.id.chat_send_other)
    RecyclerView mChatSendOther;
    @BindView(R2.id.chat_bottom)
    RelativeLayout mChatBottom;
    private ChatMessageAdapter mChatMessageAdapter;
    private List<ConstructMessage> mMessages;
    private VCardRealm mVCardRealm;
    private VCardRealm mOwnVCardRealm;
    private Realm mRealm;
    private BQMM mBQMM;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        ButterKnife.bind(this, getView());
        return getView();
    }

    public void setExtraAdapter() {
        BaseEasyAdapter mBaseEasyAdapter = new BaseEasyAdapter(getContext());
        mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
        mBaseEasyAdapter.setOnClickListener(this);
        mBaseEasyAdapter.bind(ChatExtraModel.class, ExtraFuncViewHolder.class);
        mChatSendOther.setLayoutManager(new GridLayoutManager(getContext(),4));
        ArrayList<ChatExtraModel> mChatExtraItems = new ArrayList<>();
        mChatSendOther.setAdapter(mBaseEasyAdapter);
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_photo,"图片"));
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_take_photograph,"照片"));
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_location,"位置"));
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_call,"打电话"));
        mBaseEasyAdapter.addAll(mChatExtraItems);
        mBaseEasyAdapter.notifyDataSetChanged();
    }

    public void loadOwnRealm() {
        mVCardRealm = getArguments().getParcelable("vCard");
        mRealm = Realm.getDefaultInstance();
        RealmResults<VCardRealm> realms = mRealm.where(VCardRealm.class)
                .equalTo("jid", StringSplitUtil.splitDivider(SupportService.getmXMPPConnection().getUser()))
                .findAll();
        if (realms.size() != 0) {
            mOwnVCardRealm = realms.first();
        }
    }

    @Override
    public int layout() {
        return R.layout.fragment_chat;
    }

    private void init() {
        setUpBQMM();
        loadOwnRealm();
        setAdapter();
        setExtraAdapter();
        setViewListener();
        loadArchiveMessage();
    }

    private void setUpBQMM() {
        mBQMM = BQMM.getInstance();
        mBQMM.setEditView(mChatInput);
        mBQMM.setKeyboard(mChatMsgInputBox);
        mBQMM.setSendButton(mChatSendMessage);
        mBQMM.load();
        mChatInput.setOnTouchListener((v, event) -> {
            mAddSmile.setChecked(false);
            return false;
        });
        mBQMM.setBqmmSendMsgListener(new IBqmmSendMessageListener() {
            //图文混排消息
            @Override
            public void onSendMixedMessage(List<Object> list, boolean b) {
                sendMyFriendMessage();
            }

            //单个大表情
            @Override
            public void onSendFace(Emoji emoji) {

            }
        });
    }

    private void setViewListener() {
        mChatInput.addTextChangedListener(this);
        mChatSpeak.setOnLongClickListener(this);
        mChatAddOtherInformation.setOnClickListener(this);
        mChatSendMessage.setOnClickListener(this);
        mAddSmile.setOnClickListener(this);
    }

    public void setAdapter() {
        mMessages = new ArrayList<>();
        mChatMessageAdapter = new ChatMessageAdapter(getContext(), mMessages);
        mRecycler.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mChatMessageAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null || Objects.equals(s.toString(), "")) {
            Log.e(TAG, "onTextChanged: ");
            mChatSendMessage.setVisibility(View.GONE);
            mChatAddOtherInformation.setVisibility(View.VISIBLE);
        } else {
            mBQMM.startShortcutPopupWindowByoffset(s.toString(), mChatSendMessage, 0, 20);
            mChatAddOtherInformation.setVisibility(View.GONE);
            mChatSendMessage.setVisibility(View.VISIBLE);
        }
    }

    public void loadArchiveMessage() {
        if (mVCardRealm != null) {
            SimpleArchiveMessage mSimpleArchiveMessage = new SimpleArchiveMessage();
            MessageRealm messageRealm = null;
            RealmResults<MessageRealm> messageDatas =
                    mSimpleArchiveMessage.loadArchiveMessage(mVCardRealm.getJid());
            int size = messageDatas.size();
            Log.d(TAG, "loadArchiveMessage: " + size);
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    //根据数据判断，然后加载
                    messageRealm = messageDatas.get(i);
                    addMessageToAdapter(messageRealm);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(RecieveMessage message) {
        Log.d("----------->", "onMessage: " + message);
        // 根据消息类型，作出调转服务
        if (message.message != null && !String.valueOf(message.message).equals("")) {
            Message message1 = new Message();
            message1.paramContent = (String) message.message;
            mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
                    .avatar(mVCardRealm.getAvatar())
                    .message(message1)
                    .build());
            mChatMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealm.close();
    }

    public void addMessageToAdapter(MessageRealm realm) {
        Message message1 = new Message();
        message1.paramContent = realm.getTextMessage();
        Log.d(TAG, "addMessageToAdapter: " + realm.getMainJID());
        Log.d(TAG, "addMessageToAdapter-----: " + StringSplitUtil.splitDivider(mVCardRealm.getJid()));
        if (StringSplitUtil.splitDivider(realm.getMainJID())
                .equals(StringSplitUtil.splitDivider(mVCardRealm.getJid()))) {
            mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
                    .avatar(mVCardRealm != null ? mVCardRealm.getAvatar() : null)
                    .message(message1)
                    .build());
        } else {
            mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
                    .avatar(mOwnVCardRealm != null ? mOwnVCardRealm.getAvatar() : null)
                    .message(message1)
                    .build());
        }
        mChatMessageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        SimpleArchiveMessage message = new SimpleArchiveMessage();
        requestAllMessageArchive(message.getLastUpdateTime());
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public boolean onLongClick(View v) {
        int i = v.getId();
        if (i == R.id.chat_speak) {
        }
        return true;
    }

    @OnClick({R2.id.chat_add_other_information,R2.id.chat_send_message,R2.id.add_smile})
    public void onClick(View v){
        int i = v.getId();
        if (i == R.id.chat_add_other_information) {
            Log.d(TAG, "onClick: 点击了加号");
            if (mChatSendOther.getVisibility() == View.VISIBLE) {
                mChatSendOther.setVisibility(View.GONE);
            } else {
                mChatSendOther.setVisibility(View.VISIBLE);
            }

        } else if (i == R.id.chat_send_message) {
            sendMyFriendMessage();

        } else if (i == R.id.add_smile) {
            if (isVisibleKeyBoard()) {
                hideKeyBoard();
            } else {
                showKeyBoard();
            }

        }
    }

    public void sendMyFriendMessage() {
        String message = mChatInput.getText().toString();
        TextMessage message1 =
                new TextMessage(org.jivesoftware.smack.packet.Message.Type.chat, mVCardRealm.getJid(),
                        message);
        EventBus.getDefault().post(message1);
        Message message2 = new Message();
        message2.paramContent = mChatInput.getText().toString();
        mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
                .avatar(mOwnVCardRealm != null ? mOwnVCardRealm.getAvatar() : null)
                .message(message2)
                .build());
        mChatMessageAdapter.notifyDataSetChanged();
        mChatInput.setText("");
    }

    public void showKeyBoard(){
        mChatMsgInputBox.showKeyboard();
    }

    public void hideKeyBoard(){
        mChatMsgInputBox.hideKeyboard();
    }

    public boolean isVisibleKeyBoard(){
        return mChatMsgInputBox.isKeyboardVisible();
    }
}
