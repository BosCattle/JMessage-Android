package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.ui.pattern.ConstructMessage;

/**
 * Created by jiang on 2016/11/14.
 * 设置基础的adapter
 */

public abstract class ChatBaseViewHolder extends RecyclerView.ViewHolder {
    public ChatBaseViewHolder(Context context, ViewGroup parent,int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId,parent,false));
    }


    public abstract void bindTo(int position,MessageRealm messageRealm);
}
