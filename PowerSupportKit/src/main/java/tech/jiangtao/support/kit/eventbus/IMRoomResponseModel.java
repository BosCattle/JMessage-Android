package tech.jiangtao.support.kit.eventbus;

import java.util.List;

/**
 * Class: IMRoomResponseModel </br>
 * Description: 创建群组的响应 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/05/2017 07:18</br>
 * Update: 30/05/2017 07:18 </br>
 **/

public class IMRoomResponseModel {
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

  public IMRoomResponseModel(String roomId, String roomName, String roomDesc,
      String avatar, List<String> userIds) {
    this.roomId = roomId;
    this.roomName = roomName;
    this.roomDesc = roomDesc;
    this.avatar = avatar;
    this.userIds = userIds;
  }
}
