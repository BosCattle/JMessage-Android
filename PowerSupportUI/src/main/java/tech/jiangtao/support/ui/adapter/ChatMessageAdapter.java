package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.annotation.BoolRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import tech.jiangtao.support.ui.model.type.MessageType;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
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
    private List<ConstructMessage> mConstructMessage = new ArrayList<>();

    public ChatMessageAdapter(Context context, List<ConstructMessage> constructMessages) {
        mConstructMessage = constructMessages;
        mContext = context;
    }

    @Override
    public ChatBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChatBaseViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_TEXT_MINE:
                viewHolder = new TextMessageMineViewHolder(mContext,parent);
                break;
            case TYPE_TEXT_OTHER:
                viewHolder = new TextMessageOtherViewHolder(mContext,parent);
                break;
            case TYPE_IMG_MINE:
                viewHolder = new ChatImageMineViewHolder(mContext,parent);
                break;
            case TYPE_IMG_OTHER:
                viewHolder = new ChatImageOtherViewHolder(mContext,parent);
                break;
            case TYPE_AUDIO_MINE:
                viewHolder = new PlayerMineViewHolder(mContext,parent);
                break;
            case TYPE_AUDIO_OTHER:
                viewHolder = new PlayerOtherViewHolder(mContext,parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatBaseViewHolder holder, int position) {
        holder.bindTo(position,mConstructMessage.get(position));
    }

    @Override
    public int getItemCount() {
        return mConstructMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mConstructMessage.get(position).mMessageType== MessageType.TEXT_MESSAGE_MINE){
            return TYPE_TEXT_MINE;
        }else if(mConstructMessage.get(position).mMessageType== MessageType.TEXT_MESSAGE_OTHER){
            return TYPE_TEXT_OTHER;
        }else if(mConstructMessage.get(position).mMessageType== MessageType.IMAGE_MESSAGE_MINE){
            return TYPE_IMG_MINE;
        }else if(mConstructMessage.get(position).mMessageType== MessageType.IMAGE_MESSAGE_OTHER){
            return TYPE_IMG_OTHER;
        }else if(mConstructMessage.get(position).mMessageType== MessageType.AUDIO_MESSAGE_MINE){
            return TYPE_AUDIO_MINE;
        }else if(mConstructMessage.get(position).mMessageType== MessageType.AUDIO_MESSAGE_OTHER){
            return TYPE_AUDIO_OTHER;
        }
        return super.getItemViewType(position);
    }

    public boolean clear(){
        mConstructMessage.clear();
        this.notifyDataSetChanged();
      return mConstructMessage.size() == 0;
    }

    public boolean addAll(List<ConstructMessage> messages){
        mConstructMessage.clear();
        mConstructMessage.addAll(messages);
        this.notifyDataSetChanged();
        return true;
    }
}
