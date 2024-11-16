package com.github.basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyComponentA {


    private MyComponentB myComponentB;

    public void SayHello() {
        String message = myComponentB.sayHello() + ", And I'm Component A";
        System.out.println(message);
    }

    @Autowired
    public void setMyComponentB(MyComponentB myComponentB) {
        this.myComponentB = myComponentB;
    }

    public MyComponentA(MyComponentB myComponentB) {
        this.myComponentB = myComponentB;
    }

}
