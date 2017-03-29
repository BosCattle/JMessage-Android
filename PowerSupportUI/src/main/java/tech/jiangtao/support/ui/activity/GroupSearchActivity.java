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
import android.widget.Toast;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.kit.userdata.SimpleCGroup;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;

/**
 * Class: GroupCreateActivity </br>
 * Description: 查找群组 </br>
 * Creator: Vurtex </br>
 * Email: hongkeshu@gmail.com </br>
 * Date: 2017/3/28 下午3:41</br>
 * Update: 2017/3/28 下午3:41 </br>
 **/
public class GroupSearchActivity extends BaseActivity
        implements  SearchView.OnQueryTextListener {

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
    private SimpleCGroup mSimpleCGroup;
    private AppPreferences mAppPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        mAppPreferences = new AppPreferences(this);
        setUpToolbar();
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
        mContactAdapter.setOnClickListener(new EasyViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(GroupSearchActivity.this,"你点击了"+position,Toast.LENGTH_SHORT).show();
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

    public static void startGroupCreate(Context context) {
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
