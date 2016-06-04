package com.btw.request;

import com.btw.response.ResponseEntity;
import com.btw.response.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * Created by gplzh on 2016/5/31.
 */

@Component
@Aspect
public class ControllerParamValidAspect {

    @Around("execution(* com.btw.account.UserController.*(..)) && args(request, bindingResult, session)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint, Object request, BindingResult bindingResult, Object session) throws Throwable{
        return checkBindingResult(proceedingJoinPoint, bindingResult);
    }

    @Around("execution(* com.btw.account.UserController.*(..)) && args(request, bindingResult)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint, Object request, BindingResult bindingResult) throws Throwable {
        return checkBindingResult(proceedingJoinPoint, bindingResult);
    }

    private Object checkBindingResult(ProceedingJoinPoint proceedingJoinPoint, BindingResult bindingResult) throws Throwable{
        if(bindingResult.hasErrors()){
            ResponseEntity response = new ResponseEntity();
            response.setCode(ResponseCode.PARAM_ERROR);
            response.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return response;
        }
        else{
            return proceedingJoinPoint.proceed();
        }
    }
}