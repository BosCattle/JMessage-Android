package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.china.epower.chat.R;

/**
 * Class: AddGroupActivity </br>
 * Description: 添加组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 12:21 AM</br>
 * Update: 13/11/2016 12:21 AM </br>
 **/
public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    public static void startAddGroup(Activity activity){
        Intent intent = new Intent(activity,AddGroupActivity.class);
        activity.startActivity(intent);
    }
}
