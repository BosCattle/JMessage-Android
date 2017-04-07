package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.*;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: GroupDetailRadioViewHolder </br>
 * Description: 开关 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 下午12:28</br>
 * Update: 2017/4/7 下午12:28 </br>
 **/

public class GroupDetailRadioViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.group_item_title) TextView mGroupItemTitle;
  @BindView(R2.id.group_check) CheckBox mGroupCheck;
  private Context mContext;

  public GroupDetailRadioViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_radio);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstrutContact l) {

  }
}
