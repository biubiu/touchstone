package com.shawn.touchstone.di;

import com.shawn.touchstone.di.beans.RateLimiter;

public class Demo {

    public static void main(String[] args) {
        try{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
        rateLimiter.test();
        }catch (Throwable e) {
            System.err.println(e);
        }

    }
}
