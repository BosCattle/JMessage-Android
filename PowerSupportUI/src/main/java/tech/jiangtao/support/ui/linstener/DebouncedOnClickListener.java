package tech.jiangtao.support.ui.linstener;

import android.view.View;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;

/**
 * Created by kevin on 13/11/2016.
 */

public abstract class DebouncedOnClickListener implements EasyViewHolder.OnItemClickListener,DebouncedListener {
  private final DebouncedClickHandler debouncedClickHandler;

  protected DebouncedOnClickListener() {
    this.debouncedClickHandler = new DebouncedClickHandler(this);
  }

  @Override public void onItemClick(int position, View view) {
    debouncedClickHandler.invokeDebouncedClick(position,view);
  }
}
