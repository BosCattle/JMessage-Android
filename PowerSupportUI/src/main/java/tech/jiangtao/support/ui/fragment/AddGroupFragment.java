package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.jiangtao.support.ui.R;

/**
 * Class: AddGroupFragment </br>
 * Description: 加群 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 11:46 AM</br>
 * Update: 02/12/2016 11:46 AM </br>
 **/
public class AddGroupFragment extends Fragment {

  public AddGroupFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_add_group, container, false);
  }
}
