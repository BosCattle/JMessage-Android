// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding extends BaseActivity_ViewBinding {
  private LoginActivity target;

  private View view2131689638;

  private View view2131689640;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mLoginUsername = Utils.findRequiredViewAsType(source, R.id.login_username, "field 'mLoginUsername'", AppCompatEditText.class);
    target.mLoginPassword = Utils.findRequiredViewAsType(source, R.id.login_password, "field 'mLoginPassword'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.login_button, "field 'mLoginButton' and method 'onClick'");
    target.mLoginButton = Utils.castView(view, R.id.login_button, "field 'mLoginButton'", AppCompatButton.class);
    view2131689638 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register, "field 'mRegisterText' and method 'onClick'");
    target.mRegisterText = Utils.castView(view, R.id.register, "field 'mRegisterText'", AppCompatTextView.class);
    view2131689640 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mLoginUsername = null;
    target.mLoginPassword = null;
    target.mLoginButton = null;
    target.mRegisterText = null;

    view2131689638.setOnClickListener(null);
    view2131689638 = null;
    view2131689640.setOnClickListener(null);
    view2131689640 = null;

    super.unbind();
  }
}
