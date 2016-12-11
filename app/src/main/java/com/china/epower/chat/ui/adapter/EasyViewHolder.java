package com.china.epower.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class: EasyViewHolder </br>
 * Description: 最基本的ViewHolder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 12:25 AM</br>
 * Update: 13/11/2016 12:25 AM </br>
 * 实现点击，长按，左滑事件
 **/

public abstract class EasyViewHolder<T> extends RecyclerView.ViewHolder
    implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

  private int mMinScrollWidth = 200;
  private float mStartX;
  private float mStartY;
  private float mEndX;
  private float mEndY;
  private OnItemClickListener mItemClickListener;
  private OnItemLongClickListener mItemLongClickListener;
  private OnItemLeftScrollListener mItemLeftScrollListener;

  private EasyViewHolder(View view) {
    super(view);
    bindListener();
  }

  public EasyViewHolder(Context context, ViewGroup parent, int layoutId) {
    this(LayoutInflater.from(context).inflate(layoutId, parent, false));
  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    // 根据事件来进行处理
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mStartX = event.getX();
        mStartY = event.getY();
        break;
      case MotionEvent.ACTION_UP:
        mEndX = event.getX();
        mEndY = event.getY();
        if (mStartX - mEndX > mMinScrollWidth) {
          // 判断为左滑动
          if (mItemLeftScrollListener!=null){
            mItemLeftScrollListener.onItemLeftClick(getAdapterPosition(),v);
          }
        }
        break;
    }
    return false;
  }

  protected void bindListener(){
    itemView.setOnTouchListener(this);
    itemView.setOnClickListener(this);
    itemView.setOnLongClickListener(this);
  }

  protected void unbindListener(){
    itemView.setOnTouchListener(null);
    itemView.setOnClickListener(null);
    itemView.setOnLongClickListener(null);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener){
    mItemClickListener = onItemClickListener;
  }

  public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
    mItemLongClickListener = onItemLongClickListener;
  }

  public void setOnItemLeftScrollListener(OnItemLeftScrollListener onItemLeftScrollListener){
    mItemLeftScrollListener = onItemLeftScrollListener;
  }

  public abstract void bindTo(int position,T t);

  @Override public void onClick(View v) {
    if (mItemClickListener!=null){
      mItemClickListener.onItemClick(getAdapterPosition(),v);
    }
  }

  @Override public boolean onLongClick(View v) {
    return mItemLongClickListener != null && mItemLongClickListener.onItemLongClick(
        getAdapterPosition(), v);
  }

  public interface OnItemClickListener {
    void onItemClick(int position, View view);
  }

  public interface OnItemLongClickListener {
    boolean onItemLongClick(int position, View view);
  }

  public interface OnItemLeftScrollListener {
    void onItemLeftClick(int position, View view);
  }
}
