// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding extends BaseActivity_ViewBinding {
  private RegisterActivity target;

  private View view2131689656;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mRegisterUsername = Utils.findRequiredViewAsType(source, R.id.register_username, "field 'mRegisterUsername'", AppCompatEditText.class);
    target.mRegisterPassword = Utils.findRequiredViewAsType(source, R.id.register_password, "field 'mRegisterPassword'", AppCompatEditText.class);
    target.mRegisterRetryPassword = Utils.findRequiredViewAsType(source, R.id.register_retry_password, "field 'mRegisterRetryPassword'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.register_button, "field 'mRegisterButton' and method 'onClick'");
    target.mRegisterButton = Utils.castView(view, R.id.register_button, "field 'mRegisterButton'", AppCompatButton.class);
    view2131689656 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRegisterUsername = null;
    target.mRegisterPassword = null;
    target.mRegisterRetryPassword = null;
    target.mRegisterButton = null;

    view2131689656.setOnClickListener(null);
    view2131689656 = null;

    super.unbind();
  }
}
