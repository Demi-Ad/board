package com.study.board.web.config;

import com.study.board.web.config.interceptor.UserAuthorizationInterceptor;
import com.study.board.web.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserAuthorizationInterceptor userAuthorizationInterceptor;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(Constants.RESOURCE_LOCATION);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthorizationInterceptor)
                .addPathPatterns("/post/**/edit","/post/**/delete");
    }
}
