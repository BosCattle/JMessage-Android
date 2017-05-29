package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.ui.viewholder.ChatImageMineViewHolder;
import tech.jiangtao.support.ui.viewholder.ChatImageOtherViewHolder;
import tech.jiangtao.support.ui.viewholder.PlayerMineViewHolder;
import tech.jiangtao.support.ui.viewholder.PlayerOtherViewHolder;
import tech.jiangtao.support.ui.viewholder.TextMessageMineViewHolder;
import tech.jiangtao.support.ui.viewholder.TextMessageOtherViewHolder;

/**
 * Class: ChatMessageAdapter </br>
 * Description: 消息适配器，这个类写的就多了 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 11:45 PM</br>
 * Update: 04/12/2016 11:45 PM </br>
 **/

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatBaseViewHolder> {
  private static final int TYPE_TEXT_MINE = 0x01;
  private static final int TYPE_TEXT_OTHER = 0x02;
  private static final int TYPE_IMG_MINE = 0x03;
  private static final int TYPE_IMG_OTHER = 0x04;
  private static final int TYPE_AUDIO_MINE = 0x05;
  private static final int TYPE_AUDIO_OTHER = 0x06;
  private Context mContext;
  private List<MessageRealm> messageRealms = new ArrayList<>();

  public ChatMessageAdapter(Context context, List<MessageRealm> messageRealms) {
    this.messageRealms = messageRealms;
    mContext = context;
  }

  @Override public ChatBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ChatBaseViewHolder viewHolder = null;
    switch (viewType) {
      case TYPE_TEXT_MINE:
        viewHolder = new TextMessageMineViewHolder(mContext, parent);
        break;
      case TYPE_TEXT_OTHER:
        viewHolder = new TextMessageOtherViewHolder(mContext, parent);
        break;
      case TYPE_IMG_MINE:
        viewHolder = new ChatImageMineViewHolder(mContext, parent);
        break;
      case TYPE_IMG_OTHER:
        viewHolder = new ChatImageOtherViewHolder(mContext, parent);
        break;
      case TYPE_AUDIO_MINE:
        viewHolder = new PlayerMineViewHolder(mContext, parent);
        break;
      case TYPE_AUDIO_OTHER:
        viewHolder = new PlayerOtherViewHolder(mContext, parent);
        break;
    }
    return viewHolder;
  }

  @Override public void onBindViewHolder(ChatBaseViewHolder holder, int position) {
    holder.bindTo(position, messageRealms.get(position));
  }

  @Override public int getItemCount() {
    return messageRealms.size();
  }

  @Override public int getItemViewType(int position) {
    if (messageRealms.get(position).getAuthor().equals(MessageAuthor.OWN.toString())) {
      if (messageRealms.get(position).getMessageType().equals(DataExtensionType.TEXT.toString())) {
        return TYPE_TEXT_MINE;
      } else if (messageRealms.get(position)
          .getMessageType()
          .equals(DataExtensionType.IMAGE.toString())) {
        return TYPE_IMG_MINE;
      } else if (messageRealms.get(position)
          .getMessageType()
          .equals(DataExtensionType.AUDIO.toString())) {
        return TYPE_AUDIO_MINE;
      }
    } else if (messageRealms.get(position).getAuthor().equals(MessageAuthor.FRIEND.toString())) {
      if (messageRealms.get(position).getMessageType().equals(DataExtensionType.TEXT.toString())) {
        return TYPE_TEXT_OTHER;
      } else if (messageRealms.get(position)
          .getMessageType()
          .equals(DataExtensionType.IMAGE.toString())) {
        return TYPE_IMG_OTHER;
      } else if (messageRealms.get(position)
          .getMessageType()
          .equals(DataExtensionType.AUDIO.toString())) {
        return TYPE_AUDIO_OTHER;
      }
    }
    return super.getItemViewType(position);
  }

  public boolean clear() {
    messageRealms.clear();
    this.notifyDataSetChanged();
    return messageRealms.size() == 0;
  }

  public boolean addAll(List<MessageRealm> messages) {
    messageRealms.clear();
    messageRealms.addAll(messages);
    this.notifyDataSetChanged();
    return true;
  }
}
