package com.china.epower.chat.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.china.epower.chat.R;
import com.china.epower.chat.model.type.ListDataType;
import com.china.epower.chat.ui.activity.LoginActivity;
import com.china.epower.chat.ui.activity.PersonalDetailActivity;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import com.china.epower.chat.ui.adapter.PersonalDataAdapter;
import com.china.epower.chat.ui.pattern.ConstructListData;
import com.china.epower.chat.utils.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.userdata.SimpleVCard;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: PersonalFragment </br>
 * Description: 个人页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 3:07 PM</br>
 * Update: 10/11/2016 3:07 PM </br>
 **/
public class PersonalFragment extends Fragment implements EasyViewHolder.OnItemClickListener, VCardCallback {

    public static final int TAG_HEAD = 200;
    public static final int TAG_NOTIFICATION = 300;
    public static final int TAG_CACHE = 400;
    public static final int TAG_UPDATE_PASSWORD = 500;
    public static final int TAG_UPDATE = 600;
    public static final int TAG_ABOUT = 700;

    @BindView(R.id.personal_list)
    RecyclerView mPersonalList;
    @BindView(R.id.login_button)
    AppCompatButton mLoginButton;
    private PersonalDataAdapter mDataAdapter;
    private List<ConstructListData> mDatas;
    private SimpleVCard mVCard;
    private VCard mFactVCard;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        setAdapter();
        getVCard();
        return view;
    }

    private void getVCard() {
        mVCard = new SimpleVCard(SupportService.getmXMPPConnection().getUser());
        mVCard.setmVCardCallback(this);
        mVCard.getVCard();
    }

    private void setAdapter() {
        mDatas = new ArrayList<>();
        mDataAdapter = new PersonalDataAdapter(getContext(), buildData());
        mDataAdapter.setOnClickListener(this);
        mPersonalList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
        mPersonalList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPersonalList.setAdapter(mDataAdapter);
    }

    public List<ConstructListData> buildData() {
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_HEAD)
                .tag(TAG_HEAD)
                .image(mFactVCard != null && mFactVCard.getAvatar() != null ? mFactVCard.getAvatar() : null)
                .username(mFactVCard != null && mFactVCard.getNickName() != null ? mFactVCard.getNickName() : "用户名")
                .nickname(mFactVCard != null && mFactVCard.getField("subject") != null ? mFactVCard.getField("subject") : "部门")
                .arrowIcon(R.mipmap.ic_arrow)
                .build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_SHADOW).build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
                .tag(TAG_NOTIFICATION)
                .title("通知")
                .arrowIcon(R.mipmap.ic_arrow)
                .build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
                .tag(TAG_CACHE)
                .title("清除缓存")
                .arrowIcon(R.mipmap.ic_arrow)
                .build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
                .tag(TAG_UPDATE_PASSWORD)
                .title("修改密码")
                .arrowIcon(R.mipmap.ic_arrow)
                .build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_TEXT)
                .tag(TAG_UPDATE)
                .title("版本更新")
                .subtitle("当前是最新版本")
                .build());
        mDatas.add(new ConstructListData.Builder().type(ListDataType.TAG_NORMAL)
                .tag(TAG_ABOUT)
                .title("关于我们")
                .arrowIcon(R.mipmap.ic_arrow)
                .build());
        return mDatas;
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (mDatas.get(position).getmTag()) {
            case TAG_HEAD:
                PersonalDetailActivity.startPersonalDetail(getActivity());
                break;
        }
    }

    @OnClick(R.id.login_button)
    public void onClick(View v) {
        XMPPTCPConnection connection = (XMPPTCPConnection) SupportService.getmXMPPConnection();
        connection.disconnect();
        LoginActivity.startLogin(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void recieveVCard(VCard vCard, String userJid) {
        if (vCard != null) {
            mFactVCard = vCard;
            mDataAdapter.clear();
            buildData();
            mDataAdapter.notifyDataSetChanged();
        } else {
            SimpleHUD.showErrorMessage(getContext(), "获取VCard失败。");
        }
    }

    @Override
    public void settingVCard(String message) {
        SimpleHUD.showInfoMessage(getContext(), message);
    }
}
