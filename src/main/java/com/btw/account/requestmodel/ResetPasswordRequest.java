package com.btw.account.requestmodel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by gplzh on 2016/5/31.
 */

@ApiModel
public class ResetPasswordRequest {

    @ApiModelProperty(value = "新密码", required = true)
    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码必须在6~16个字符长度内")
    public String newPassword;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "(\\\\+\\\\d+)?1[3458]\\\\d{9}$", message = "手机号码格式不正确")
    public String phone;

    @ApiModelProperty(value = "区号")
    public String zone;

    @ApiModelProperty(value = "验证码")
    public String code;
}
