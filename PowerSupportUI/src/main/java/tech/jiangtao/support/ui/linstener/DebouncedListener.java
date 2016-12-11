package tech.jiangtao.support.ui.linstener;

import android.view.View;

/**
 * Created by kevin on 13/11/2016.
 */

public interface DebouncedListener {
  boolean onDebouncedClick(View v, int position);
}
