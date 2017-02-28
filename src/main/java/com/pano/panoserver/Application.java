package com.pano.panoserver;

import com.pano.panoserver.interceptor.AuthorizationInterceptor;
//import com.pano.panoserver.authorization.interceptor.TestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangboquan on 17/2/9.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    static class WebMvcConfigurer extends WebMvcConfigurerAdapter {

        @Bean
        public HandlerInterceptor authorizationInterceptor(){
            return new AuthorizationInterceptor();
        }

        @Bean
        public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();
            //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
            factory.setMaxFileSize("10MB"); //KB,MB
            /// 设置总上传数据总大小
            factory.setMaxRequestSize("10MB");
            //Sets the directory location where files will be stored.
            //factory.setLocation("路径地址");
            return factory.createMultipartConfig();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/**");
        }
    }
}
