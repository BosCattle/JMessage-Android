package com.china.epower.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.china.epower.chat.ui.pattern.ConstructMessage;

/**
 * Created by jiang on 2016/11/14.
 * 设置基础的adapter
 */

public abstract class ChatBaseViewHolder extends RecyclerView.ViewHolder {
    public ChatBaseViewHolder(Context context, ViewGroup parent,int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId,parent,false));
    }


    public abstract void bindTo(int position,ConstructMessage constructMessage);
}
