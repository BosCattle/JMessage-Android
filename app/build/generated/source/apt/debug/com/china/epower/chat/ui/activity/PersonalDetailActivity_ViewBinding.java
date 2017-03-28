// Generated code from Butter Knife. Do not modify!
package com.china.epower.chat.ui.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.china.epower.chat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalDetailActivity_ViewBinding extends BaseActivity_ViewBinding {
  private PersonalDetailActivity target;

  @UiThread
  public PersonalDetailActivity_ViewBinding(PersonalDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PersonalDetailActivity_ViewBinding(PersonalDetailActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTvToolbar = Utils.findRequiredViewAsType(source, R.id.tv_toolbar, "field 'mTvToolbar'", TextView.class);
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mRecyclerview = Utils.findRequiredViewAsType(source, R.id.recyclerview, "field 'mRecyclerview'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    PersonalDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvToolbar = null;
    target.mToolbar = null;
    target.mRecyclerview = null;

    super.unbind();
  }
}
