package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.userdata.SimpleCGroup;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.UserServiceApi;
import tech.jiangtao.support.ui.model.group.Friends;
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
        implements EasyViewHolder.OnItemClickListener {

    @BindView(R2.id.tv_toolbar)
    TextView mTvToolbar;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.rv_constansList)
    RecyclerView mConstantsList;
    public static final String TAG = GroupCreateActivity.class.getSimpleName();
    private ContactAdapter mContactAdapter;
    private List<ConstrutContact> mConstrutContact;
    private SimpleCGroup mSimpleCGroup;
    private AppPreferences mAppPreferences;
    private UserServiceApi mUserServiceApi;
    public static List<Friends> mChoicedFriends=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);
        ButterKnife.bind(this);
        init();

    }

    public void init() {
        mAppPreferences = new AppPreferences(this);
        String name =null;
        try {
            name = mAppPreferences.getString("userJid");
        }catch (ItemNotFoundException e){
            e.printStackTrace();
        }
        name="vurtex@dc-a4b8eb92-xmpp.jiangtao.tech.";
        LogUtils.d(TAG,name);
        mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
        setUpToolbar();
        setUpAdapter();
        mUserServiceApi.post(name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            //TODO 这里解析JSON
            for (Friends friends : list) {
                ConstrutContact build = new ConstrutContact.Builder().build();
                build.mType = ContactType.TYPE_CHOICE_MEMBER_CHOICE;
                build.mFriends=friends;
                LogUtils.d(TAG, friends.toString());
                mConstrutContact.add(build);
            }
            mContactAdapter.notifyDataSetChanged();
        }, new ErrorAction() {
            @Override
            public void call(Throwable throwable) {
                super.call(throwable);
                LogUtils.d(TAG,throwable.getLocalizedMessage());
            }
        });

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
        mConstantsList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
        mConstantsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mConstantsList.setAdapter(mContactAdapter);
    }

    public void setUpToolbar() {
        mToolbar.setTitle("");
        mTvToolbar.setText("创建群");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> this.finish());
    }

    @Override
    protected boolean preSetupToolbar() {
        return false;
    }

    public static void startGroupCreate(Context context) {
        Intent intent = new Intent(context, GroupCreateActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_group_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_group_create) {
            EditText et = new EditText(GroupCreateActivity.this);
            et.setHint("请输入群名");
            et.setWidth(RecyclerView.LayoutParams.WRAP_CONTENT);
            new AlertDialog.Builder(GroupCreateActivity.this).setTitle("请输入群名").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 收集选择的成员
                    int size = mChoicedFriends.size();
                    LogUtils.d(TAG, size+"");
                    //执行创建群动作
                }
            }).setNegativeButton("取消",null).create().show();
        }
        return true;
    }
}
