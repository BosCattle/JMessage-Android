package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.*;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: GroupDetailValueViewHolder </br>
 * Description: 显示值 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 下午12:29</br>
 * Update: 2017/4/7 下午12:29 </br>
 **/

public class GroupDetailValueViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.group_item_title) TextView mGroupItemTitle;
  @BindView(R2.id.group_item_subtitle) TextView mGroupItemSubtitle;
  private Context mContext;

  public GroupDetailValueViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_value);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstrutContact l) {
    mGroupItemTitle.setText(l.mTitle);
    mGroupItemSubtitle.setText(l.mSubtitle);
  }
}
