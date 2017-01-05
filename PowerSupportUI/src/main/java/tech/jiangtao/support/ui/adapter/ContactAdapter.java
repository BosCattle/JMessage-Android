package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import tech.jiangtao.support.ui.linstener.DebouncedOnClickListener;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.viewholder.ContactCellViewHolder;
import tech.jiangtao.support.ui.viewholder.ContactHeadViewHolder;
import tech.jiangtao.support.ui.viewholder.ContactsViewHolder;

/**
 * Class: ContactAdapter </br>
 * Description: 通讯录 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 4:40 PM</br>
 * Update: 30/11/2016 4:40 PM </br>
 **/

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

  private Context mContext;
  private List<ConstrutContact> mDatas;
  private EasyViewHolder.OnItemClickListener mOnItemClickListener;

  public ContactAdapter(Context context,List<ConstrutContact> datas){
    mContext = context;
    mDatas = datas;
  }

  private void bindListener(ContactViewHolder viewholder) {
    if (viewholder!=null){
      viewholder.setOnItemClickListener(mOnItemClickListener);
    }
  }

  @Override public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ContactViewHolder viewHolder = null;
    switch (viewType){
      case 0:
        viewHolder = new ContactHeadViewHolder(mContext,parent);
        break;
      case 1:
        viewHolder = new ContactsViewHolder(mContext,parent);
        break;
      case 2:
        viewHolder = new ContactCellViewHolder(mContext,parent);
    }
    bindListener(viewHolder);
    return viewHolder;
  }

  @Override public void onBindViewHolder(ContactViewHolder holder, int position) {
    holder.bindTo(position,mDatas.get(position));
  }

  @Override public int getItemCount() {
    return mDatas.size();
  }

  @Override public int getItemViewType(int position) {
    int type = 0;
    if (mDatas.get(position).mType== ContactType.TYPE_GROUP){
      type = 0;
    }else if (mDatas.get(position).mType== ContactType.TYPE_NORMAL){
      type = 1;
    }else if (mDatas.get(position).mType== ContactType.TYPE_LETTER){
      type = 2;
    }
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

  public  void clear(){
    mDatas.clear();
    this.notifyDataSetChanged();
  }
}
