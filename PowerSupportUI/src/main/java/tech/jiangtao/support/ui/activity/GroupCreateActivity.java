package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import tech.jiangtao.support.kit.callback.GroupCreateCallBack;
import tech.jiangtao.support.kit.eventbus.muc.model.GroupCreateParam;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.userdata.SimpleCGroup;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.PinYinUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.fragment.ContactFragment;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
/**
 * Class: GroupCreateActivity </br>
 * Description: 创建群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/3/28 下午3:41</br>
 * Update: 2017/3/28 下午3:41 </br>
 **/
public class GroupCreateActivity extends BaseActivity
    implements SearchView.OnQueryTextListener, EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.group_add) RecyclerView mGroupAdd;
  @BindView(R2.id.group_edit) SearchView mSearchView;
  public static final String TAG = GroupCreateActivity.class.getSimpleName();
  private ContactAdapter mContactAdapter;
  private List<ConstrutContact> mConstrutContact;
  private SimpleCGroup mSimpleCGroup;
  private AppPreferences mAppPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_create);
    ButterKnife.bind(this);
    init();
  }

  public void init(){
    mAppPreferences = new AppPreferences(this);
    setUpToolbar();
    setUpEditText();
    setUpAdapter();
  }

  /**
   * Method: setUpAdapter </br>
   * Description:  设置Adapter</br>
   * Date: 15/01/2017 11:08 PM </br>
   **/
  private void setUpAdapter() {
    mConstrutContact = new ArrayList<>();
    mContactAdapter = new ContactAdapter(this, mConstrutContact);
    mContactAdapter.setOnClickListener(this);
    mGroupAdd.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mGroupAdd.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mGroupAdd.setAdapter(mContactAdapter);
  }

  /**
   * Method: setUpEditText </br>
   * Description:  设置SearchView</br>
   * Date: 15/01/2017 11:08 PM </br>
   **/
  private void setUpEditText() {
    mSearchView.setIconifiedByDefault(false);
    //为该SearchView组件设置事件监听器
    mSearchView.setOnQueryTextListener(this);
    //设置该SearchView显示搜索按钮
    mSearchView.setSubmitButtonEnabled(false);
    mSearchView.setIconified(false);
    //设置该SearchView内默认显示的提示文本
    mSearchView.setQueryHint("查找");
    if (mSearchView != null) {
      try {        // 拿到字节码
        Class<?> argClass = mSearchView.getClass();
        // 指定某个私有属性,mSearchPlate是搜索框父布局的名字
        Field ownField = argClass.getDeclaredField("mSearchPlate");
        // 暴力反射,只有暴力反射才能拿到私有属性
        ownField.setAccessible(true);
        View mView = (View) ownField.get(mSearchView);
        // 设置背景
        mView.setBackgroundColor(Color.TRANSPARENT);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("创建群");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupCreate(Context context) {
    Intent intent = new Intent(context, GroupCreateActivity.class);
    context.startActivity(intent);
  }

  @Override public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override public boolean onQueryTextChange(String newText) {
    if (newText.equals("")){
      mConstrutContact.clear();
    }
    return false;
  }

  @Override public void onItemClick(int position, View view) {

  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_group_create, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.menu_group_create){
      //检查是否有选择，创建群
      String userJid = null;
      String username = null;
      try {
        userJid = mAppPreferences.getString("userJid");
        username = mAppPreferences.getString("username");
        if (username==null){
          username = StringSplitUtil.splitPrefix(userJid);
        }
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
      }
      GroupCreateParam mGroupCreateParam = new GroupCreateParam(username + "的群", userJid, null);
      mSimpleCGroup.startCreateGroup(mGroupCreateParam, new GroupCreateCallBack() {
        @Override public void createSuccess() {
          //创建群聊成功
          GroupChatActivity.startGroupChat(GroupCreateActivity.this);
        }

        @Override public void createFailed(String failedReason) {
          //创建群聊失败

        }
      });
    }
    return true;
  }
}
