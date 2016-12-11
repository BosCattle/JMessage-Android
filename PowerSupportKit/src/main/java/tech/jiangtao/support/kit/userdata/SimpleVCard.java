package tech.jiangtao.support.kit.userdata;

import android.util.Log;
import io.realm.Realm;
import io.realm.RealmResults;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.StringSplitUtil;

/**
 * Class: SimpleVCard </br>
 * Description: 封装VCard </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/12/2016 11:02 PM</br>
 * Update: 01/12/2016 11:02 PM </br>
 **/

public class SimpleVCard {
  private VCardCallback mVCardCallback;
  private VCardManager manager = VCardManager.getInstanceFor(SupportService.getmXMPPConnection());
  private String mUserJID;

  public SimpleVCard(String userJID) {
    mUserJID = StringSplitUtil.splitDivider(userJID);
  }

  public void setmVCardCallback(VCardCallback cardCallback) {
    mVCardCallback = cardCallback;
  }

  public void getVCard() {
    Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
      try {
        subscriber.onNext(manager.loadVCard(mUserJID));
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        e.printStackTrace();
        subscriber.onError(e);
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(vCard -> {
          if (mVCardCallback != null) {
            mVCardCallback.recieveVCard(vCard, StringSplitUtil.splitDivider(mUserJID));
            updateRealm(StringSplitUtil.splitDivider(mUserJID), vCard);
          }
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            Log.d(TAG, "call: " + throwable.toString());
          }
        });
  }

  public void setVCard(VCard card) {
    Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        try {
          manager.saveVCard(card);
          subscriber.onCompleted();
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          subscriber.onError(e);
          e.printStackTrace();
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        if (mVCardCallback != null) {
          mVCardCallback.settingVCard("上传失败" + throwable.toString());
        } else {
          throw new RuntimeException("mVCardCallback can not be null.");
        }
      }
    }, () -> {
      if (mVCardCallback != null) {
        mVCardCallback.settingVCard("上传成功");
      } else {
        throw new RuntimeException("mVCardCallback can not be null.");
      }
    });
  }

  private void createVCard(VCard vCard) {
    Realm realm = Realm.getDefaultInstance();
    VCardRealm vCardRealm = new VCardRealm();
    setVCardTrans(vCardRealm, vCard);
    realm.beginTransaction();
    realm.copyToRealm(vCardRealm);
    realm.commitTransaction();
  }

  private void setVCardTrans(VCardRealm vCardRealm, VCard vCard) {
    vCardRealm.setJid(StringSplitUtil.splitDivider(mUserJID));
    updateVCardTrans(vCardRealm, vCard);
  }

  private void updateVCardTrans(VCardRealm vCardRealm, VCard vCard) {
    vCardRealm.setNickName(vCard.getNickName());
    vCardRealm.setSex(vCard.getField("sex"));
    vCardRealm.setSubject(vCard.getField("subject"));
    vCardRealm.setOffice(vCard.getField("office"));
    vCardRealm.setEmail(vCard.getEmailWork());
    vCardRealm.setPhoneNumber(vCard.getPhoneWork("voice"));
    vCardRealm.setSignature(vCard.getField("signature"));
    vCardRealm.setAvatar(vCard.getAvatar());
  }

  private void updateRealm(String userJid, VCard vCard) {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<VCardRealm> result =
        realm.where(VCardRealm.class).equalTo("jid", userJid).findAll();
    if (result.size() == 0) {
      createVCard(vCard);
    } else {
      VCardRealm vCardRealm = result.first();
      realm.executeTransaction(realm1 -> {
        updateVCardTrans(vCardRealm, vCard);
      });
    }
  }
}
