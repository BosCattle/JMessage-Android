package com.china.epower.chat.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import com.cocosw.favor.FavorAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Subscriber;
import tech.jiangtao.support.kit.realm.sharepreference.FirstEnter;
import tech.jiangtao.support.kit.util.CommonUtils;

/**
 * Author: doublmine
 * Created Date: 2016/09/06
 * Summary: TODO
 */

@RuntimePermissions public class IndexActivity extends BaseActivity
    implements ViewPager.OnPageChangeListener {
  @BindView(R.id.view_pager) ViewPager mViewPager;
  @BindView(R.id.dot_container) LinearLayout mDotContainer;
  private final static int DOT_SIZE = 6;
  @BindView(R.id.btn_sure) AppCompatButton mBtnSure;

  private ViewAdapter mViewAdapter;

  private List<View> mViews;
  private int preDotPosition = 0;//点的上一个位置
  private int mButtonHeight;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_index);
    ButterKnife.bind(this);
    initialize();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  private void initialize() {
    initBtnPosition();
    mViews = new ArrayList<>();
    View view1 = new View(this);
    View view2 = new View(this);
    View view3 = new View(this);
    view1.setBackgroundResource(R.mipmap.ic_index1);
    view2.setBackgroundResource(R.mipmap.ic_index2);
    view3.setBackgroundResource(R.mipmap.ic_index3);
    mViews.add(view1);
    mViews.add(view2);
    mViews.add(view3);
    mViewAdapter = new ViewAdapter(mViews, this);
    mViewPager.setAdapter(mViewAdapter);
    mViewPager.addOnPageChangeListener(this);
    setupDotContainer(mViews);
  }

  private void setupDotContainer(List<View> list) {
    View dot = null;
    LinearLayout.LayoutParams params = null;
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        dot = new View(this);
        dot.setBackgroundResource(R.drawable.selector_dot_drawable);
        params = new LinearLayout.LayoutParams(CommonUtils.dpToPx(DOT_SIZE),
            CommonUtils.dpToPx(DOT_SIZE));
        params.leftMargin = 20;
        dot.setEnabled(false);
        dot.setLayoutParams(params);
        mDotContainer.addView(dot);
      }
      if (preDotPosition == 0) mDotContainer.getChildAt(0).setEnabled(true);
    }
  }

  private void initBtnPosition() {
    mBtnSure.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @SuppressLint("NewApi") @SuppressWarnings("deprecation") @Override
          public void onGlobalLayout() {
            mButtonHeight = mBtnSure.getHeight();
            mBtnSure.setTranslationY(CommonUtils.dpToPx(36) + mBtnSure.getHeight());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
              mBtnSure.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
              mBtnSure.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
          }
        });
    mBtnSure.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Observable.create(new Observable.OnSubscribe<Object>() {
          @Override public void call(Subscriber<? super Object> subscriber) {
            subscriber.onNext(new Object());
          }
        }).throttleFirst(1,TimeUnit.SECONDS).subscribe(o -> {
          //IndexActivityPermissionsDispatcher.needPermissionDialogWithCheck(this);
          FirstEnter firstEnter = new FavorAdapter.Builder(IndexActivity.this).build().create(FirstEnter.class);
          firstEnter.setEntered(true);
          LoginActivity.startLogin(IndexActivity.this);
        });
      }
    });
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    mDotContainer.getChildAt(preDotPosition).setEnabled(false);
    mDotContainer.getChildAt(position).setEnabled(true);
    preDotPosition = position;
    if (mViews != null && mViews.size() - 1 == position) {
      mBtnSure.animate()
          .translationY(0)
          .setDuration(400)
          .setInterpolator(new LinearInterpolator())
          .start();
    } else {
      if (mBtnSure.getTranslationY() != mButtonHeight + CommonUtils.dpToPx(36)) {
        mBtnSure.animate()
            .translationY(mButtonHeight + CommonUtils.dpToPx(36))
            .setDuration(400)
            .setInterpolator(new LinearInterpolator())
            .start();
      }
    }
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  @NeedsPermission({
      Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
      Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
  }) void needPermissionDialog() {
    FirstEnter firstEnter = new FavorAdapter.Builder(this).build().create(FirstEnter.class);
    firstEnter.setEntered(true);
    MainActivity.startMain(this);
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //IndexActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
  }

  @OnShowRationale({
      Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
      Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
  }) void showRationaleDialog(PermissionRequest request) {
    new AlertDialog.Builder(this).setIcon(getApplicationInfo().icon)
        .setTitle("权限请求")
        .setMessage("点将台需要一些必需的权限来保证正常工作,请在稍后的权限对话框中允许点将台的权限。")
        .setPositiveButton("确定", (dialog, button) -> request.proceed())
        .setCancelable(false)
        .setNegativeButton("取消", (dialog, button) -> request.cancel())
        .show();
  }

  @OnPermissionDenied({
      Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
      Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
  }) void permissionDeniedCallback() {
    //IndexActivityPermissionsDispatcher.needPermissionDialogWithCheck(this);

  }

  @OnNeverAskAgain({
      Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
      Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
  }) void neverAskAgainCallback() {
    new AlertDialog.Builder(this).setIcon(getApplicationInfo().icon)
        .setTitle("授权失败")
        .setMessage("缺失必要的权限,点将台将无法正常工作!请清除应用数据以重新授权!")
        .setCancelable(false)
        .setPositiveButton("确定", (dialog, button) -> {
          dialog.dismiss();
        })
        .show();
  }

  class ViewAdapter extends PagerAdapter {

    private List<View> mList;
    private Context mContext;

    public ViewAdapter(List<View> list, Context context) {
      mList = list;
      mContext = context;
    }

    @Override public int getCount() {
      if (mList != null) return mList.size();
      return 0;
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      if (mList != null) container.removeView(mList.get(position));
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      if (mList != null) {
        container.addView(mList.get(position));
        return mList.get(position);
      }
      return super.instantiateItem(container, position);
    }
  }

  public static void startIndex(Context context) {
    Intent intent = new Intent(context, IndexActivity.class);
    context.startActivity(intent);
    if (context instanceof Activity) {
      ((Activity) context).finish();
    }
  }
}


