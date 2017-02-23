package com.pano.panoserver.interceptor;

import com.pano.panoserver.annotation.Authorization;
import com.pano.panoserver.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by wangboquan on 17/1/23.
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenRepository tokenRepository;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果有注解
        if (method.getAnnotation(Authorization.class) != null) {
            String authorization = request.getHeader("authorization");

            String[] param = authorization.split("_");
            if (param.length != 2) {
                return false;
            }
            //使用userId和源token简单拼接成的token，可以增加加密措施
            int userId = Integer.parseInt(param[0]);
            String token = param[1];
            if (tokenRepository.checkToken(userId, token)){
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } else {
            return true;
        }
    }
}
