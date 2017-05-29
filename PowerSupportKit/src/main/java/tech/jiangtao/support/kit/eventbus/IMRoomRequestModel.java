package tech.jiangtao.support.kit.eventbus;

/**
 * Class: IMRoomRequestModel </br>
 * Description: 创建房间的请求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/05/2017 04:29</br>
 * Update: 30/05/2017 04:29 </br>
 **/

public class IMRoomRequestModel {
  public String roomName;
  // 在房间中显示的用户名
  public String nickName;
  // 房间描述
  public String roomDesc;
  // 群组的头像
  public String avatar;

  public IMRoomRequestModel(String roomName, String nickName, String roomDesc) {
    this.roomName = roomName;
    this.nickName = nickName;
    this.roomDesc = roomDesc;
  }

  public IMRoomRequestModel(String roomName, String nickName, String roomDesc,String avatar) {
    this.roomName = roomName;
    this.nickName = nickName;
    this.roomDesc = roomDesc;
    this.avatar = avatar;
  }
}
