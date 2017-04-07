package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.StringUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.UserServiceApi;
import tech.jiangtao.support.ui.model.group.Groups;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: GroupCreateActivity </br>
 * Description: 查找群组 </br>
 * Creator: Vurtex </br>
 * Email: hongkeshu@gmail.com </br>
 * Date: 2017/3/28 下午3:41</br>
 * Update: 2017/3/28 下午3:41 </br>
 **/
public class GroupSearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @BindView(R2.id.tv_toolbar)
    TextView mTvToolbar;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    public static final String TAG = GroupSearchActivity.class.getSimpleName();
    @BindView(R2.id.et_groupName)
    EditText etGroupName;
    @BindView(R2.id.btn_searchGroup)
    Button btnSearchGroup;
    @BindView(R2.id.rv_groupList)
    RecyclerView groupList;
    private ContactAdapter mContactAdapter;
    private List<ConstrutContact> mConstrutContact;
    private List<Groups> mGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        setUpToolbar();
        setUpAdapter();
        btnSearchGroup.setOnClickListener(v -> {
            String roomName = etGroupName.getText()+"";
            if(!StringUtils.isEmpty(roomName))
            ApiService.getInstance().createApiService(UserServiceApi.class).getQueryGroup(roomName).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
                //TODO 这里解析JSON
                if (list!=null){
                    mGroups = list;
                }
                for (int i = 0; i < list.size(); i++) {
                    ConstrutContact build = new ConstrutContact.Builder().build();
                    build.mType = ContactType.TYPE_GROUP;
                    build.mId = R.mipmap.ic_chat_default;
                    build.mTitle = "TestGroup" + i;
                    build.mVCardRealm = new VCardRealm("jid" + i);
                    mConstrutContact.add(build);
                }
                mContactAdapter.notifyDataSetChanged();
            }, new ErrorAction() {
                @Override public void call(Throwable throwable) {
                    super.call(throwable);
                    SimpleHUD.showErrorMessage(GroupSearchActivity.this,throwable.getLocalizedMessage());
                }
            });
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
        mContactAdapter.setOnClickListener(new EasyViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Groups groups = mGroups.get(position);
                //TODO 申请入群
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

    @Override
    protected boolean preSetupToolbar() {
        return false;
    }

    public static void startGroupSearch(Context context) {
        Intent intent = new Intent(context, GroupSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_group_create, menu);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //TODO 更改后就开始执行查询动作
        return false;
    }
}
