package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.StringUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.model.group.Groups;
import tech.jiangtao.support.kit.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: GroupCreateActivity </br>
 * Description: 查找群组 </br>
 * Creator: Vurtex </br>
 * Email: hongkeshu@gmail.com </br>
 * Date: 2017/3/28 下午3:41</br>
 * Update: 2017/4/10 下午12:41 </br>
 **/
public class GroupSearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  public static final String TAG = GroupSearchActivity.class.getSimpleName();
  @BindView(R2.id.rv_groupList) RecyclerView groupList;
  @BindView(R2.id.sv_groupSearch) SearchView svGroupSearch;
  private ContactAdapter mContactAdapter;
  private List<ConstrutContact> mConstrutContact;
  private List<Groups> mGroups;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_search);
    ButterKnife.bind(this);
    init();
  }

  public void init() {
    setUpToolbar();
    setUpEditText();
    setUpAdapter();
  }

  private void setUpEditText() {
    svGroupSearch.setIconifiedByDefault(false);
    //为该SearchView组件设置事件监听器
    svGroupSearch.setOnQueryTextListener(this);
    //设置该SearchView显示搜索按钮
    svGroupSearch.setSubmitButtonEnabled(false);
    svGroupSearch.setIconified(false);
    //设置该SearchView内默认显示的提示文本
    svGroupSearch.setQueryHint("群昵称");
    if (svGroupSearch != null) {
      try {        //--拿到字节码
        Class<?> argClass = svGroupSearch.getClass();
        //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
        Field ownField = argClass.getDeclaredField("mSearchPlate");
        //--暴力反射,只有暴力反射才能拿到私有属性
        ownField.setAccessible(true);
        View mView = (View) ownField.get(svGroupSearch);
        //--设置背景
        mView.setBackgroundColor(Color.TRANSPARENT);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Method: setUpAdapter </br>
   * Description:  设置Adapter</br>
   * Date: 15/01/2017 11:08 PM </br>
   **/
  private void setUpAdapter() {
    mConstrutContact = new ArrayList<>();
    mContactAdapter = new ContactAdapter(this, mConstrutContact);
    mContactAdapter.setOnClickListener(new EasyViewHolder.OnItemClickListener() {
      @Override public void onItemClick(int position, View view) {
        new MaterialDialog.Builder(GroupSearchActivity.this).title("提醒：")
            .content("确定要加入群组吗？")
            .positiveText(R.string.group_yes)
            .negativeText(R.string.group_cancel)
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Groups groups = mGroups.get(position);
                //SimpleCGroup mSimpleCGroup = new SimpleCGroup();
                //mSimpleCGroup.startGroupRequest(groups.groupUid, groups.roomName);
              }
            })
            .show();
      }
    });
    groupList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    groupList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    groupList.setAdapter(mContactAdapter);
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("查找群");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupSearch(Context context) {
    Intent intent = new Intent(context, GroupSearchActivity.class);
    context.startActivity(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_group_create, menu);
    return true;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    String roomName = query;
    if (!StringUtils.isEmpty(roomName)) {
      ApiService.getInstance()
          .createApiService(UserServiceApi.class)
          .getQueryGroup(roomName)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(list -> {
            SimpleHUD.dismiss();
            if (list != null) {
              mGroups = list;
            }
            mConstrutContact.clear();
            for (Groups mGroups : list) {
              ConstrutContact build = new ConstrutContact.Builder().build();
              build.mType = ContactType.TYPE_GROUP_LIST;
              build.mObject = mGroups;
              mConstrutContact.add(build);
            }
            mContactAdapter.notifyDataSetChanged();
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              SimpleHUD.showErrorMessage(GroupSearchActivity.this, throwable.getLocalizedMessage());
            }
          });
      SimpleHUD.showLoadingMessage(GroupSearchActivity.this, "正在查询", false);
    }
    return false;
  }

  @Override public boolean onQueryTextChange(String newText) {
    //TODO 更改后就开始执行查询动作
    return false;
  }
}
