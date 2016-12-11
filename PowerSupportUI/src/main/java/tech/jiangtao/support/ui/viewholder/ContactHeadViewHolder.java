package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: ContactHeadViewHolder </br>
 * Description: 标题栏的ViewHolder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 9:52 PM</br>
 * Update: 04/12/2016 9:52 PM </br>
 **/

public class ContactHeadViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {
  @BindView(R2.id.item_chat_head) CircleImageView mItemChatHead;
  @BindView(R2.id.item_chat_title) TextView mItemChatTitle;
  @BindView(R2.id.item_online_status) TextView mItemOnlineStatus;
  private Context mContext;

  public ContactHeadViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_contact_head);
    ButterKnife.bind(this,itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstrutContact l) {
    Glide.with(mContext)
        .load(l.mId)
        .centerCrop()
        .into(mItemChatHead);
    mItemChatTitle.setText(l.mTitle);
  }
}
