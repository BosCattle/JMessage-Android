package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.kit.model.type.TransportType;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.utils.ResourceAddress;

/**
 * Class: GroupAdditionViewHolder </br>
 * Description: 添加群成员 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 20/04/2017 6:22 PM</br>
 * Update: 20/04/2017 6:22 PM </br>
 **/
public class GroupAdditionViewHolder extends EasyViewHolder<ContactRealm> {
  @BindView(R2.id.group_contact_avatar) CircleImageView mGroupContactAvatar;
  @BindView(R2.id.group_contact_nickname) TextView mGroupContactNickname;
  @BindView(R2.id.group_addition_choice) AppCompatCheckBox mGroupAdditionChoice;
  private Context mContext;

  public GroupAdditionViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_addition);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ContactRealm contactRealm) {
    Glide.with(mContext)
        .load(ResourceAddress.url(contactRealm.getAvatar(), TransportType.AVATAR))
        .placeholder(R.mipmap.ic_chat_default)
        .centerCrop()
        .into(mGroupContactAvatar);
    mGroupContactNickname.setText(contactRealm.getNickName());
    mGroupAdditionChoice.setChecked(true);
  }
}
