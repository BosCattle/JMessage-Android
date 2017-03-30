package tech.jiangtao.support.kit.userdata;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.eventbus.LocalVCardEvent;
import tech.jiangtao.support.kit.eventbus.OwnVCardRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleVCard </br>
 * Description: 封装VCard </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/12/2016 11:02 PM</br>
 * Update: 01/12/2016 11:02 PM </br>
 * 保存通讯录和获取信息隔离,可以使用啊
 **/

public class SimpleVCard {

    public VCardCallback vCardCallback;

    public SimpleVCard(){
        if (!HermesEventBus.getDefault().isRegistered(this))
            HermesEventBus.getDefault().register(this);
    }

    // TODO: 2017/1/5 vcardRealm更改
    public void startUpdate(LocalVCardEvent realm, VCardCallback callBack){
        vCardCallback = callBack;
        HermesEventBus.getDefault().post(realm);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVCardEvent(OwnVCardRealm event){
        if (event.error==null){
            vCardCallback.success(event.success);
        }else {
            vCardCallback.error(event.error);
        }
    }

    public void destroy(){
        HermesEventBus.getDefault().unregister(this);
    }
}
