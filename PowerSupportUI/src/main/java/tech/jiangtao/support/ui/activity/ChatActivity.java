package tech.jiangtao.support.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.fragment.ChatFragment;

public class ChatActivity extends BaseActivity {

  public static final String TAG = ChatActivity.class.getSimpleName();
  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  private ContactRealm mContactRealm;

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
    mContactRealm = getIntent().getParcelableExtra(SupportIM.VCARD);
    buildFragment();
    setUpToolbar();
  }

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    ChatFragment fragment = ChatFragment.newInstance();
    Bundle bundle = new Bundle();
    bundle.putParcelable(SupportIM.VCARD, mContactRealm);
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.chat_func_detail, fragment);
    fragmentTransaction.commit();
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText(
        mContactRealm != null && mContactRealm.getNickName() != null ? mContactRealm.getNickName()
            : "");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(
        v -> ActivityCompat.finishAfterTransition(ChatActivity.this));
  }

  public static void startChat(Activity activity, ContactRealm mContactRealm) {
    Intent intent = new Intent(activity, ChatActivity.class);
    intent.putExtra(SupportIM.VCARD, mContactRealm);
    activity.startActivity(intent);
  }
}
