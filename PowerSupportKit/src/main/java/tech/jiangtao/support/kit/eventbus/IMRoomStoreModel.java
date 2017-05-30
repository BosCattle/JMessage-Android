package tech.jiangtao.support.kit.eventbus;

import java.util.List;

/**
 * Class: IMRoomStoreModel </br>
 * Description: 保存群组信息的通知 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/05/2017 07:56</br>
 * Update: 30/05/2017 07:56 </br>
 **/

public class IMRoomStoreModel {
  // 群组id
  public String roomId;
  // 群组名字
  public String roomName;
  // 在房间中显示的用户名
  public String nickName;
  // 房间描述
  public String roomDesc;
  // 群组的头像
  public String avatar;
  // 群组成员
  public List<String> userIds;

  public IMRoomStoreModel(String roomId, String roomName, String roomDesc,
      String avatar, List<String> userIds) {
    this.roomId = roomId;
    this.roomName = roomName;
    this.roomDesc = roomDesc;
    this.avatar = avatar;
    this.userIds = userIds;
  }
}
