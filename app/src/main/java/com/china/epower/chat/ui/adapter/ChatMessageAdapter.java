package com.china.epower.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.china.epower.chat.model.type.MessageType;
import com.china.epower.chat.ui.pattern.ConstructMessage;
import com.china.epower.chat.ui.viewholder.TextMessageMineViewHolder;
import com.china.epower.chat.ui.viewholder.TextMessageOtherViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2016/11/14.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatBaseViewHolder> {
    private static final int TYPE_TEXT_MINE = 0x01;
    private static final int TYPE_TEXT_OTHER = 0x02;
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
        }
        return super.getItemViewType(position);
    }

    public boolean clear(){
        mConstructMessage.clear();
        this.notifyDataSetChanged();
        if (mConstructMessage.size()==0){
            return true;
        }
        return false;
    }

    public boolean addAll(List<ConstructMessage> messages){
        mConstructMessage.clear();
        mConstructMessage.addAll(messages);
        this.notifyDataSetChanged();
        return true;
    }
}
