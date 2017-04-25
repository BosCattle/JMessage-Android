package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import tech.jiangtao.support.ui.linstener.DebouncedOnClickListener;
import tech.jiangtao.support.ui.linstener.DebouncedOnLongClickListener;
import tech.jiangtao.support.ui.pattern.SessionListMessage;
import tech.jiangtao.support.ui.viewholder.AllChatListViewHolder;

/**
 * Class: ContactAdapter </br>
 * Description: 通讯录 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 4:40 PM</br>
 * Update: 30/11/2016 4:40 PM </br>
 **/

public class SessionAdapter extends RecyclerView.Adapter<BaseSessionListViewHolder> {

  private Context mContext;
  private List<SessionListMessage> mDatas;
  private EasyViewHolder.OnItemClickListener mOnItemClickListener;
  private EasyViewHolder.OnItemLongClickListener mOnItemLongClickListener;

  public SessionAdapter(Context context,List<SessionListMessage> datas){
    mContext = context;
    mDatas = datas;
  }

  private void bindListener(BaseSessionListViewHolder viewholder) {
    if (viewholder!=null){
      viewholder.setOnItemClickListener(mOnItemClickListener);
      viewholder.setOnItemLongClickListener(mOnItemLongClickListener);
    }
  }

  @Override public BaseSessionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    BaseSessionListViewHolder viewHolder = null;
    switch (viewType){
      case 0:
        viewHolder = new AllChatListViewHolder(mContext, parent) {
        };
        break;
      case 1:
        break;
    }
    bindListener(viewHolder);
    return viewHolder;
  }

  @Override public void onBindViewHolder(BaseSessionListViewHolder holder, int position) {
    holder.bindTo(position,mDatas.get(position));
  }


  @Override public int getItemCount() {
    return mDatas.size();
  }

  @Override public int getItemViewType(int position) {
    int type;
    type = 0;
    return type;
  }

  public void setOnClickListener(final EasyViewHolder.OnItemClickListener listener) {
    this.mOnItemClickListener = new DebouncedOnClickListener() {
      @Override public boolean onDebouncedClick(View v, int position) {
        if (listener!=null){
          listener.onItemClick(position,v);
        }
        return true;
      }
    };
  }

  public void setOnLongClickListener(final EasyViewHolder.OnItemLongClickListener listener) {
    this.mOnItemLongClickListener = new DebouncedOnLongClickListener() {
      @Override public boolean onDebouncedClick(View v, int position) {
        if (listener!=null){
          listener.onItemLongClick(position,v);
        }
        return true;
      }
    };
  }

  public  void clear(){
    mDatas.clear();
    this.notifyDataSetChanged();
  }
}
