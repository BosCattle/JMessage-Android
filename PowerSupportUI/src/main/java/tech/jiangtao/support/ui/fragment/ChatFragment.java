package tech.jiangtao.support.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.activity.VideoPickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import io.realm.Realm;
import io.realm.RealmResults;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.archive.type.FileType;
import tech.jiangtao.support.kit.eventbus.BaseMessage;
import tech.jiangtao.support.kit.eventbus.NormalFileMessage;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.FileTransferService;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleArchiveMessage;
import tech.jiangtao.support.kit.util.ErrorAction;
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
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.utils.CommonUtils;
import tech.jiangtao.support.ui.view.AudioRecordButton;
import tech.jiangtao.support.ui.view.MediaManager;
import tech.jiangtao.support.ui.viewholder.ExtraFuncViewHolder;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;
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
    View.OnLongClickListener, AudioRecordButton.onAudioFinishRecordListener {

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
  private List<ConstructMessage> mMessages;
  private VCardRealm mVCardRealm;
  private VCardRealm mOwnVCardRealm;
  private Realm mRealm;
  private BQMM mBQMM;
  private UpLoadServiceApi mUpLoadServiceApi;

  public static ChatFragment newInstance() {
    return new ChatFragment();
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

  public void loadOwnRealm() {
    mVCardRealm = getArguments().getParcelable("vCard");
    mRealm = Realm.getDefaultInstance();
    RealmResults<VCardRealm> realms = mRealm.where(VCardRealm.class)
        .equalTo("jid", SupportService.getmXMPPConnection().getUser())
        .findAll();
    if (realms.size() != 0) {
      mOwnVCardRealm = realms.first();
    }
  }

  @Override public int layout() {
    return R.layout.fragment_chat;
  }

  private void init() {
    mUpLoadServiceApi = ApiService.getInstance().createApiService(UpLoadServiceApi.class);
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
      @Override public void onSendMixedMessage(List<Object> list, boolean b) {
        sendMyFriendMessage();
      }

      //单个大表情
      @Override public void onSendFace(Emoji emoji) {

      }
    });
  }

  private void setViewListener() {
    mChatInput.addTextChangedListener(this);
    mChatSpeak.setOnLongClickListener(this);
    mChatAddOtherInformation.setOnClickListener(this);
    mChatSendMessage.setOnClickListener(this);
    mAddSmile.setOnClickListener(this);
    mAudioRecord.setMonAudioFinishRecordListener(this);
  }

  public void setAdapter() {
    mMessages = new ArrayList<>();
    mChatMessageAdapter = new ChatMessageAdapter(getContext(), mMessages);
    mRecycler.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mRecycler.setAdapter(mChatMessageAdapter);
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override
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

  @Override public void afterTextChanged(Editable s) {

  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(BaseMessage message) {
    Log.d("----------->", "onMessage: " + message);
    // 根据消息类型，作出调转服务
    if (message instanceof RecieveMessage) {
      if (message.message != null && !String.valueOf(message.message).equals("")) {
        Message message1 = new Message();
        message1.paramContent = (String) message.message;
        mMessages.add(new ConstructMessage.Builder().itemType(MessageType.TEXT_MESSAGE_OTHER)
            .avatar(mVCardRealm.getAvatar())
            .message(message1)
            .build());
        mChatMessageAdapter.notifyDataSetChanged();
      }
    } else if (message instanceof NormalFileMessage) {
      NormalFileMessage message2 = (NormalFileMessage) message;
      if (message2.mFileType == FileType.TYPE_IMAGE) {
        Message message1 = new Message();
        message1.fileName = message2.fileName;
        message1.fimePath = message2.fileAddress;
        message1.type = message2.mFileType;
        mMessages.add(new ConstructMessage.Builder().itemType(MessageType.IMAGE_MESSAGE_OTHER)
            .avatar(mVCardRealm.getAvatar())
            .message(message1)
            .build());
      } else if (message2.mFileType == FileType.TYPE_AUDIO) {
        Message message1 = new Message();
        message1.fileName = message2.fileName;
        message1.fimePath = message2.fileAddress;
        message1.type = message2.mFileType;
        mMessages.add(new ConstructMessage.Builder().itemType(MessageType.AUDIO_MESSAGE_OTHER)
            .avatar(mVCardRealm.getAvatar())
            .message(message1)
            .build());
      }
      mChatMessageAdapter.notifyDataSetChanged();
    }
  }

  @Override public void onStop() {
    super.onStop();
    mRealm.close();
  }

  // TODO: 24/12/2016 添加类型
  public void addMessageToAdapter(MessageRealm realm) {
    Message message1 = new Message();
    message1.paramContent = realm.getTextMessage();
    Log.d(TAG, "addMessageToAdapter: " + realm.getMainJID());
    Log.d(TAG, "addMessageToAdapter-----: " + mVCardRealm.getJid());
    if (mVCardRealm != null && realm.getMainJID().equals(mVCardRealm.getJid())) {
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

  @Override public void onPause() {
    super.onPause();
    // TODO: 26/12/2016 暂时取消掉消息缓存 
    //SimpleArchiveMessage message = new SimpleArchiveMessage();
    //requestAllMessageArchive(message.getLastUpdateTime());
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
    Log.d(TAG, "onActivityResult: 进入fragment的回调");
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
        ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
        uploadFile(list.get(0).getPath(),"image");
      } else if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {

      } else if (requestCode == Constant.REQUEST_CODE_PICK_AUDIO) {

      } else if (requestCode == Constant.REQUEST_CODE_PICK_VIDEO) {

      }
    }
  }

  public void startServiceToUpload(String filePath) {
    Intent intent = new Intent(getActivity(), FileTransferService.class);
    Bundle bundle = new Bundle();
    bundle.putString(FileTransferService.FILE_TRANSFER_FILE_NAME, filePath);
    bundle.putString(FileTransferService.FILE_TRANSFER_EXTRA_MESSAGE, "文件来啦!");
    bundle.putString(FileTransferService.FILE_TRANSFER_USER_JID, mVCardRealm.getJid());
    intent.putExtras(bundle);
    getActivity().startService(intent);
  }

  @Override public void onFinishRecord(float seconds, String filePath) {
    //构建本地发送消息，开启服务器发送消息到对方
    startServiceToUpload(filePath);
    Message message1 = new Message();
    message1.fimePath = filePath;
    message1.time = seconds;
    message1.type = FileType.TYPE_AUDIO;
    mMessages.add(new ConstructMessage.Builder().itemType(MessageType.AUDIO_MESSAGE_MINE)
        .avatar(mOwnVCardRealm != null && mOwnVCardRealm.getAvatar() != null
            ? mOwnVCardRealm.getAvatar() : null)
        .message(message1)
        .build());
    mChatMessageAdapter.notifyDataSetChanged();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  public void uploadFile(String path, String type) {
    // use the FileUtils to get the actual file by uri
    File file = new File(path);

    // create RequestBody instance from file
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"),type);
    mUpLoadServiceApi.upload(body,typeBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(filePath -> {
          Log.d(TAG, "uploadFile: " + filePath);
          //发送消息添加到本地，然后发送拓展消息到对方
          Message message1 = new Message();
          message1.fimePath = CommonUtils.getUrl("image",filePath.filePath);
          message1.type = FileType.TYPE_IMAGE;
          mMessages.add(new ConstructMessage.Builder().itemType(MessageType.IMAGE_MESSAGE_MINE)
              .avatar(mOwnVCardRealm != null && mOwnVCardRealm.getAvatar() != null
                  ? mOwnVCardRealm.getAvatar() : null)
              .message(message1)
              .build());
          mChatMessageAdapter.notifyDataSetChanged();
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
          }
        });
  }
}
