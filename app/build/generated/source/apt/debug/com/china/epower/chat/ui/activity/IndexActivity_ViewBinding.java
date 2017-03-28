// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.activity;

import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IndexActivity_ViewBinding extends BaseActivity_ViewBinding {
  private IndexActivity target;

  @UiThread
  public IndexActivity_ViewBinding(IndexActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IndexActivity_ViewBinding(IndexActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.view_pager, "field 'mViewPager'", ViewPager.class);
    target.mDotContainer = Utils.findRequiredViewAsType(source, R.id.dot_container, "field 'mDotContainer'", LinearLayout.class);
    target.mBtnSure = Utils.findRequiredViewAsType(source, R.id.btn_sure, "field 'mBtnSure'", AppCompatButton.class);
  }

  @Override
  public void unbind() {
    IndexActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.mDotContainer = null;
    target.mBtnSure = null;

    super.unbind();
  }
}
