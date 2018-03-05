package com.travel.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.travel.core.interceptor.CustomHandlerInterceptor;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter  {
	
	  @Bean
	  public CustomHandlerInterceptor customHandlerInterceptor() {
	    return new CustomHandlerInterceptor();
	  }
	  
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(customHandlerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
