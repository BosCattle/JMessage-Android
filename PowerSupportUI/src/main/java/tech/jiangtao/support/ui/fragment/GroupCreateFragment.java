package tech.jiangtao.support.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Objects;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.GroupServiceApi;
import tech.jiangtao.support.ui.api.service.UpLoadServiceApi;
import tech.jiangtao.support.ui.model.group.Group;
import tech.jiangtao.support.ui.model.type.TransportType;
import tech.jiangtao.support.ui.utils.ResourceAddress;
import work.wanghao.simplehud.SimpleHUD;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

/**
 * Class: GroupCreateFragment </br>
 * Description: 创建群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/04/2017 11:45 AM</br>
 * Update: 18/04/2017 11:45 AM </br>
 **/
public class GroupCreateFragment extends BaseFragment {

  @BindView(R2.id.group_create_circle) CircleImageView mGroupCreateCircle;
  @BindView(R2.id.group_create_name) AppCompatEditText mGroupCreateName;
  @BindView(R2.id.group_create_detail) AppCompatEditText mGroupCreateDetail;
  @BindView(R2.id.group_button) AppCompatButton mGroupButton;
  private GroupServiceApi mGroupServiceApi;
  private UpLoadServiceApi mUpLoadServiceApi;
  private String mResourceId;

  public static GroupCreateFragment newInstance() {
    return new GroupCreateFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, getView());
    mGroupServiceApi = ApiService.getInstance().createApiService(GroupServiceApi.class);
    mUpLoadServiceApi = ApiService.getInstance().createApiService(UpLoadServiceApi.class);
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_group_create;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @OnClick({ R2.id.group_create_circle, R2.id.group_button }) public void onClick(View v) {
    if (v.getId() == R.id.group_create_circle) {
      // 打开相册，拿图
      Intent intent1 = new Intent(getContext(), ImagePickActivity.class);
      intent1.putExtra(IS_NEED_CAMERA, true);
      intent1.putExtra(Constant.MAX_NUMBER, 1);
      startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
    } else if (v.getId() == R.id.group_button) {
      if (mGroupCreateName.getText() == null || Objects.equals(
          mGroupCreateName.getText().toString().trim(), "")) {
        SimpleHUD.showErrorMessage(getContext(), "群组名不能为空...");
        return;
      }
      if (mGroupCreateDetail.getText() == null || mGroupCreateDetail.getText()
          .toString()
          .trim()
          .equals("")) {
        SimpleHUD.showErrorMessage(getContext(), "群介绍不能为空...");
        return;
      }
      if (mResourceId == null || mResourceId.trim().equals("")) {
        SimpleHUD.showErrorMessage(getContext(), "请上传群头像");
        return;
      }
      String name = mGroupCreateName.getText().toString();
      String des = mGroupCreateDetail.getText().toString();
      String groupId = name + "@" + SupportIM.MUC_GROUP + "." + SupportIM.mDomain;
      AppPreferences appPreference = new AppPreferences(getContext());
      try {
        String userId = appPreference.getString(SupportIM.USER_ID);
        mGroupServiceApi.createGroup(groupId, userId, name, mResourceId, des)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(() -> SimpleHUD.showLoadingMessage(getContext(), "正在创建群..", false))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(groupRealm -> {
              writeGroupToRealm(groupRealm);
              SimpleHUD.dismiss();
            });
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private void writeGroupToRealm(GroupRealm group) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(group), () -> {
      Class clazz = getActivity().getClass();
      Annotation[] annotation = clazz.getAnnotations();
      for (int i = 0; i < annotation.length; i++) {
        if (annotation[i] instanceof GroupChatRouter) {
          GroupChatRouter groupChat = (GroupChatRouter) annotation[i];
          Intent intent = new Intent(getActivity(), groupChat.router());
          intent.putExtra(SupportIM.GROUP, group);
          startActivity(intent);
        }
      }
    }, error -> LogUtils.e(TAG, "保存群组数据失败"));
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
        ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
        uploadFile(list.get(0).getPath(), "avatar");
      }
    }
  }

  public void uploadFile(String path, String type) {
    // use the FileUtils to get the actual file by uri
    File file = new File(path);
    // create RequestBody instance from file
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"), type);
    mUpLoadServiceApi.upload(body, typeBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(filePath -> {
          LogUtils.d(TAG, "uploadFile: " + filePath);
          mResourceId = filePath.resourceId;
          Glide.with(getContext())
              .load(Uri.parse(ResourceAddress.url(filePath.resourceId, TransportType.AVATAR)))
              .centerCrop()
              .into(mGroupCreateCircle);
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            SimpleHUD.showErrorMessage(getContext(), "上传失败" + throwable.toString());
          }
        });
  }
}
