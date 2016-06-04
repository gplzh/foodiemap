package com.btw.account.requestmodel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by gplzh on 2016/5/31.
 */

@ApiModel
public class ChangePasswordRequest {

    @ApiModelProperty(value = "原密码", required = true)
    @NotNull(message = "原密码不能为空")
    public String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotNull(message = "新密码不能为空")
    @Size(min = 6, max = 16, message = "密码必须在6~16个字符长度内")
    public String newPassword;
}
