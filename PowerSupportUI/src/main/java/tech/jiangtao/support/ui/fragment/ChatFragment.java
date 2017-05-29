package tech.jiangtao.support.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
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
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.archive.type.FileType;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.ReceiveLastMessage;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.manager.IMAccountManager;
import tech.jiangtao.support.kit.manager.IMConversationManager;
import tech.jiangtao.support.kit.manager.IMMessageManager;
import tech.jiangtao.support.kit.model.IMFilePath;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.ChatMessageAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.UpLoadServiceApi;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.model.ChatExtraModel;
import tech.jiangtao.support.kit.model.Message;
import tech.jiangtao.support.kit.model.User;
import tech.jiangtao.support.kit.model.type.MessageType;
import tech.jiangtao.support.kit.model.type.TransportType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.utils.ResourceAddress;
import tech.jiangtao.support.ui.view.AudioManager;
import tech.jiangtao.support.ui.view.AudioRecordButton;
import tech.jiangtao.support.ui.viewholder.ExtraFuncViewHolder;
import work.wanghao.simplehud.SimpleHUD;

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
public class ChatFragment extends BaseFragment
    implements TextWatcher, View.OnClickListener, EasyViewHolder.OnItemClickListener,
    View.OnLongClickListener, AudioRecordButton.onAudioFinishRecordListener,
    SwipeRefreshLayout.OnRefreshListener, View.OnFocusChangeListener,
    IMListenerCollection.IMMessageNotificationListener, IMListenerCollection.IMFileUploadListener,
    IMListenerCollection.IMMessageChangeListener, IBqmmSendMessageListener, View.OnTouchListener {

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
  private List<MessageRealm> mMessages;
  private ContactRealm mContactRealm;
  private ContactRealm mOwnContactRealm;
  private BQMM mBQMM;
  private int mLastVisibleItem;
  private LinearLayoutManager mLinearLayoutManager;
  private int mPage = 1;
  private List<MessageRealm> mMessageRealm;
  private InputMethodManager mInputMethodManager;
  private int mTotalPage;
  private int mCurrentPage = 1;

  public ChatFragment() {
  }

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

  public void inputAdapter() {
    BaseEasyAdapter mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
    mBaseEasyAdapter.setOnClickListener((position, view) -> {
      ChatExtraModel model = (ChatExtraModel) mBaseEasyAdapter.get(position);
      switch (model.name) {
        case "图片":
          Intent intent1 = new Intent(getContext(), ImagePickActivity.class);
          intent1.putExtra(IS_NEED_CAMERA, true);
          intent1.putExtra(Constant.MAX_NUMBER, 1);
          startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
          break;
        case "文档":
          Intent intent4 = new Intent(getContext(), NormalFilePickActivity.class);
          intent4.putExtra(Constant.MAX_NUMBER, 1);
          intent4.putExtra(NormalFilePickActivity.SUFFIX,
              new String[] { "xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf" });
          startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
          break;
        case "语音":
          Intent intent3 = new Intent(getContext(), AudioPickActivity.class);
          intent3.putExtra(IS_NEED_RECORDER, true);
          intent3.putExtra(Constant.MAX_NUMBER, 1);
          startActivityForResult(intent3, Constant.REQUEST_CODE_PICK_AUDIO);
          break;
        case "视频":
          Intent intent2 = new Intent(getContext(), VideoPickActivity.class);
          intent2.putExtra(IS_NEED_CAMERA, true);
          intent2.putExtra(Constant.MAX_NUMBER, 1);
          startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_VIDEO);
          break;
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
  public void updateItems(List<MessageRealm> messageRealmse, String userJid, int page) {
    mMessages.clear();
    mChatMessageAdapter.clear();
    mMessages.addAll(messageRealmse);
    mChatMessageAdapter.notifyDataSetChanged();
  }

  @Override public int layout() {
    return R.layout.fragment_chat;
  }

  private void init() {
    mInputMethodManager =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    setViewListener();
    setUpRefreshing();
    setUpBQMM();
    setAdapter();
    inputAdapter();
  }

  private void setUpRefreshing() {
    mSwiftRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
    mSwiftRefresh.setDistanceToTriggerSync(300);
    mSwiftRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
    mSwiftRefresh.setSize(SwipeRefreshLayout.LARGE);
    mSwiftRefresh.setOnRefreshListener(this);
    mRecycler.addOnScrollListener(new OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && mLastVisibleItem + 1 == mChatMessageAdapter.getItemCount()) {
          //已经跳到最后一项
          if (mPage > 1) {
            mPage -= 1;
            new Handler().postDelayed(
                () -> updateItems(mMessageRealm, mOwnContactRealm.getUserId(), mPage), 2000);
          }
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
      }
    });
  }

  @SuppressLint("ClickableViewAccessibility") private void setUpBQMM() {
    mBQMM = BQMM.getInstance();
    mBQMM.setEditView(mChatInput);
    mBQMM.setKeyboard(mChatMsgInputBox);
    mBQMM.setSendButton(mChatSendMessage);
    mBQMM.load();
    mChatInput.setOnTouchListener(this);
    mBQMM.setBqmmSendMsgListener(this);
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
    mMessages = new ArrayList<>();
    mChatMessageAdapter = new ChatMessageAdapter(getContext(), mMessages);
    mLinearLayoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    mRecycler.setLayoutManager(mLinearLayoutManager);
    mRecycler.setAdapter(mChatMessageAdapter);
    mContactRealm = getArguments().getParcelable(SupportIM.VCARD);
    mOwnContactRealm = new IMAccountManager(getContext()).getAccount();
    IMMessageManager.geInstance().getMessages(mContactRealm, ChatFragment.this, mCurrentPage);
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

  @Override public void onPause() {
    super.onPause();
    //将会话的为未读数目设置为0
    IMConversationManager.geInstance().setChatConversationReadStatus(getContext(), mContactRealm);
  }

  @Override public void onItemClick(int position, View view) {

  }

  @Override public boolean onLongClick(View v) {
    return true;
  }

  @OnClick({
      R2.id.chat_add_other_information, R2.id.chat_send_message, R2.id.add_smile,
      R2.id.chat_audio_record, R2.id.chat_speak
  }) public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.chat_add_other_information) {
      LogUtils.d(TAG, "onClick: 点击了加号");
      hideKeyBoard();
      if (mChatSendOther.getVisibility() == View.VISIBLE) {
        mChatSendOther.setVisibility(View.GONE);
      } else {
        mChatSendOther.setVisibility(View.VISIBLE);
      }
    } else if (id == R.id.chat_send_message) {
      sendMyFriendMessage(mChatInput.getText().toString(), DataExtensionType.TEXT,
          MessageExtensionType.CHAT);
    } else if (id == R.id.add_smile) {
      mChatSendOther.setVisibility(View.GONE);
      //切换键盘
      if (mInputMethodManager.isActive() && !isVisibleKeyBoard()) {
        mInputMethodManager.hideSoftInputFromWindow(
            getActivity().getWindow().getDecorView().getWindowToken(), 0);
        showKeyBoard();
      } else {
        hideKeyBoard();
      }
    } else if (id == R.id.chat_audio_record) {

    } else if (id == R.id.chat_speak) {
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
  public void sendMyFriendMessage(String message, DataExtensionType type,
      MessageExtensionType messageExtensionType) {
    tech.jiangtao.support.kit.model.jackson.Message messageBody =
        new tech.jiangtao.support.kit.model.jackson.Message();
    messageBody.setMessage(message);
    messageBody.setChatType(messageExtensionType.toString());
    messageBody.setType(type.toString());
    messageBody.setMsgSender(mOwnContactRealm.getUserId());
    messageBody.setMsgReceived(mContactRealm.getUserId());
    IMMessageManager.geInstance().sendMessage(messageBody, this);
    //将消息更新到本地
    mChatInput.setText("");
    hideKeyBoard();
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
        ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
        uploadFile(list.get(0).getPath(), DataExtensionType.FILE.toString());
        mChatSendOther.setVisibility(View.GONE);
      } else if (requestCode == Constant.REQUEST_CODE_PICK_AUDIO) {
        ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO);
        uploadFile(list.get(0).getPath(), DataExtensionType.AUDIO.toString());
        mChatSendOther.setVisibility(View.GONE);
      } else if (requestCode == Constant.REQUEST_CODE_PICK_VIDEO) {
        ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
        uploadFile(list.get(0).getPath(), DataExtensionType.VIDEO.toString());
        mChatSendOther.setVisibility(View.GONE);
      }
    }
  }

  @Override public void onFinishRecord(float seconds, String filePath) {
    //构建本地发送消息，开启服务器发送消息到对方
    uploadFile(filePath, DataExtensionType.AUDIO.toString());
  }

  /**
   * 当前的意思是，将位置放到最后一行
   */
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

  /**
   *
   * @param path
   * @param type
   */
  public void uploadFile(String path, String type) {
    IMMessageManager.geInstance().uploadFile(path, type, this);
  }

  /**
   * 下拉，加载历史消息
   */
  @Override public void onRefresh() {
    if (mCurrentPage!=mTotalPage) {
      mCurrentPage += 1;
      LogUtils.d(TAG, "onRefresh: 打印出当前的mPage" + mPage);
      IMMessageManager.geInstance().getMessages(mContactRealm, ChatFragment.this, mCurrentPage);
    }
  }

  @Override public void onFocusChange(View v, boolean hasFocus) {
    if (hasFocus) {
      hideKeyBoard();
    }
  }

  @Override public void change(List<MessageRealm> messageRealms, int page) {
    mTotalPage = page;
    if (messageRealms != null && mOwnContactRealm != null) {
      mMessageRealm = messageRealms;
      updateItems(messageRealms, mOwnContactRealm.getUserId(), mPage);
      updateChatData();
    }
    mSwiftRefresh.setRefreshing(false);
  }

  @Override public void success(IMFilePath path) {
    //发送消息添加到本地，然后发送拓展消息到对方
    if (path.type.toString().equals(DataExtensionType.IMAGE.toString())) {
      sendMyFriendMessage(ResourceAddress.url(path.resourceId, TransportType.IMAGE),
          DataExtensionType.IMAGE, MessageExtensionType.CHAT);
    }
    if (path.type.toString().equals(DataExtensionType.AUDIO.toString())) {
      sendMyFriendMessage(ResourceAddress.url(path.resourceId, TransportType.AUDIO),
          DataExtensionType.AUDIO, MessageExtensionType.CHAT);
    }
    if (path.type.toString().equals(DataExtensionType.VIDEO.toString())) {
      sendMyFriendMessage(ResourceAddress.url(path.resourceId, TransportType.AUDIO),
          DataExtensionType.VIDEO, MessageExtensionType.CHAT);
    }
  }

  @Override public void failed(Result result) {
    LogUtils.d(TAG, "上传文件失败," + result.getMsg() + "    正在重试....");
  }

  @Override public void message(MessageRealm messageRealm) {
    LogUtils.d(TAG, "消息发送成功....");
  }

  @Override public void error(Result result) {
    LogUtils.e(TAG, "消息发送失败...." + result.getMsg());
  }

  /**
   * 图文混排消息
   */
  @Override public void onSendMixedMessage(List<Object> list, boolean b) {

  }

  @Override public void onSendFace(Emoji emoji) {

  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    mAddSmile.setChecked(false);
    return false;
  }
}
