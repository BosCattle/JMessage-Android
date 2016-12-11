package com.china.epower.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.china.epower.chat.model.type.ListDataType;
import com.china.epower.chat.ui.linstener.DebouncedOnClickListener;
import com.china.epower.chat.ui.pattern.ConstructListData;
import com.china.epower.chat.ui.viewholder.PersonAvatarViewHolder;
import com.china.epower.chat.ui.viewholder.PersonNormalViewHolder;
import com.china.epower.chat.ui.viewholder.PersonShadowViewHolder;
import com.china.epower.chat.ui.viewholder.PersonalImageViewHolder;
import com.china.epower.chat.ui.viewholder.PersonalTextViewHolder;
import java.util.List;

/**
 * Class: PersonalDataAdapter </br>
 * Description: 个人中心的页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 4:40 PM</br>
 * Update: 30/11/2016 4:40 PM </br>
 **/

public class PersonalDataAdapter extends RecyclerView.Adapter<PersonalBaseViewHolder> {

  private Context mContext;
  private List<ConstructListData> mDatas;
  private EasyViewHolder.OnItemClickListener mOnItemClickListener;

  public PersonalDataAdapter(Context context,List<ConstructListData> datas){
    mContext = context;
    mDatas = datas;
  }

  private void bindListener(PersonalBaseViewHolder viewholder) {
    if (viewholder!=null){
      viewholder.setOnItemClickListener(mOnItemClickListener);
    }
  }

  @Override public PersonalBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    PersonalBaseViewHolder viewHolder = null;
    switch (viewType){
      case 0:
        viewHolder = new PersonShadowViewHolder(mContext,parent);
        break;
      case 1:
        viewHolder = new PersonNormalViewHolder(mContext,parent);
        break;
      case 2:
        viewHolder = new PersonAvatarViewHolder(mContext,parent);
        break;
      case 3:
        viewHolder = new PersonalTextViewHolder(mContext,parent);
        break;
      case 4:
        viewHolder = new PersonalImageViewHolder(mContext,parent);
    }
    bindListener(viewHolder);
    return viewHolder;
  }

  @Override public void onBindViewHolder(PersonalBaseViewHolder holder, int position) {
    holder.bindTo(position,mDatas.get(position));
  }

  @Override public int getItemCount() {
    return mDatas.size();
  }

  @Override public int getItemViewType(int position) {
    int type = 0;
    if (mDatas.get(position).getmType()==ListDataType.TAG_SHADOW){
      type = 0;
    }else if (mDatas.get(position).getmType()==ListDataType.TAG_NORMAL){
      type = 1;
    }else if (mDatas.get(position).getmType()==ListDataType.TAG_HEAD){
      type = 2;
    }else if (mDatas.get(position).getmType()==ListDataType.TAG_TEXT){
      type = 3;
    }else if (mDatas.get(position).getmType()==ListDataType.TAG_IMAGE){
      type = 4;
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
