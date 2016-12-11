package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.ui.fragment.ChatFragment;

public class ChatActivity extends BaseActivity {

  public static final String TAG = ChatActivity.class.getSimpleName();
  public static final String VCARD = "vCard";
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  private VCardRealm mVCardRealm;
  private FragmentManager mFragmentManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    ButterKnife.bind(this);
    init();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  private void init() {
    mVCardRealm = getIntent().getParcelableExtra(VCARD);
    buildFragment();
    setUpToolbar();
  }

  public void buildFragment() {
    mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    ChatFragment fragment = ChatFragment.newInstance();
    Bundle bundle = new Bundle();
    bundle.putParcelable(VCARD, mVCardRealm);
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.chat_func_detail, fragment);
    fragmentTransaction.commit();
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText(mVCardRealm!=null&&mVCardRealm.getNickName() != null ? mVCardRealm.getNickName() : "");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(
        v -> ActivityCompat.finishAfterTransition(ChatActivity.this));
  }

  public static void startChat(Activity activity, VCardRealm object) {
    Intent intent = new Intent(activity, ChatActivity.class);
    intent.putExtra(VCARD, object);
    activity.startActivity(intent);
  }
}
