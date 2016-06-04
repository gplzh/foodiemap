package com.btw.account.requestmodel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by gplzh on 2016/5/29.
 */

@ApiModel
public class LoginRequest {

    @ApiModelProperty(value = "认证类型", required = true)
    public int identityType;

    @NotNull
    @ApiModelProperty(value = "认证标识", required = true)
    public String identifier;

    @NotNull
    @ApiModelProperty(value = "认证密码", required = true)
    public String credential;

}
