package com.github.basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyComponentA {

    @Autowired
    private MyComponentB myComponentB;

    public void SayHello() {
        String message = myComponentB.sayHello() + ", 그리고 난 컴포넌트 B";
        System.out.println(message);
    }

}
