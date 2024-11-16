package com.github.basic.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(name="Bean1")
    public MyBean1 makeMyBean1() {
        return new MyBean1();
    }

    @Bean(name="Bean2")
    public MyBean2 makeMyBean2() {
        return new MyBean2("test");
    }
}
