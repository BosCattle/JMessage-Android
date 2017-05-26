package tech.jiangtao.support.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;

public abstract class BaseActivity extends AppCompatActivity {

  @Nullable @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @Nullable @BindView(R2.id.toolbar) Toolbar mToolbar;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
    setupToolbar();
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


  @NonNull public final Toolbar getToolbar() {
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
  }

}
