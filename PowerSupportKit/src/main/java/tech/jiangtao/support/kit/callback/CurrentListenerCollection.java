package tech.jiangtao.support.kit.callback;

/**
 * Created by kevin on 27/05/2017.
 */

public class CurrentListenerCollection {

  private LoginListener loginListener;

  public void setLoginListener(LoginListener loginListener) {
    this.loginListener = loginListener;
  }

  public LoginListener getLoginListener() {
    return loginListener;
  }
}
