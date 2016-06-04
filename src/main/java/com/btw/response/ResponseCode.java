package com.btw.response;


/**
 * Created by gplzh on 2016/5/29.
 */
public enum ResponseCode {

    @Message(code = -100, msg = "未知错误")
    UNKNOWN_ERROR,

    @Message(code = 100, msg = "没问题")
    OK,

    @Message(code = 101, msg = "尚未登陆")
    NO_LOGIN,

    @Message(code = 102, msg = "账号不存在")
    NO_AUTHORIZE,

    @Message(code = 103, msg = "该账号已授权")
    ALREADY_AUTHORIZE,

    @Message(code = 104, msg = "参数错误")
    PARAM_ERROR,

    @Message(code = 105, msg = "密码错误")
    PASSWORD_ERROR,

    @Message(code = 106, msg = "请上传文件")
    EMPTY_FILE,

    @Message(code = 107, msg = "文件必须小于100K")
    FILE_LIMIT_SIZE_100KB,

    @Message(code = 107, msg = "文件必须是PNG|JPG")
    FILE_EXTENSION_ERROR_PNG_JPG,
}
