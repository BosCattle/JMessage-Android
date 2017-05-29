package tech.jiangtao.support.kit.manager;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.UpLoadServiceApi;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.model.jackson.Message;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import xiaofei.library.hermeseventbus.HermesEventBus;

import static android.content.ContentValues.TAG;

/**
 * Class: IMMessageManager </br>
 * Description: 消息管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:46</br>
 * Update: 27/05/2017 02:46 </br>
 **/

public class IMMessageManager {

  private static final String TAG = IMMessageManager.class.getSimpleName();
  private Realm mRealm;
  private IMListenerCollection.IMMessageChangeListener mIMMessageChangeListener;
  private UpLoadServiceApi mUpLoadServiceApi;
  private RealmResults<MessageRealm> mMessageRealms;

  private IMMessageManager() {
    connectRealm();
  }

  public static IMMessageManager geInstance() {
    return IMMessageManagerHolder.sIMMessageManager;
  }

  private static class IMMessageManagerHolder {
    private static final IMMessageManager sIMMessageManager = new IMMessageManager();
  }

  public void setmIMMessageChangeListener(
      IMListenerCollection.IMMessageChangeListener mIMMessageChangeListener) {
    this.mIMMessageChangeListener = mIMMessageChangeListener;
  }

  /**
   * 存储消息
   */
  public MessageRealm storeMessage(IMMessageResponseModel model, ContactRealm contactRealm,
      GroupRealm groupRealm) {
    // ---> 保存到消息表
    connectRealm();
    MessageRealm messageRealm = mRealm.createObject(MessageRealm.class, model.getId());
    LogUtils.d(TAG, "消息id" + model.getId());
    messageRealm.setSender(StringSplitUtil.splitDivider(model.getMessage().getMsgSender()));
    messageRealm.setReceiver(StringSplitUtil.splitDivider(model.getMessage().getMsgReceived()));
    messageRealm.setTextMessage(model.getMessage().getMessage());
    messageRealm.setTime(model.getDate());
    messageRealm.setThread(model.getThread());
    messageRealm.setType(model.getMessage().toString());
    messageRealm.setMessageType(model.getMessage().getType());
    messageRealm.setMessageStatus(false);
    messageRealm.setAuthor(model.getAuthor().toString());
    if (model.getMessage().getChatType().equals(MessageExtensionType.CHAT.toString())) {
      messageRealm.setMessageExtensionType(0);
    } else if (model.getMessage()
        .getChatType()
        .equals(MessageExtensionType.GROUP_CHAT.toString())) {
      messageRealm.setMessageExtensionType(1);
      messageRealm.setGroupId(model.getMessage().getGroup());
    }
    messageRealm.setContactRealm(contactRealm);
    messageRealm.setGroupRealm(groupRealm);
    mRealm.copyToRealm(messageRealm);
    if (mIMMessageChangeListener != null && model.getAuthor().equals(MessageAuthor.OWN)) {
      mIMMessageChangeListener.message(messageRealm);
    }
    return messageRealm;
  }

  /**
   * 获取所有的消息，并且，当消息改变时也会通知
   */
  public void getMessages(ContactRealm contactRealm,
      IMListenerCollection.IMMessageNotificationListener listener, int page) {
    // 查询
    int radice = 20;
    connectRealm();
    mRealm.executeTransaction(realm -> {
      mMessageRealms = mRealm.where(MessageRealm.class)
          .equalTo(SupportIM.SENDER, contactRealm.getUserId())
          .or()
          .equalTo(SupportIM.RECEIVER, contactRealm.getUserId())
          .findAll();
      mMessageRealms = mMessageRealms.sort("time", Sort.DESCENDING);
      int allPage = page(mMessageRealms, radice);
      if (allPage > 0 && page > 0) {
        listener.change(getPageMessages(mMessageRealms, page, allPage, radice), allPage);
      }
      mMessageRealms.addChangeListener(element -> {
        int totalPage = page(element, radice);
        listener.change(getPageMessages(element, page, totalPage, radice), totalPage);
      });
    });
  }

  /**
   * 计算page
   */
  private int page(List<MessageRealm> messageRealms, int redice) {
    int size = messageRealms.size();
    if (size == 0) {
      return 0;
    } else if (size > 0 && size < 20) {
      return 1;
    } else {
      return size / redice + ((size % redice) > 0 ? 1 : 0);
    }
  }

  /**
   * @param messageRealms 所有的数据
   * @param page 当前需要的页数
   * @return 根据页数获取数据
   * 此处需要反序取数据
   */
  private List<MessageRealm> getPageMessages(RealmResults<MessageRealm> messageRealms, int page,
      int allPage, int radice) {
    if (page <= allPage) {
      if (allPage == 1) {
        return messageRealms.sort("time", Sort.ASCENDING);
      }
      else if (page == allPage) {
        List<MessageRealm> messages = new ArrayList<>();
        for (int i = messageRealms.size()-1; i >= (page - 1) * radice; i--) {
          messages.add(messageRealms.get(i));
        }
        return messages;
      }
      else {
        List<MessageRealm> messages = new ArrayList<>();
        for (int i = page * radice; i >= (page - 1) * radice; i--) {
          messages.add(messageRealms.get(i));
        }
        return messages;
      }
    } else {
      LogUtils.e(TAG, "妈的智障，总页数都没有这么多....");
    }
    return null;
  }

  /**
   * 发送消息
   */
  public void sendMessage(Message message, IMListenerCollection.IMMessageChangeListener listener) {
    HermesEventBus.getDefault().postSticky(message);
    setmIMMessageChangeListener(listener);
  }

  /**
   * 判断服务是否连接
   */
  public void connectHermes() {
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
  }

  /**
   * 判断数据库是否连接
   */
  public void connectRealm() {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
  }

  /**
   * 上传文件
   */
  public void uploadFile(String path, String type,
      IMListenerCollection.IMFileUploadListener listener) {
    // use the FileUtils to get the actual file by uri
    File file = new File(path);
    // create RequestBody instance from file
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"), type);
    if (mUpLoadServiceApi == null) {
      mUpLoadServiceApi = ApiService.getInstance().createApiService(UpLoadServiceApi.class);
    }
    mUpLoadServiceApi.upload(body, typeBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(filePath -> {
          LogUtils.d(TAG, "uploadFile: " + filePath);
          listener.success(filePath);
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            // 死循环上传
            uploadFile(path, type, listener);
            listener.failed(new Result(400, throwable.getMessage()));
          }
        });
  }
}
