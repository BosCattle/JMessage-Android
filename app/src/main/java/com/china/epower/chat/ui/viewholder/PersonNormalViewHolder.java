package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import com.china.epower.chat.ui.adapter.PersonalBaseViewHolder;
import com.china.epower.chat.ui.pattern.ConstructListData;

/**
 * Class: PersonNormalViewHolder </br>
 * Description: 带右箭头的标准viewholder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 4:40 PM</br>
 * Update: 30/11/2016 4:40 PM </br>
 **/

public class PersonNormalViewHolder extends PersonalBaseViewHolder {
  @BindView(R.id.list_item_title) TextView mListItemTitle;
  @BindView(R.id.list_item_arrow) ImageView mListItemArrow;

  public PersonNormalViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_normal);
    ButterKnife.bind(this,itemView);
  }

  @Override public void bindTo(int position, ConstructListData listData) {
    mListItemTitle.setText(listData.getmTitle());
    mListItemArrow.setImageResource(listData.getmArrowIcon());
  }
}
