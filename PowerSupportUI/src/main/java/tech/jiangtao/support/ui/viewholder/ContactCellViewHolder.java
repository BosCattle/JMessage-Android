package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: ContactCellViewHolder </br>
 * Description: 通讯录界面空的包含字母的viewholder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 1:28 AM</br>
 * Update: 06/01/2017 1:28 AM </br>
 **/

public class ContactCellViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.letter_cell) TextView mLetterCell;

  public ContactCellViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_letter_cell);
    ButterKnife.bind(this, itemView);
  }

  @Override public void bindTo(int position, ConstrutContact l) {
    mLetterCell.setText(l.mTitle.toUpperCase());
  }
}
