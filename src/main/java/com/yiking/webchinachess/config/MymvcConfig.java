package com.yiking.webchinachess.config;

import com.yiking.webchinachess.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MymvcConfig extends WebMvcConfigurerAdapter {


    @Bean
    public WebMvcConfigurerAdapter wevmvcControl() {
        WebMvcConfigurerAdapter webMvcConfigurerAdapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
                registry.addViewController("/main.html").setViewName("index");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/register.html").setViewName("register");
                registry.addViewController("/onlineindex.html").setViewName("onlineindex");
            }
        };

        return webMvcConfigurerAdapter;
    }

    @Bean
    public MyLocaleResolver localeResolver() {

        return new MyLocaleResolver();
    }
}
