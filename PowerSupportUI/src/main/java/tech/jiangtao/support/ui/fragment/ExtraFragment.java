package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.ChatExtraModel;
import tech.jiangtao.support.ui.viewholder.ExtraFuncViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * 添加额外功能的fragment
 */
public class ExtraFragment extends Fragment implements EasyViewHolder.OnItemClickListener {


    @BindView(R2.id.recycler_extra)
    RecyclerView mRecyclerExtra;
    private BaseEasyAdapter mBaseEasyAdapter;
    private ArrayList<ChatExtraModel> mChatExtraItems;

    public static ExtraFragment newInstance() {
        return new ExtraFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extra, container, false);
        ButterKnife.bind(this, view);
        setAdapter();
        return view;
    }

    public void setAdapter() {
        mBaseEasyAdapter = new BaseEasyAdapter(getContext());
        mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
        mBaseEasyAdapter.setOnClickListener(this);
        mBaseEasyAdapter.bind(ChatExtraModel.class, ExtraFuncViewHolder.class);
        mRecyclerExtra.setLayoutManager(new GridLayoutManager(getContext(),4));
        mChatExtraItems = new ArrayList<>();
        mRecyclerExtra.setAdapter(mBaseEasyAdapter);
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_photo,"图片"));
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_take_photograph,"照片"));
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_location,"位置"));
        mChatExtraItems.add(new ChatExtraModel(R.mipmap.ic_call,"打电话"));
        mBaseEasyAdapter.addAll(mChatExtraItems);
        mBaseEasyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, View view) {

    }
}
