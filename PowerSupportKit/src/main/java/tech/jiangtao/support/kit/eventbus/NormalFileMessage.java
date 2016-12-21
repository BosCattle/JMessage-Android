package tech.jiangtao.support.kit.eventbus;

import tech.jiangtao.support.kit.archive.type.FileType;

/**
 * Created by jiang on 2016/12/21.
 */

public class NormalFileMessage extends BaseMessage {

    public FileType mFileType;
    public String fileName;
    public String fileAddress;
    public String userJID;

    public NormalFileMessage(FileType mFileType,String fileName,String fileAddress,String userJID) {
        super(null);
        this.fileAddress = fileAddress;
        this.fileName = fileName;
        this.userJID = userJID;
        this.mFileType = mFileType;
    }
}
