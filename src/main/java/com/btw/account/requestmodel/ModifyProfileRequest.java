package com.btw.account.requestmodel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by gplzh on 2016/5/31.
 */

@ApiModel
public class ModifyProfileRequest {

    @ApiModelProperty(value = "昵称", required = true)
    public String nickname;

    @ApiModelProperty(value = "头像", required = true)
    public String avatar;

    @ApiModelProperty(value = "性别", required = true)
    public Boolean sex;

}
