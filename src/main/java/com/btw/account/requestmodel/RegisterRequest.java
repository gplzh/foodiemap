package com.btw.account.requestmodel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by gplzh on 2016/5/29.
 */

@ApiModel
public class RegisterRequest {

    @ApiModelProperty(value = "认证类型")
    public int identityType;

    @ApiModelProperty(value = "认证标识")
    public String identifier;

    @ApiModelProperty(value = "认证密码")
    public String credential;

    @ApiModelProperty(value = "昵称")
    public String nickname;

    @ApiModelProperty(value = "头像地址")
    public String avatar;

    @ApiModelProperty(value = "手机号码")
//    @Pattern(regexp = "(\\\\+\\\\d+)?1[3458]\\\\d{9}$", message = "手机号码格式不正确")
    public String phone;

    @ApiModelProperty(value = "区号")
    public String zone;

    @ApiModelProperty(value = "验证码")
    public String code;
}
