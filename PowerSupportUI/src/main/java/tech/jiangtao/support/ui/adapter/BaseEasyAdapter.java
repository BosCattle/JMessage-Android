package tech.jiangtao.support.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import tech.jiangtao.support.ui.linstener.DebouncedOnClickListener;
import tech.jiangtao.support.ui.linstener.DebouncedOnLeftClickListener;
import tech.jiangtao.support.ui.linstener.DebouncedOnLongClickListener;

/**
 * Class: BaseEasyAdapter </br>
 * Description: 有关recyclerView最基本的adapter </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 11/11/2016 2:06 PM</br>
 * Update: 11/11/2016 2:06 PM </br>
 **/

public class BaseEasyAdapter extends RecyclerView.Adapter<EasyViewHolder> {

  private EasyViewHolder.OnItemClickListener mOnItemClickListener;
  private EasyViewHolder.OnItemLongClickListener mOnItemLongClickListener;
  private EasyViewHolder.OnItemLeftScrollListener mOnItemLeftScrollListener;
  private List<Object> mObjs = new ArrayList<>();
  private BaseEasyViewHolderFactory mViewHolderFactory;

  public BaseEasyAdapter(Context context,Class valueClass,Class<? extends EasyViewHolder> easyViewHolder){
    this(context);
    bind(valueClass,easyViewHolder);
  }

  public BaseEasyAdapter(Context context) {
    this(new BaseEasyViewHolderFactory(context));
  }

  public BaseEasyAdapter(BaseEasyViewHolderFactory baseEasyViewHolderFactory) {
    this.mViewHolderFactory = baseEasyViewHolderFactory;
  }

  public void bind(Class valueClass,Class<? extends EasyViewHolder> easyViewHolder){
    mViewHolderFactory.bind(valueClass,easyViewHolder);
  }

  public BaseEasyAdapter(BaseEasyViewHolderFactory easyViewHolderFactory, Class valueClass,
      Class<? extends EasyViewHolder> easyViewHolderClass) {
    this(easyViewHolderFactory);
    bind(valueClass, easyViewHolderClass);
  }

  public void viewHolderFactory(BaseEasyViewHolderFactory easyViewHolderFactory) {
    this.mViewHolderFactory = easyViewHolderFactory;
  }

  private void bindListeners(EasyViewHolder easyViewHolder) {
    if (easyViewHolder!=null){
      easyViewHolder.setOnItemClickListener(mOnItemClickListener);
      easyViewHolder.setOnItemLongClickListener(mOnItemLongClickListener);
      easyViewHolder.setOnItemLeftScrollListener(mOnItemLeftScrollListener);
    }
  }

  @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    EasyViewHolder easyViewHolder = mViewHolderFactory.create(viewType, parent);
    bindListeners(easyViewHolder);
    return easyViewHolder;
  }

  @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
    holder.bindTo(position, mObjs.get(position));
  }


  @Override public int getItemViewType(int position) {
    return mViewHolderFactory.itemViewType(mObjs.get(position));
  }

  @Override public int getItemCount() {
    return mObjs.size();
  }


  public void add(Object object, int position) {
    mObjs.add(position, object);
    notifyItemInserted(position);
  }

  public void add(Object object) {
    mObjs.add(object);
    notifyItemInserted(getIndex(object));
  }

  public void addAll(List<?> objects) {
    mObjs.clear();
    mObjs.addAll(objects);
    notifyDataSetChanged();
  }

  public void appendAll(List<?> objects) {
    if (objects == null) {
      throw new IllegalArgumentException("objects can not be null");
    }
    List<?> toAppends = filter(objects);
    final int toAppendSize = toAppends.size();
    if (toAppendSize <= 0) {
      return;
    }
    int prevSize = this.mObjs.size();
    List<Object> data = new ArrayList<>(prevSize + toAppendSize);
    data.addAll(mObjs);
    data.addAll(toAppends);
    mObjs = data;
    notifyItemRangeInserted(prevSize, toAppends.size());
  }

  /**
   * 去掉重复数据
   */
  private List<?> filter(List<?> data) {
    List<Object> returnData = new ArrayList<>();
    List<?> localDataList = this.mObjs;
    for (Object o : data) {
      if (!localDataList.contains(o)) {
        returnData.add(o);
      }
    }
    return returnData;
  }

  public boolean update(Object data, int position) {
    Object oldData = mObjs.set(position, data);
    if (oldData != null) {
      notifyItemChanged(position);
    }
    return oldData != null;
  }

  public boolean remove(Object data) {
    return mObjs.contains(data) && remove(getIndex(data));
  }

  public boolean remove(int position) {
    boolean validIndex = isValidIndex(position);
    if (validIndex) {
      mObjs.remove(position);
      notifyItemRemoved(position);
    }
    return validIndex;
  }

  public void clear() {
    mObjs.clear();
    notifyDataSetChanged();
  }

  public List<Object> getItems() {
    return mObjs;
  }

  public Object get(int position) {
    return mObjs.get(position);
  }

  public int getIndex(Object item) {
    return mObjs.indexOf(item);
  }

  public boolean isEmpty() {
    return getItemCount() == 0;
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

  public void setOnLeftClickListener(final EasyViewHolder.OnItemLeftScrollListener onLeftScrollListener){
    mOnItemLeftScrollListener = new DebouncedOnLeftClickListener() {
      @Override public boolean onDebouncedClick(View v, int position) {
        if (onLeftScrollListener!=null){
          onLeftScrollListener.onItemLeftClick(position,v);
        }
        return true;
      }
    };
  }

  private boolean isValidIndex(int position) {
    return position >= 0 && position < getItemCount();
  }
}
