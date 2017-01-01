package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tech.jiangtao.support.ui.pattern.SessionListMessage;

/**
 * Class: BaseSessionListViewHolder </br>
 * Description: 所有会话列表页的ViewHolder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 31/12/2016 12:45 PM</br>
 * Update: 31/12/2016 12:45 PM </br>
 **/

public abstract class BaseSessionListViewHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener{

  private EasyViewHolder.OnItemClickListener mItemClickListener;

  public BaseSessionListViewHolder(Context context, ViewGroup parent,int layoutId) {
    super(LayoutInflater.from(context).inflate(layoutId,parent,false));
    itemView.setOnClickListener(this);
  }

  public abstract void bindTo(int position,SessionListMessage session);


  @Override public void onClick(View v) {
    if (mItemClickListener!=null){
      mItemClickListener.onItemClick(getAdapterPosition(),v);
    }
  }

  public void setOnItemClickListener(EasyViewHolder.OnItemClickListener onItemClickListener){
    mItemClickListener = onItemClickListener;
  }
}
