package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.china.epower.chat.R;
import com.china.epower.chat.model.ChatExtraModel;
import com.china.epower.chat.ui.adapter.EasyViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.ui.adapter.PersonalBaseViewHolder;
import com.china.epower.chat.ui.pattern.ConstructListData;

/**
 * Created by jiang on 2016/11/15.
 * 设置背景颜色
 */

public class PersonShadowViewHolder extends PersonalBaseViewHolder {
  @BindView(R.id.item_shadow) LinearLayout mItemShadow;

  public PersonShadowViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_shadow);
    ButterKnife.bind(this, itemView);
  }

  @Override public void bindTo(int position, ConstructListData listData) {
  }
}
