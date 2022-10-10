package com.nsa.msc.takeaway.takeaway;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TakeawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeawayApplication.class, args);
    }

//    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
}
