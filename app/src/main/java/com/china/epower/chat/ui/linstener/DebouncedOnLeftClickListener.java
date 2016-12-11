package com.china.epower.chat.ui.linstener;

import android.view.View;
import com.china.epower.chat.ui.adapter.EasyViewHolder;

/**
 * Created by kevin on 13/11/2016.
 */

public abstract class DebouncedOnLeftClickListener implements EasyViewHolder.OnItemLeftScrollListener,DebouncedListener {

  private final DebouncedClickHandler debouncedClickHandler;
  protected DebouncedOnLeftClickListener() {
    this.debouncedClickHandler = new DebouncedClickHandler(this);
  }

  @Override public void onItemLeftClick(int position, View view) {
    debouncedClickHandler.invokeDebouncedClick(position,view);
  }
}
