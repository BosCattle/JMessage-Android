package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import tech.jiangtao.support.ui.R;

/**
 * Class: GroupListActivity </br>
 * Description: 所有群组页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:23 PM</br>
 * Update: 08/01/2017 2:23 PM </br>
 **/
public class GroupListActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_list);
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupList(Context context){
    Intent intent = new Intent(context,GroupListActivity.class);
    context.startActivity(intent);
  }
}
