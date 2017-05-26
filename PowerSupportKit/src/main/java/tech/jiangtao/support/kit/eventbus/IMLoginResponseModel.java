package tech.jiangtao.support.kit.eventbus;

import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.model.Result;


public class IMLoginResponseModel {
    public Account account;
    public Result result;

    public IMLoginResponseModel(Account account, Result result) {
        this.account = account;
        this.result = result;
    }
}
