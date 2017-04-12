package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.afollestad.materialdialogs.MaterialDialog;
import com.china.epower.chat.R;
import com.china.epower.chat.model.type.ListDataType;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import com.china.epower.chat.ui.adapter.PersonalDataAdapter;
import com.china.epower.chat.ui.pattern.ConstructListData;
import com.china.epower.chat.utils.RecyclerViewUtils;
import com.china.epower.chat.utils.ResourceAddress;
import com.google.gson.Gson;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.AccountServiceApi;
import tech.jiangtao.support.ui.api.service.UpLoadServiceApi;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.type.TransportType;
import work.wanghao.simplehud.SimpleHUD;

import static com.vincent.filepicker.Constant.REQUEST_CODE_PICK_IMAGE;
import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

/**
 * Class: PersonalDetailActivity </br>
 * Description: 个人信息界面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/12/2016 10:58 PM</br>
 * Update: 01/12/2016 10:58 PM </br>
 * 用rxJava封装一层VCard，用原生太麻烦。
 * vCard也许为空
 **/
public class PersonalDetailActivity extends BaseActivity
    implements EasyViewHolder.OnItemClickListener {

  public static final int TAG_IMAGE = 100;
  public static final int TAG_USERNAME = 200;
  public static final int TAG_SEX = 300;
  public static final int TAG_SUBJECT = 400;
  public static final int TAG_POSITION = 500;
  public static final int TAG_STYLE = 600;
  public static final int TAG_PHONE = 700;
  public static final int TAG_EMAIL = 800;
  private static final String TAG = PersonalDetailActivity.class.getSimpleName();
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.recyclerview) RecyclerView mRecyclerview;
  private ArrayList<ConstructListData> mDatas;
  private PersonalDataAdapter mDataAdapter;
  private UpLoadServiceApi mUpLoadServiceApi;
  private AccountServiceApi mAccountServiceApi;
  private User mUser;
  private AppPreferences mAppPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal_detail);
    mAccountServiceApi = ApiService.getInstance().createApiService(AccountServiceApi.class);
    mUpLoadServiceApi = ApiService.getInstance().createApiService(UpLoadServiceApi.class);
    ButterKnife.bind(this);
    setUpToolbar();
    setAdapter();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public void setUpToolbar() {
    if (mToolbar != null) {
      mToolbar.setTitle("");
      mTvToolbar.setText("个人信息");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(
          v -> ActivityCompat.finishAfterTransition(PersonalDetailActivity.this));
    }
  }

  private void setAdapter() {
    mDatas = new ArrayList<>();
    mDataAdapter = new PersonalDataAdapter(this, buildData());
    mDataAdapter.setOnClickListener(this);
    mRecyclerview.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mRecyclerview.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mRecyclerview.setAdapter(mDataAdapter);
  }

  @Override public void onItemClick(int position, View view) {
    switch (mDatas.get(position).getmTag()) {
      case TAG_USERNAME:
        showDialog();
        break;
      case TAG_SEX:
        showSingleChoice();
        break;
      case TAG_IMAGE:
        showImageChoose();
        break;
      case TAG_SUBJECT:
        showSingleSubjectChoice();
        break;
      case TAG_POSITION:
        showSingleOfficeChoice();
        break;
      case TAG_EMAIL:
        showEmailDialog();
        break;
      case TAG_PHONE:
        showPhoneDialog();
        break;
      case TAG_STYLE:
        break;
    }
  }

  public ArrayList<ConstructListData> buildData() {
    mAppPreferences = new AppPreferences(this);
    try {
      Gson gson = new Gson();
      mUser = gson.fromJson(mAppPreferences.getString(SupportIM.USER), User.class);
      mAppPreferences.getString(SupportIM.USER);
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    mDatas.clear();
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_IMAGE)
        .tag(TAG_IMAGE)
        .image((mUser != null && mUser.avatar != null) ? ResourceAddress.url(mUser.avatar,
            TransportType.AVATAR) : null)
        .title("头像")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_USERNAME)
        .title("用户名")
        .subtitle(mUser != null && mUser.nickName != null ? mUser.nickName : "用户名")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_SEX)
        .title("性别")
        .subtitle(mUser.sex ? "女" : "男")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_SUBJECT)
        .title("部门")
        .subtitle("部门")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_POSITION)
        .title("职位")
        .subtitle("职位")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_EMAIL)
        .title("邮箱")
        .subtitle("邮箱")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_PHONE)
        .title("手机号")
        .subtitle("手机号")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_STYLE)
        .title("个性签名")
        .subtitle(mUser.signature != null ? mUser.signature : "个性签名...")
        .build());
    return mDatas;
  }

  public static void startPersonalDetail(Activity activity) {
    Intent intent = new Intent(activity, PersonalDetailActivity.class);
    activity.startActivity(intent);
  }

  public void showDialog() {
    new MaterialDialog.Builder(this).title(R.string.hint_dialog_input)
        .content(R.string.profile_max_length)
        .inputType(InputType.TYPE_CLASS_TEXT)
        .input(R.string.hint_dialog_input, R.string.hint_dialog_input, (dialog, input) -> {
          dialog.dismiss();
          if (input.length() >= 6) {
            SimpleHUD.showErrorMessage(this, (String) getText(R.string.profile_max_length));
          } else if (input.length() == 0) {
            SimpleHUD.showErrorMessage(this, (String) getText(R.string.profile_min_length));
          } else {
            mAccountServiceApi.updateAccount(mUser.uid, mUser.userId, input.toString(),
                mUser.avatar, mUser.sex, mUser.signature)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> SimpleHUD.showLoadingMessage(this, "正在更新", false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                  SimpleHUD.dismiss();
                  updateUsers(user);
                });
          }
        })
        .show();
  }

  private void updateUsers(User user) {
    Gson gson = new Gson();
    mAppPreferences.put(SupportIM.USER, gson.toJson(user));
    buildData();
    mDataAdapter.notifyDataSetChanged();
  }

  public void showEmailDialog() {
    new MaterialDialog.Builder(this).title(R.string.hint_dialog_email)
        .content(R.string.profile_email_pro)
        .inputType(InputType.TYPE_CLASS_TEXT)
        .input(R.string.hint_dialog_email, R.string.hint_dialog_email, (dialog, input) -> {
          dialog.dismiss();
          SimpleHUD.showInfoMessage(this, (String) getText(R.string.profile_email_pro));
        })
        .show();
  }

  public void showPhoneDialog() {
    new MaterialDialog.Builder(this).title(R.string.hint_dialog_phone)
        .content(R.string.profile_phone_pro)
        .inputType(InputType.TYPE_CLASS_TEXT)
        .input(R.string.hint_dialog_phone, R.string.hint_dialog_phone, (dialog, input) -> {
          dialog.dismiss();
          SimpleHUD.showInfoMessage(this, (String) getText(R.string.profile_email_pro));
        })
        .show();
  }

  public void showSingleChoice() {
    new MaterialDialog.Builder(this).title(R.string.profile_sex)
        .items(R.array.sex)
        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
          mAccountServiceApi.updateAccount(mUser.uid, mUser.userId, mUser.nickName, mUser.avatar,
              which > 0, mUser.signature)
              .subscribeOn(Schedulers.io())
              .doOnSubscribe(() -> SimpleHUD.showLoadingMessage(this, "正在更新", false))
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(user -> {
                SimpleHUD.dismiss();
                updateUsers(user);
              });
          return true;
        })
        .positiveText(R.string.profile_sure)
        .show();
  }

  public void showSingleSubjectChoice() {
    new MaterialDialog.Builder(this).title(R.string.profile_subject)
        .items(R.array.subject)
        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
          dialog.dismiss();
          SimpleHUD.showInfoMessage(this, (String) getText(R.string.profile_email_pro));
          return true;
        })
        .positiveText(R.string.profile_sure)
        .show();
  }

  public void showSingleOfficeChoice() {
    new MaterialDialog.Builder(this).title(R.string.profile_office)
        .items(R.array.office)
        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
          dialog.dismiss();
          SimpleHUD.showInfoMessage(this, (String) getText(R.string.profile_email_pro));
          return true;
        })
        .positiveText(R.string.profile_sure)
        .show();
  }

  public void showImageChoose() {
    Intent intent1 = new Intent(this, ImagePickActivity.class);
    intent1.putExtra(IS_NEED_CAMERA, true);
    intent1.putExtra(Constant.MAX_NUMBER, 1);
    startActivityForResult(intent1, REQUEST_CODE_PICK_IMAGE);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
      if (list.size() != 0) {
        Log.d(TAG, "onActivityResult: mVCard不为null");
        uploadFile(list.get(0).getPath(), "avatar");
        //发送通知
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  /**
   * @param path 文件路径
   * @param type 文件类型,必须为avatar
   */
  public void uploadFile(String path, String type) {
    if (mUpLoadServiceApi == null) {
      mUpLoadServiceApi = ApiService.getInstance().createApiService(UpLoadServiceApi.class);
    }
    File file = new File(path);
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"),type);
    mUpLoadServiceApi.upload(body, typeBody)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(() -> SimpleHUD.showLoadingMessage(this, "正在上传", false))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(filePath -> {
          mAccountServiceApi.updateAccount(mUser.uid, mUser.userId, mUser.nickName,
              filePath.resourceId, mUser.sex, mUser.signature)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(user -> {
                SimpleHUD.dismiss();
                updateUsers(user);
              }, new ErrorAction() {
                @Override public void call(Throwable throwable) {
                  super.call(throwable);
                  SimpleHUD.dismiss();
                  SimpleHUD.showErrorMessage(PersonalDetailActivity.this,throwable.getMessage());
                }
              });
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            SimpleHUD.dismiss();
            SimpleHUD.showErrorMessage(PersonalDetailActivity.this,throwable.getMessage());
          }
        });
  }
}
