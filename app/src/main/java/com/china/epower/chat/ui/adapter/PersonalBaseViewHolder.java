package com.china.epower.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.china.epower.chat.ui.pattern.ConstructListData;

/**
 * Class: PersonalBaseViewHolder </br>
 * Description: 个人中心等部分的viewholder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 3:55 PM</br>
 * Update: 30/11/2016 3:55 PM </br>
 **/

public abstract class PersonalBaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
  private EasyViewHolder.OnItemClickListener mItemClickListener;
  public PersonalBaseViewHolder(Context context, ViewGroup parent,int layoutId) {
    super(LayoutInflater.from(context).inflate(layoutId,parent,false));
    itemView.setOnClickListener(this);
  }


  public abstract void bindTo(int position,ConstructListData l);


  @Override public void onClick(View v) {
    if (mItemClickListener!=null){
      mItemClickListener.onItemClick(getAdapterPosition(),v);
    }
  }

  public void setOnItemClickListener(EasyViewHolder.OnItemClickListener onItemClickListener){
    mItemClickListener = onItemClickListener;
  }
}
