package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import com.china.epower.chat.ui.adapter.PersonalBaseViewHolder;
import com.china.epower.chat.ui.pattern.ConstructListData;

/**
 * Class: PersonalTextViewHolder </br>
 * Description: 可以修改的文本 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 4:35 PM</br>
 * Update: 30/11/2016 4:35 PM </br>
 **/

public class PersonalTextViewHolder extends PersonalBaseViewHolder {

  @BindView(R.id.list_item_title) TextView mListItemTitle;
  @BindView(R.id.list_item_subtitle) TextView mListItemSubtitle;

  public PersonalTextViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_text);
    ButterKnife.bind(this,itemView);
  }

  @Override public void bindTo(int position, ConstructListData listData) {
    mListItemTitle.setText(listData.getmTitle());
    mListItemSubtitle.setText(listData.getmSubtitle());
  }
}
