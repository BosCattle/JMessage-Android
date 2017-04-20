package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;

/**
 * Class: MemberDeleteFragment </br>
 * Description: 删除群组用户 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 20/04/2017 5:59 PM</br>
 * Update: 20/04/2017 5:59 PM </br>
 **/
public class MemberDeleteFragment extends BaseFragment {

  public static MemberDeleteFragment newInstance() {
    return new MemberDeleteFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater,container,savedInstanceState);
    ButterKnife.bind(this,getView());
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_member_delete;
  }
}
