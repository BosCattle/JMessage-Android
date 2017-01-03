//package tech.jiangtao.support.ui.fragment;
//
//import android.app.Dialog;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TextView;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import com.kevin.library.widget.CleanDialog;
//import com.kevin.library.widget.builder.IconFlag;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import org.jivesoftware.smack.SmackException;
//import org.jivesoftware.smack.XMPPConnection;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smack.roster.Roster;
//import org.jivesoftware.smackx.search.ReportedData;
//import org.jivesoftware.smackx.search.UserSearchManager;
//import org.jivesoftware.smackx.xdata.Form;
//import rx.Observable;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//import tech.jiangtao.support.kit.init.SupportIM;
//import tech.jiangtao.support.kit.service.SupportService;
//import tech.jiangtao.support.kit.util.ErrorAction;
//import tech.jiangtao.support.kit.util.StringSplitUtil;
//import tech.jiangtao.support.ui.R;
//import tech.jiangtao.support.ui.R2;
//import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
//import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
//import tech.jiangtao.support.ui.adapter.EasyViewHolder;
//import tech.jiangtao.support.ui.model.User;
//import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
//import tech.jiangtao.support.ui.viewholder.AddFriendViewHolder;
//import work.wanghao.simplehud.SimpleHUD;
//
///**
// * Class: AddFriendFragment </br>
// * Description: 封装的添加好友界面 </br>
// * Creator: kevin </br>
// * Email: jiangtao103cp@gmail.com </br>
// * Date: 10/12/2016 9:58 PM</br>
// * Update: 10/12/2016 9:58 PM </br>
// **/
//public class AddFriendFragment extends BaseFragment implements TextWatcher,EasyViewHolder.OnItemClickListener,View.OnClickListener {
//
//  @BindView(R2.id.search_view) EditText mSearchView;
//  @BindView(R2.id.search_submit) TextView mSearchSubmit;
//  @BindView(R2.id.friend_list) RecyclerView mFriendContaner;
//  private BaseEasyAdapter mBaseEasyAdapter;
//  private ArrayList<User> mList = new ArrayList<>();
//
//  public static AddFriendFragment newInstance() {
//    return new AddFriendFragment();
//  }
//
//  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
//      Bundle savedInstanceState) {
//    super.onCreateView(inflater, container, savedInstanceState);
//    ButterKnife.bind(getView());
//    mSearchView.addTextChangedListener(this);
//    mSearchSubmit.setOnClickListener(this);
//    setAdapter();
//    return getView();
//  }
//
//  public void setAdapter(){
//    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
//    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(getContext()));
//    mBaseEasyAdapter.setOnClickListener(this);
//    mBaseEasyAdapter.bind(User.class, AddFriendViewHolder.class);
//    mFriendContaner.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
//    mFriendContaner.setLayoutManager(
//        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//    mList = new ArrayList<>();
//    mFriendContaner.setAdapter(mBaseEasyAdapter);
//  }
//
//  @Override public int layout() {
//    return R.layout.fragment_add_friend;
//  }
//
//  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//  }
//
//  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//  }
//
//  @Override public void afterTextChanged(Editable s) {
//
//  }
//
//  @Override public void onItemClick(int position, View view) {
//    final CleanDialog dialog =
//        new CleanDialog.Builder(getContext()).iconFlag(IconFlag.OK)
//            .negativeButton("取消", Dialog::dismiss)
//            .positiveButton("确认", dialog1 -> {
//              Roster roster = Roster.getInstanceFor(null);
//              roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
//              Observable.create((Observable.OnSubscribe) o -> {
//                try {
//                  roster.createEntry(mList.get(position).username+"@"+SupportService.getmXMPPConnection().getServiceName(),mList.get(position).name,null);
//                } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | SmackException.NotConnectedException | XMPPException.XMPPErrorException e) {
//                  e.printStackTrace();
//                }
//              }).subscribeOn(Schedulers.io())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe();
//              dialog1.dismiss();
//            })
//            .title("确认添加"+mList.get(position).name+"为好友吗?")
//            .negativeTextColor(Color.BLACK)
//            .positiveTextColor(Color.BLACK)
//            .builder();
//    dialog.showDialog();
//  }
//
//  public void receiveData(String text,XMPPConnection connect){
//    UserSearchManager manager = new UserSearchManager(connect);
//    Form anwserForm = null;
//    try {
//      if (connect.getServiceName()!=null) {
//        String searchString ="";
//        Log.d("------>", "receiveData: "+connect.getServiceName());
//        Collection<String> services = manager.getSearchServices(); //search is UserSearchManager
//        String[] nn = services.toArray(new String[services.size()]);
//        for (String aNn : nn) {
//          searchString += aNn;
//        }
//        Log.d("------>", "receiveData: "+searchString);
////        Form form = manager.getSearchForm("search." + connect.getServiceName());
//        Form form = manager.getSearchForm(searchString);
//        anwserForm = form.createAnswerForm();
//        anwserForm.setAnswer("Username", true);
//        Log.d("用户信息", "receiveData: "+text);
//        anwserForm.setAnswer("search", text);
//      }else {
//        throw new NullPointerException("connect not be null");
//      }
//    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
//      e.printStackTrace();
//    }
//    Form finalAnwserForm = anwserForm;
//    rx.Observable.create(new rx.Observable.OnSubscribe<ReportedData>() {
//      @Override public void call(Subscriber<? super ReportedData> subscriber) {
//        try {
//          subscriber.onNext(manager.getSearchResults(finalAnwserForm,"search."+connect.getServiceName()));
//        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
//          subscriber.onError(e);
//          e.printStackTrace();
//        }
//      }
//    }).subscribeOn(Schedulers.io())
//        .doOnSubscribe(()-> SimpleHUD.showLoadingMessage(getContext(),"正在查询..",false))
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(data -> {
//          SimpleHUD.dismiss();
//          if (data.getRows() != null) {
//            Iterator<ReportedData.Row> it = data.getRows().iterator();
//            ReportedData.Row row = null;
//            mList.clear();
//            while (it.hasNext()) {
//              row = it.next();
//              User user = new User();
//              user.username = row.getValues("Username").iterator().next();
//              user.name = row.getValues("Name").iterator().next();
//              user.email = row.getValues("Email").iterator().next();
//              String jid = row.getValues("jid").iterator().next();
//              mList.add(user);
//            }
//            mBaseEasyAdapter.clear();
//            mBaseEasyAdapter.addAll(mList);
//            mBaseEasyAdapter.notifyDataSetChanged();
//          } else {
//          }
//        }, new ErrorAction() {
//          @Override public void call(Throwable throwable) {
//            super.call(throwable);
//            SimpleHUD.dismiss();
//          }
//        });
//  }
//
//  @Override public void onClick(View v) {
//    receiveData(mSearchView.getText().toString().trim(), SupportService.getmXMPPConnection());
//  }
//}
