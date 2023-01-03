package com.revature.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{
    
    @Autowired
    private LoggingInterceptor loggingInterceptor;
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registery){
        registery.addInterceptor(loggingInterceptor).addPathPatterns("/**").order(Ordered.HIGHEST_PRECEDENCE);
        registery.addInterceptor(authenticationInterceptor).addPathPatterns("/api/**").order(1);
    }
}
