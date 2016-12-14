package tech.jiangtao.support.kit.realm;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class: ContactRealm </br>
 * Description: 通讯录本地备份 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/12/2016 2:41 PM</br>
 * Update: 08/12/2016 2:41 PM </br>
 * 单独保存通讯录信息
 **/

public class ContactRealm extends RealmObject{

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    private String nickName;
    private String sex;
    private String subject;
    private String office;
    private String email;
    private String phoneNumber;
    private String signature;
    private byte[] avatar;
    private String firstLetter;
    private String allPinYin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getAllPinYin() {
        return allPinYin;
    }

    public void setAllPinYin(String allPinYin) {
        this.allPinYin = allPinYin;
    }
}
