package com.btw.response;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * Created by gplzh on 16-4-15.
 */
@ApiModel
public class ResponseEntity<T> {

    @ApiModelProperty(value = "状态码", required = true)
    private int code = 100;

    @ApiModelProperty(value = "错误信息，默认为空", required = true)
    private String msg;

    @ApiModelProperty(value = "结果", required = true)
    private T result;

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        try{
            String enumName = code.name();
            Message msg = code.getClass().getField(enumName).getAnnotation(Message.class);
            this.code = msg.code();
            this.msg = msg.msg();
        }
        catch (Exception e){}
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
