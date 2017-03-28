// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import com.roughike.bottombar.BottomBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding extends BaseActivity_ViewBinding {
  private MainActivity target;

  private View view2131689547;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mContentContainer = Utils.findRequiredViewAsType(source, R.id.contentContainer, "field 'mContentContainer'", FrameLayout.class);
    target.mBottomBar = Utils.findRequiredViewAsType(source, R.id.bottomBar, "field 'mBottomBar'", BottomBar.class);
    view = Utils.findRequiredView(source, R.id.image, "field 'mImage' and method 'onClick'");
    target.mImage = Utils.castView(view, R.id.image, "field 'mImage'", ImageView.class);
    view2131689547 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mContentContainer = null;
    target.mBottomBar = null;
    target.mImage = null;

    view2131689547.setOnClickListener(null);
    view2131689547 = null;

    super.unbind();
  }
}
