package com.dynamic.Model.response;
import com.zyc.fact.model.SysHead;

public enum CommonCode implements ResultCode {
    INVALID_PARAM("10003","参数非法"),
    SUCCESS("0000","操作成功!"),
    FAIL("11111","操作失败！"),
    UNAUTHENTICATED("10001","此操作需要登陆系统！"),
    UNAUTHORISE("10002","权限不足，无权操作！"),
    SERVER_ERROR("99999","抱歉，系统繁忙，请稍后重试！"),
    NO_RULE("404","找不到规则");

    //操作代码
    String code;
    //提示信息
    String message;
    private CommonCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static void setResultCode(CommonCode code, SysHead sysHead)
    {
        sysHead.setRetCode(code.code);
        sysHead.setRetMsg(code.message);
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
