package tech.jiangtao.support.ui.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.kevin.library.widget.CleanDialog;
import com.kevin.library.widget.builder.IconFlag;
import java.util.ArrayList;
import tech.jiangtao.support.kit.callback.QueryUserCallBack;
import tech.jiangtao.support.kit.eventbus.AddRosterEvent;
import tech.jiangtao.support.kit.eventbus.QueryUser;
import tech.jiangtao.support.kit.eventbus.QueryUserResult;
import tech.jiangtao.support.kit.userdata.SimpleUserQuery;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.AddFriendViewHolder;
import work.wanghao.simplehud.SimpleHUD;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: AddFriendFragment </br>
 * Description: 封装的添加好友界面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/12/2016 9:58 PM</br>
 * Update: 10/12/2016 9:58 PM </br>
 **/
public class AddFriendFragment extends BaseFragment
    implements EasyViewHolder.OnItemClickListener, QueryUserCallBack {

  @BindView(R2.id.search_view) EditText mSearchView;
  @BindView(R2.id.search_submit) TextView mSearchSubmit;
  @BindView(R2.id.friend_list) RecyclerView mFriendContaner;
  private BaseEasyAdapter mBaseEasyAdapter;
  private ArrayList<QueryUserResult> mList = new ArrayList<>();
  private SimpleUserQuery mQuery;

  public static AddFriendFragment newInstance() {
    return new AddFriendFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    setAdapter();
    return getView();
  }

  public void setAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
    mBaseEasyAdapter.setOnClickListener(this);
    mBaseEasyAdapter.bind(QueryUserResult.class, AddFriendViewHolder.class);
    mFriendContaner.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mFriendContaner.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mBaseEasyAdapter.addAll(mList);
    mFriendContaner.setAdapter(mBaseEasyAdapter);
  }

  @Override public int layout() {
    return R.layout.fragment_add_friend;
  }

  @OnClick(R2.id.search_submit) public void onClick(View v) {
    if (mSearchView.getText().toString() != "") {
      mQuery = new SimpleUserQuery();
      mQuery.startQuery(new QueryUser(mSearchView.getText().toString()), this);
      SimpleHUD.showLoadingMessage(getContext(),"正在查询",false);
    }
  }

  @Override public void onItemClick(int position, View view) {
    final CleanDialog dialog = new CleanDialog.Builder(getContext()).iconFlag(IconFlag.OK)
        .negativeButton("取消", Dialog::dismiss)
        .positiveButton("确认", dialog1 -> {
          HermesEventBus.getDefault()
              .post(new AddRosterEvent(mList.get(position).getJid(),
                  mList.get(position).getNickName()));
          dialog1.dismiss();
        })
        .title("确认添加" + mList.get(position).getNickName() + "为好友吗?")
        .negativeTextColor(Color.WHITE)
        .positiveTextColor(Color.WHITE)
        .builder();
    dialog.showDialog();
  }

  @Override public void querySuccess(QueryUserResult result) {
    SimpleHUD.dismiss();
    SimpleHUD.showSuccessMessage(getContext(),"查询成功");
    mBaseEasyAdapter.clear();
    mList.clear();
    mList.add(result);
    mBaseEasyAdapter.add(result);
    mBaseEasyAdapter.notifyDataSetChanged();
    mQuery.destroy();
  }

  @Override public void queryFail(String errorReason) {
    SimpleHUD.dismiss();
    SimpleHUD.showErrorMessage(getContext(), errorReason);
    mQuery.destroy();
  }
}
