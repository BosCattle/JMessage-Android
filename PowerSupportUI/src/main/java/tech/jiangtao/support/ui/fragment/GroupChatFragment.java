package tech.jiangtao.support.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.melink.bqmmsdk.bean.Emoji;
import com.melink.bqmmsdk.sdk.BQMM;
import com.melink.bqmmsdk.sdk.IBqmmSendMessageListener;
import com.melink.bqmmsdk.ui.keyboard.BQMMKeyboard;
import com.melink.bqmmsdk.widget.BQMMEditView;
import com.melink.bqmmsdk.widget.BQMMSendButton;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.activity.VideoPickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.archive.type.FileType;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.eventbus.RecieveLastMessage;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.ChatMessageAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.UpLoadServiceApi;
import tech.jiangtao.support.ui.model.ChatExtraModel;
import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.group.Friends;
import tech.jiangtao.support.ui.model.group.Groups;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.view.AudioManager;
import tech.jiangtao.support.ui.view.AudioRecordButton;
import tech.jiangtao.support.ui.viewholder.ExtraFuncViewHolder;
import work.wanghao.simplehud.SimpleHUD;
import xiaofei.library.hermeseventbus.HermesEventBus;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

/**
 * Class: ChatFragment </br>
 * Description: 聊天页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:40 AM</br>
 * Update: 02/12/2016 11:40 AM </br>
 **/
public class GroupChatFragment extends BaseFragment
    implements TextWatcher, View.OnClickListener, EasyViewHolder.OnItemClickListener,
    View.OnLongClickListener, AudioRecordButton.onAudioFinishRecordListener,
    SwipeRefreshLayout.OnRefreshListener, View.OnFocusChangeListener {

  public static final String USER_OWN = "own";
  public static final String USER_FRIEND = "friend";

  @BindView(R2.id.recycler) RecyclerView mRecycler;
  @BindView(R2.id.swift_refresh) SwipeRefreshLayout mSwiftRefresh;
  @BindView(R2.id.chat_speak) ImageView mChatSpeak;
  @BindView(R2.id.chat_add_other_information) ImageView mChatAddOtherInformation;
  @BindView(R2.id.chat_send_message) BQMMSendButton mChatSendMessage;
  @BindView(R2.id.container_send) FrameLayout mContainerSend;
  @BindView(R2.id.add_smile) CheckBox mAddSmile;
  @BindView(R2.id.chat_input) BQMMEditView mChatInput;
  @BindView(R2.id.chat_inline_container) RelativeLayout mChatInlineContainer;
  @BindView(R2.id.chat_msg_input_box) BQMMKeyboard mChatMsgInputBox;
  @BindView(R2.id.chat_send_other) RecyclerView mChatSendOther;
  @BindView(R2.id.chat_bottom) RelativeLayout mChatBottom;
  @BindView(R2.id.chat_audio_record) AudioRecordButton mAudioRecord;
  private ChatMessageAdapter mChatMessageAdapter;
  private List<ConstructMessage> mMessages = new ArrayList<>();
  private BQMM mBQMM;
  private UpLoadServiceApi mUpLoadServiceApi;
  private boolean mIsRefresh;
  private int mLastVisibleItem;
  private LinearLayoutManager mLinearLayoutManager;
  private String mUserJid;
  private int mPage = 1;
  private Groups mGroup;
  private Friends mOwn;
  private InputMethodManager mInputMethodManager;

  public static GroupChatFragment newInstance() {
    return new GroupChatFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    init();
    ButterKnife.bind(this, getView());
    return getView();
  }

  public void setExtraAdapter() {
    BaseEasyAdapter mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
    mBaseEasyAdapter.setOnClickListener((position, view) -> {
      ChatExtraModel model = (ChatExtraModel) mBaseEasyAdapter.get(position);
      if (model.name.equals("图片")) {
        Intent intent1 = new Intent(getContext(), ImagePickActivity.class);
        intent1.putExtra(IS_NEED_CAMERA, true);
        intent1.putExtra(Constant.MAX_NUMBER, 1);
        startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
      } else if (model.name.equals("文档")) {
        Intent intent4 = new Intent(getContext(), NormalFilePickActivity.class);
        intent4.putExtra(Constant.MAX_NUMBER, 1);
        intent4.putExtra(NormalFilePickActivity.SUFFIX,
            new String[] { "xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf" });
        startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
      } else if (model.name.equals("语音")) {
        Intent intent3 = new Intent(getContext(), AudioPickActivity.class);
        intent3.putExtra(IS_NEED_RECORDER, true);
        intent3.putExtra(Constant.MAX_NUMBER, 1);
        startActivityForResult(intent3, Constant.REQUEST_CODE_PICK_AUDIO);
      } else if (model.name.equals("视频")) {
        Intent intent2 = new Intent(getContext(), VideoPickActivity.class);
        intent2.putExtra(IS_NEED_CAMERA, true);
        intent2.putExtra(Constant.MAX_NUMBER, 1);
        startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_VIDEO);
      }
    });
    mBaseEasyAdapter.bind(ChatExtraModel.class, ExtraFuncViewHolder.class);
    mChatSendOther.setLayoutManager(new GridLayoutManager(getContext(), 4));
    ArrayList<ChatExtraModel> mChatExtraItems = new ArrayList<>();
    mChatSendOther.setAdapter(mBaseEasyAdapter);
    mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_photo, "图片"));
    mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_location, "位置"));
    mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_call, "打电话"));
    mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_call, "视频"));
    mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_call, "文档"));
    mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_call, "语音"));
    mBaseEasyAdapter.addAll(mChatExtraItems);
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  /**
   * 根据条件更新
   */
  public void updateItems(RealmResults<MessageRealm> messageRealmse, String userJid, int page) {
    mMessages.clear();
    LogUtils.d(TAG, "updateItems: 获取到消息的大小为:" + messageRealmse.size());
    for (int i =
        (messageRealmse.size() - (20 * page) > 20 ? messageRealmse.size() - (20 * page) : 0);
        i < (messageRealmse.size() - (20 * (page - 1))); i++) {
      LogUtils.d(TAG, "updateItems: 打印出当前的i值:" + i);
      LogUtils.d(TAG, "updateItems: 打印出当前的page值:" + page);
      if (StringSplitUtil.splitDivider(messageRealmse.get(i).getSender())
          .equals(StringSplitUtil.splitDivider(userJid))) {
        //自己的消息
        Message message1 = new Message();
        message1.paramContent = messageRealmse.get(i).getTextMessage();
        if (messageRealmse.get(i).getMessageType().equals(DataExtensionType.TEXT.toString())) {
          message1.type = FileType.TYPE_TEXT;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
              .avatar(mGroup != null ? mGroup.avatar : null)
              .message(message1)
              .build());
        } else if (messageRealmse.get(i)
            .getMessageType()
            .equals(DataExtensionType.IMAGE.toString())) {
          message1.fimePath = messageRealmse.get(i).getTextMessage();
          message1.type = FileType.TYPE_IMAGE;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.IMAGE_MESSAGE_MINE)
              .avatar(mOwn != null && mOwn.avatar != null ? mOwn.avatar : null)
              .message(message1)
              .build());
        } else if (messageRealmse.get(i)
            .getMessageType()
            .equals(DataExtensionType.AUDIO.toString())) {
          message1.fimePath = messageRealmse.get(i).getTextMessage();
          message1.time = 10;
          message1.type = FileType.TYPE_AUDIO;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.AUDIO_MESSAGE_MINE)
              .avatar(mOwn != null && mOwn.avatar != null ? mOwn.avatar : null)
              .message(message1)
              .build());
        }
      } else {
        //别人发送的消息
        Message message1 = new Message();
        message1.paramContent = messageRealmse.get(i).getTextMessage();
        if (messageRealmse.get(i).getMessageType().equals(DataExtensionType.TEXT.toString())) {
          message1.paramContent = messageRealmse.get(i).getTextMessage();
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
              .avatar(mGroup.avatar)
              .message(message1)
              .build());
        } else if (messageRealmse.get(i)
            .getMessageType()
            .equals(DataExtensionType.IMAGE.toString())) {
          message1.fimePath = messageRealmse.get(i).getTextMessage();
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.IMAGE_MESSAGE_OTHER)
              .avatar(mGroup.avatar)
              .message(message1)
              .build());
        } else if (messageRealmse.get(i)
            .getMessageType()
            .equals(DataExtensionType.AUDIO.toString())) {
          message1.fimePath = messageRealmse.get(i).getTextMessage();
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.AUDIO_MESSAGE_OTHER)
              .avatar(mGroup.avatar)
              .message(message1)
              .build());
        }
      }
    }
    //将数据更新到上一个20
    mRecycler.scrollToPosition(19);
    mSwiftRefresh.setRefreshing(false);
  }

  @Override public int layout() {
    return R.layout.fragment_chat;
  }

  private void init() {
    mOwn = getArguments().getParcelable(USER_OWN);
    mGroup = getArguments().getParcelable(USER_FRIEND);
    final AppPreferences appPreferences = new AppPreferences(getContext());
    try {
      mUserJid = appPreferences.getString(SupportIM.USER_ID);
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    mUpLoadServiceApi = ApiService.getInstance().createApiService(UpLoadServiceApi.class);
    mInputMethodManager =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    setViewListener();
    setUpBQMM();
    setAdapter();
    setExtraAdapter();
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
      @Override public void onSendMixedMessage(List<Object> list, boolean b) {
      }

      //单个大表情
      @Override public void onSendFace(Emoji emoji) {

      }
    });
  }

  private void setViewListener() {
    mChatInput.addTextChangedListener(this);
    mChatInput.setOnFocusChangeListener(this);
    mChatSpeak.setOnLongClickListener(this);
    mChatAddOtherInformation.setOnClickListener(this);
    mChatSendMessage.setOnClickListener(this);
    mAddSmile.setOnClickListener(this);
    mAudioRecord.setMonAudioFinishRecordListener(this);
  }

  public void setAdapter() {
    mChatMessageAdapter = new ChatMessageAdapter(getContext(), mMessages);
    mLinearLayoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    mRecycler.setLayoutManager(mLinearLayoutManager);
    mRecycler.setAdapter(mChatMessageAdapter);
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (s == null || Objects.equals(s.toString(), "")) {
      LogUtils.e(TAG, "onTextChanged: ");
      mChatSendMessage.setVisibility(View.GONE);
      mChatAddOtherInformation.setVisibility(View.VISIBLE);
    } else {
      mBQMM.startShortcutPopupWindowByoffset(s.toString(), mChatSendMessage, 0, 20);
      mChatAddOtherInformation.setVisibility(View.GONE);
      mChatSendMessage.setVisibility(View.VISIBLE);
    }
  }

  @Override public void afterTextChanged(Editable s) {

  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(RecieveLastMessage message) {
    LogUtils.d("----------->", "onMessage: " + message);
    if (StringSplitUtil.splitDivider(message.userJID).equals(mGroup.groupUid)
        || StringSplitUtil.splitDivider(message.ownJid).equals(mGroup.groupUid)) {
      if (message.messageAuthor == MessageAuthor.FRIEND) {
        Message message1 = new Message();
        message1.paramContent = message.message;
        if (message.messageType == DataExtensionType.TEXT) {
          message1.paramContent = message.message;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
              .avatar(mGroup.avatar)
              .message(message1)
              .build());
        } else if (message.messageType == DataExtensionType.IMAGE) {
          message1.fimePath = message.message;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.IMAGE_MESSAGE_OTHER)
              .avatar(mGroup.avatar)
              .message(message1)
              .build());
          LogUtils.d(TAG, "onMessage: " + message1);
        } else if (message.messageType == DataExtensionType.AUDIO) {
          message1.fimePath = message.message;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.AUDIO_MESSAGE_OTHER)
              .avatar(mGroup.avatar)
              .message(message1)
              .build());
          LogUtils.d(TAG, "onMessage: " + message1);
        }
      } else if (message.messageAuthor == MessageAuthor.OWN) {
        Message message2 = new Message();
        message2.paramContent = message.message;
        if (message.messageType == DataExtensionType.TEXT) {
          message2.type = FileType.TYPE_TEXT;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
              .avatar(mOwn != null ? mOwn.avatar : null)
              .message(message2)
              .build());
        } else if (message.messageType == DataExtensionType.IMAGE) {
          message2.fimePath = message.message;
          message2.type = FileType.TYPE_IMAGE;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.IMAGE_MESSAGE_MINE)
              .avatar(mOwn != null && mOwn.avatar != null ? mOwn.avatar : null)
              .message(message2)
              .build());
        } else if (message.messageType == DataExtensionType.AUDIO) {
          message2.fimePath = message.message;
          message2.time = 10;
          message2.type = FileType.TYPE_AUDIO;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.AUDIO_MESSAGE_MINE)
              .avatar(mOwn != null && mOwn.avatar != null ? mOwn.avatar : null)
              .message(message2)
              .build());
        }
      }
    }
    updateChatData();
  }

  // TODO: 24/12/2016 添加类型
  public void addMessageToAdapter(MessageRealm realm) {
    Message message1 = new Message();
    message1.paramContent = realm.getTextMessage();
    LogUtils.d(TAG, "addMessageToAdapter: " + realm.getSender());
    LogUtils.d(TAG, "addMessageToAdapter-----: " + mGroup.groupUid);
    if (mGroup != null && realm.getSender().equals(mGroup.groupUid)) {
      mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
          .avatar(mGroup != null ? mGroup.groupUid : null)
          .message(message1)
          .build());
    } else {
      mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_MINE)
          .avatar(mOwn != null ? mOwn.avatar : null)
          .message(message1)
          .build());
    }
    updateChatData();
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void onItemClick(int position, View view) {

  }

  @Override public boolean onLongClick(View v) {
    int i = v.getId();
    if (i == R.id.chat_speak) {
    }
    return true;
  }

  @OnClick({
      R2.id.chat_add_other_information, R2.id.chat_send_message, R2.id.add_smile,
      R2.id.chat_audio_record, R2.id.chat_speak
  }) public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.chat_add_other_information) {
      LogUtils.d(TAG, "onClick: 点击了加号");
      hideKeyBoard();
      if (mChatSendOther.getVisibility() == View.VISIBLE) {
        mChatSendOther.setVisibility(View.GONE);
      } else {
        mChatSendOther.setVisibility(View.VISIBLE);
      }
    } else if (i == R.id.chat_send_message) {
      sendMyFriendMessage(mChatInput.getText().toString(), DataExtensionType.TEXT);
    } else if (i == R.id.add_smile) {
      mChatSendOther.setVisibility(View.GONE);
      //切换键盘
      if (mInputMethodManager.isActive() && !isVisibleKeyBoard()) {
        mInputMethodManager.hideSoftInputFromWindow(
            getActivity().getWindow().getDecorView().getWindowToken(), 0);
        showKeyBoard();
      } else {
        hideKeyBoard();
      }
    } else if (i == R.id.chat_audio_record) {

    } else if (i == R.id.chat_speak) {
      if (mAudioRecord.getVisibility() == View.VISIBLE) {
        mChatInput.setVisibility(View.VISIBLE);
        mAudioRecord.setVisibility(View.GONE);
      } else {
        mChatInput.setVisibility(View.GONE);
        mAudioRecord.setVisibility(View.VISIBLE);
      }
    }
  }

  /**
   * 发送消息到对方，并且添加到本地
   */
  public void sendMyFriendMessage(String message, DataExtensionType type) {
    TextMessage message1 =
        new TextMessage(org.jivesoftware.smack.packet.Message.Type.groupchat, mGroup.node, message,
            type);
    message1.messageType = type;
    HermesEventBus.getDefault().post(message1);
    //将消息更新到本地
    mChatInput.setText("");
  }

  public void showKeyBoard() {
    mChatMsgInputBox.showKeyboard();
  }

  public void hideKeyBoard() {
    mChatMsgInputBox.hideKeyboard();
  }

  public boolean isVisibleKeyBoard() {
    return mChatMsgInputBox.isKeyboardVisible();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    LogUtils.d(TAG, "onActivityResult: 进入fragment的回调");
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
        ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
        uploadFile(list.get(0).getPath(), DataExtensionType.IMAGE.toString());
        mChatSendOther.setVisibility(View.GONE);
      } else if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {

      } else if (requestCode == Constant.REQUEST_CODE_PICK_AUDIO) {

      } else if (requestCode == Constant.REQUEST_CODE_PICK_VIDEO) {

      }
    }
  }

  @Override public void onFinishRecord(float seconds, String filePath) {
    //构建本地发送消息，开启服务器发送消息到对方
    uploadFile(filePath, DataExtensionType.AUDIO.toString());
  }

  public void updateChatData() {
    mChatMessageAdapter.notifyDataSetChanged();
    if (mMessages.size() > 1) {
      mRecycler.scrollToPosition(mMessages.size() - 1);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    AudioManager.getInstance().onDestroy();
  }

  public void uploadFile(String path, String type) {
    // use the FileUtils to get the actual file by uri
    File file = new File(path);
    // create RequestBody instance from file
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"), type);
    mUpLoadServiceApi.upload(body, typeBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(filePath -> {
          LogUtils.d(TAG, "uploadFile: " + filePath);
          //发送消息添加到本地，然后发送拓展消息到对方
          if (type.equals(DataExtensionType.IMAGE.toString())) {
            sendMyFriendMessage(filePath.resourceId, DataExtensionType.IMAGE);
          }
          if (type.equals(DataExtensionType.AUDIO.toString())) {
            sendMyFriendMessage(filePath.resourceId, DataExtensionType.AUDIO);
          }
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            SimpleHUD.showErrorMessage(getContext(), "上传失败" + throwable.toString());
          }
        });
  }

  @Override public void onRefresh() {
    mPage += 1;
    LogUtils.d(TAG, "onRefresh: 打印出当前的mPage" + mPage);
  }

  @Override public void onFocusChange(View v, boolean hasFocus) {
    if (hasFocus) {
      hideKeyBoard();
      LogUtils.d(TAG, "onFocusChange: 获取");
    } else {
      LogUtils.d(TAG, "失去焦点");
    }
  }
}
