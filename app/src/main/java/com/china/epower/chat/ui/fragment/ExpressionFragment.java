package com.china.epower.chat.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.china.epower.chat.R;
import com.china.epower.chat.model.ChatExtraModel;
import com.china.epower.chat.ui.adapter.BaseEasyAdapter;
import com.china.epower.chat.ui.adapter.BaseEasyViewHolderFactory;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import com.china.epower.chat.ui.viewholder.ExpressViewHolder;
import com.china.epower.chat.ui.viewholder.ExtraFuncViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 表情的fragment
 */
public class ExpressionFragment extends Fragment implements EasyViewHolder.OnItemClickListener {


    @BindView(R.id.list_express)
    RecyclerView mListExpress;
    private BaseEasyAdapter mBaseEasyAdapter;
    private ArrayList<ChatExtraModel> mChatExtraItems;

    public static ExpressionFragment newInstance() {
        return new ExpressionFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expression, container, false);
        ButterKnife.bind(this, view);
        setAdapter();
        return view;
    }

    public void setAdapter() {
        mBaseEasyAdapter = new BaseEasyAdapter(getContext());
        mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
        mBaseEasyAdapter.setOnClickListener(this);
        mBaseEasyAdapter.bind(ChatExtraModel.class, ExpressViewHolder.class);
        mListExpress.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mChatExtraItems = new ArrayList<>();
        mListExpress.setAdapter(mBaseEasyAdapter);
        for (int i = 0; i <26; i++) {
            ChatExtraModel model = new ChatExtraModel(R.mipmap.ex_childrien_a);
            mChatExtraItems.add(model);
        }
        mBaseEasyAdapter.addAll(mChatExtraItems);
        mBaseEasyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, View view) {

    }
}
