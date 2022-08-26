package com.wx.mp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//@EnableAspectJAutoProxy
//@EnableScheduling
//@EnableTransactionManagement
@SpringBootApplication
@Configuration
public class WxMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxMpApplication.class, args);
    }
}
