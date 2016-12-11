package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.china.epower.chat.R;
import com.china.epower.chat.app.PowerApp;
import com.china.epower.chat.model.type.ListDataType;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import com.china.epower.chat.ui.adapter.PersonalDataAdapter;
import com.china.epower.chat.ui.pattern.ConstructListData;
import com.china.epower.chat.utils.ErrorAction;
import com.china.epower.chat.utils.RecyclerViewUtils;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.stringencoder.Base64;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleVCard;
import tech.jiangtao.support.kit.util.FileUtil;
import work.wanghao.simplehud.SimpleHUD;

import static com.china.epower.chat.ui.fragment.PersonalFragment.TAG_UPDATE;
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
 **/
public class PersonalDetailActivity extends BaseActivity
    implements EasyViewHolder.OnItemClickListener,VCardCallback {

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
  private VCard mVCard;
  private SimpleVCard vCard ;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal_detail);
    ButterKnife.bind(this);
    setUpToolbar();
    getVCard();
    setAdapter();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  private void getVCard() {
    vCard = new SimpleVCard(SupportService.getmXMPPConnection().getUser());
    vCard.setmVCardCallback(this);
    vCard.getVCard();
  }

  public void setUpToolbar() {
    if (mToolbar != null) {
      mToolbar.setTitle("");
      mTvToolbar.setText("个人信息");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          ActivityCompat.finishAfterTransition(PersonalDetailActivity.this);
        }
      });
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
    mDatas.clear();
    String avatar =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1478986459429&di=a8e5cd961cbfafab630ee5e0dbb48229&imgtype=0&src=http%3A%2F%2Fimage81.360doc.com%2FDownloadImg%2F2015%2F01%2F2419%2F49440174_1.jpg";
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_IMAGE)
        .tag(TAG_IMAGE)
        .image((mVCard!=null&&mVCard.getAvatar()!=null)?mVCard.getAvatar():null)
        .title("头像")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_USERNAME)
        .title("用户名")
        .subtitle(mVCard!=null&&mVCard.getNickName()!=null?mVCard.getNickName():"")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_SEX)
        .title("性别")
        .subtitle((mVCard!=null&&mVCard.getField("sex")!=null)?mVCard.getField("sex"):"")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_SUBJECT)
        .title("部门")
        .subtitle((mVCard!=null&&mVCard.getField("subject")!=null)?mVCard.getField("subject"):"")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_POSITION)
        .title("职位")
        .subtitle((mVCard!=null&&mVCard.getField("office")!=null)?mVCard.getField("office"):"")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_EMAIL)
        .title("邮箱")
        .subtitle((mVCard!=null&&mVCard.getEmailWork()!=null)?mVCard.getEmailWork():"")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_PHONE)
        .title("手机号")
        .subtitle((mVCard!=null&&mVCard.getPhoneWork("voice")!=null)?mVCard.getPhoneWork("voice"):"")
        .build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
    mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
        .tag(TAG_STYLE)
        .title("个性签名")
        .subtitle((mVCard!=null&&mVCard.getPrefix()!=null)?mVCard.getPrefix():"")
        .build());
    return mDatas;
  }

  public static void startPersonalDetail(Activity activity) {
    Intent intent = new Intent(activity, PersonalDetailActivity.class);
    activity.startActivity(intent);
    activity.finish();
  }

  public void showDialog() {
    new MaterialDialog.Builder(this).title(R.string.hint_dialog_input)
        .content(R.string.profile_max_length)
        .inputType(InputType.TYPE_CLASS_TEXT)
        .input(R.string.hint_dialog_input, R.string.hint_dialog_input, (dialog, input) -> {
          dialog.dismiss();
          if (input.length() >= 6) {
            SimpleHUD.showErrorMessage(this,
                (String) getText(R.string.profile_max_length));
          } else if (input.length() == 0) {
            SimpleHUD.showErrorMessage(this,
                (String) getText(R.string.profile_min_length));
          } else {
            if (mVCard!=null) {
              mVCard.setNickName(input.toString());
              vCard.setVCard(mVCard);
            }
          }
        })
        .show();
  }

  public void showEmailDialog() {
    new MaterialDialog.Builder(this).title(R.string.hint_dialog_email)
        .content(R.string.profile_email_pro)
        .inputType(InputType.TYPE_CLASS_TEXT)
        .input(R.string.hint_dialog_email, R.string.hint_dialog_email, (dialog, input) -> {
          dialog.dismiss();
          if (mVCard!=null&&input.toString().contains("@")) {
            mVCard.setEmailWork(input.toString());
            vCard.setVCard(mVCard);
          }else {
            SimpleHUD.showErrorMessage(this,
                (String) getText(R.string.profile_email_pro));
          }
        })
        .show();
  }

  public void showPhoneDialog() {
    new MaterialDialog.Builder(this).title(R.string.hint_dialog_phone)
        .content(R.string.profile_phone_pro)
        .inputType(InputType.TYPE_CLASS_TEXT)
        .input(R.string.hint_dialog_phone, R.string.hint_dialog_phone, (dialog, input) -> {
          dialog.dismiss();
          if (mVCard!=null&&input.length()==11) {
            mVCard.setPhoneWork("voice",input.toString());
            vCard.setVCard(mVCard);
          }else {
            SimpleHUD.showErrorMessage(this,
                (String) getText(R.string.profile_phone_pro));
          }
        })
        .show();
  }

  public void showSingleChoice() {
    new MaterialDialog.Builder(this).title(R.string.profile_sex)
        .items(R.array.sex)
        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
          String[] list = getResources().getStringArray(R.array.sex);
          if (mVCard!=null) {
            mVCard.setField("sex", list[which]);
            vCard.setVCard(mVCard);
          }
          return true;
        })
        .positiveText(R.string.profile_sure)
        .show();
  }

  public void showSingleSubjectChoice() {
    new MaterialDialog.Builder(this).title(R.string.profile_subject)
        .items(R.array.subject)
        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
          String[] list = getResources().getStringArray(R.array.subject);
          if (mVCard!=null) {
            mVCard.setField("subject", list[which]);
            vCard.setVCard(mVCard);
          }
          return true;
        })
        .positiveText(R.string.profile_sure)
        .show();
  }

  public void showSingleOfficeChoice() {
    new MaterialDialog.Builder(this).title(R.string.profile_office)
        .items(R.array.office)
        .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
          String[] list = getResources().getStringArray(R.array.office);
          if (mVCard!=null) {
            mVCard.setField("office", list[which]);
            vCard.setVCard(mVCard);
          }
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
      if (list.size()!=0){
        byte[] bytes = FileUtil.getFileByte(list.get(0).getPath());
        if (mVCard!=null){
          Log.d(TAG, "onActivityResult: mVCard不为null");
          mVCard.setAvatar(bytes);
          vCard.setVCard(mVCard);
        }
      }
    }
  }

  @Override public void recieveVCard(VCard vCard,String userJID) {
    // 拿到VCard,进行数据构建
    if (vCard==null){
      SimpleHUD.showErrorMessage(this,"vCard获取失败");
    }else {
      mVCard = vCard;
      buildData();
      mDataAdapter.notifyDataSetChanged();
    }
  }

  @Override public void settingVCard(String message) {
    Log.d(TAG, "settingVCard: "+message);
    mDataAdapter.clear();
    buildData();
    mDataAdapter.notifyDataSetChanged();
    SimpleHUD.showInfoMessage(this,message);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
