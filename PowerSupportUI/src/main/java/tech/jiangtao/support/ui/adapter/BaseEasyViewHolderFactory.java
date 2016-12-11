package tech.jiangtao.support.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class: BaseEasyViewHolderFactory </br>
 * Description: 最基本的adapter </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 12:24 AM</br>
 * Update: 13/11/2016 12:24 AM </br>
 **/

public class BaseEasyViewHolderFactory {

  private Context mContext;
  private Map<Class,Class<? extends EasyViewHolder>> mViewHolders = new HashMap<>();
  private List<Class> mValueClassType = new ArrayList<>();

  public BaseEasyViewHolderFactory(Context context){
    this.mContext = context;
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) public EasyViewHolder create(int viewType,ViewGroup parent){
    Class valueClass = mValueClassType.get(viewType);
    Class<? extends EasyViewHolder> easyViewHolder = mViewHolders.get(valueClass);
    try {
      Constructor<? extends EasyViewHolder> constructor = easyViewHolder.getDeclaredConstructor(Context.class,ViewGroup.class);
      return constructor.newInstance(mContext,parent);
      }
    catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException("参数不正确");
    }
  }

  public void bind(Class valueClass,Class<? extends EasyViewHolder> viewHolder){
    mValueClassType.add(valueClass);
    mViewHolders.put(valueClass,viewHolder);
  }

  public int itemViewType(Object obj){
    return mValueClassType.indexOf(obj.getClass());
  }

  public List<Class> getValueClassTypes() {
    return mValueClassType;
  }

  public Map<Class, Class<? extends EasyViewHolder>> getBoundViewHolders() {
    return mViewHolders;
  }
}
