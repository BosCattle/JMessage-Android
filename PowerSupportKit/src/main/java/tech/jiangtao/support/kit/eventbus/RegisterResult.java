package tech.jiangtao.support.kit.eventbus;

/**
 * Class: RegisterResult </br>
 * Description:  注册结果 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 11:21 PM</br>
 * Update: 06/01/2017 11:21 PM </br>
 **/

public class RegisterResult {

  public RegisterAccount account;
  public String errorMessage;

  public RegisterResult(RegisterAccount account,String message){
    this.account = account;
    this.errorMessage = message;
  }

}
