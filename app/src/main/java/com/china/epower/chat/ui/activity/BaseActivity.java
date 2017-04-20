package com.china.epower.chat.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;

public abstract class BaseActivity extends AppCompatActivity {

  @Nullable @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @Nullable @BindView(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
    setupToolbar();
  }

  @Override protected void onResume() {
    super.onResume();
    // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
    PgyFeedbackShakeManager.setShakingThreshold(1000);
    // 以对话框的形式弹出
    PgyFeedbackShakeManager.register(this);
  }

  protected void setupToolbar() {
    if (mToolbar != null && preSetupToolbar()) {
      mToolbar.setTitle("");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(
          v -> ActivityCompat.finishAfterTransition(BaseActivity.this));
    }
  }


  @Nullable public final Toolbar getToolbar() {
    if (mToolbar==null){
      throw  new NullPointerException("必须设置一个toolbar");
    }
    return mToolbar;
  }

  @Nullable public final TextView getTitleTextView() {
    return mTvToolbar;
  }

  /**
   * 复写此方法以自定义toolbar的行为操作
   * <b>返回为false将不会预处理Toolbar</b>
   */
  protected abstract boolean preSetupToolbar();

  @Override protected void onPause() {
    super.onPause();
    PgyFeedbackShakeManager.unregister();
  }
}
