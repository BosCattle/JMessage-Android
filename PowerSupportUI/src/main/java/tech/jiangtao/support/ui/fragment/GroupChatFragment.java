package tech.jiangtao.support.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.jiangtao.support.ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChatFragment extends Fragment {


    public GroupChatFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_chat, container, false);
    }

}
