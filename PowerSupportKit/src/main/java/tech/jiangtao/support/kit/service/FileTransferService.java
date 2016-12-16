package tech.jiangtao.support.kit.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import java.io.File;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

/**
 * 不用启动单独的线程来执行耗时的任务
 * 发送语音文件，图片，文件等操作
 * 利用后台线程
 */
public class FileTransferService extends IntentService{

  public static final String FILE_TRANSFER_FILE_NAME = "file_name";
  public static final String FILE_TRANSFER_USER_JID = "user_jid";
  public static final String FILE_TRANSFER_EXTRA_MESSAGE = "extra_message";
  public static final String FILE_TRANSFER_MESSAGE_TYPE = "message_type";
  private FileTransferManager mFileTransferManager;

  public FileTransferService(String name) {
    super(name);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    mFileTransferManager = FileTransferManager.getInstanceFor(SupportService.getmXMPPConnection());
    return START_STICKY;
  }

  @Override protected void onHandleIntent(Intent intent) {
    String fileName = intent.getStringExtra(FILE_TRANSFER_FILE_NAME);
    String userJID = intent.getStringExtra(FILE_TRANSFER_USER_JID);
    String message = intent.getStringExtra(FILE_TRANSFER_EXTRA_MESSAGE);
    String type = intent.getStringExtra(FILE_TRANSFER_MESSAGE_TYPE);
    OutgoingFileTransfer mOutgoingFileTransfer = mFileTransferManager.createOutgoingFileTransfer(userJID);
    try {
      //The size in bytes of the file that will be transmitted.
      File file = new File(fileName);
      mOutgoingFileTransfer.sendFile(file,message);
    } catch (SmackException e) {
      e.printStackTrace();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mFileTransferManager = null;
  }
}
