package com.imooc;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String greeting(String name) {
        System.out.println("greet");
        return "hello" + name;
    }
}
