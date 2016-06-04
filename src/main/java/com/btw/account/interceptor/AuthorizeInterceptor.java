package com.btw.account.interceptor;

import com.btw.account.annotations.Authentication;
import com.btw.account.domain.User;
import com.btw.response.ResponseEntity;
import com.btw.response.ResponseCode;
import com.btw.utils.PackageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gplzh on 2016/5/29.
 */

public class AuthorizeInterceptor implements HandlerInterceptor {

    //需要账号验证的uri
    private static HashMap<String, Boolean> _authentication_controller = null;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (_authentication_controller == null) {
            _authentication_controller = new HashMap<String, Boolean>();

            //扫描指定的包，把带有@Authentication注释的controller放入_authentication_uri中
            try {
                List<Class<?>> clazzes = PackageUtil.getClassList("com.btw.foodiemap.controller", true, Controller.class);
                clazzes.addAll(PackageUtil.getClassList("com.btw.account", false, Controller.class));
                // 遍历所有的Class
                for (Class<?> clazz : clazzes) {
                    //过滤没有@Controller注解的类
                    if (clazz.getAnnotation(Controller.class) == null) continue;
                    //过滤没有@RequestMapping注解的类
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    if (requestMapping == null) continue;
                    String[] reqMapOfClass = requestMapping.value();
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        //过滤没有@Authentication注解的方法
                        if (method.getAnnotation(Authentication.class) == null) continue;
                        //过滤没有@RequestMapping注解的方法
                        requestMapping = method.getAnnotation(RequestMapping.class);
                        if (requestMapping == null) continue;
                        String[] reqMapOfMethod = requestMapping.value();
                        //添加到Hash里
                        for (String cls : reqMapOfClass) {
                            for (String met : reqMapOfMethod) {
                                _authentication_controller.put(cls + met, true);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        String uri = httpServletRequest.getRequestURI();
        if (_authentication_controller.containsKey(uri)) {
            User user = (User) httpServletRequest.getSession().getAttribute("userInfo");
            if (user == null) {

                ResponseEntity noLoginResponse = new ResponseEntity();
                noLoginResponse.setCode(ResponseCode.NO_LOGIN);
                ObjectMapper mapper = new ObjectMapper();
                httpServletResponse.getWriter().write(mapper.writeValueAsString(noLoginResponse));

                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
