package com.imooc.comtroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;


@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping("/order")
    public Callable<String> order() throws InterruptedException {
        logger.info("主线程开始");
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程结束");

                return "success";
            }
        };
        logger.info("主线程返回");
        return callable;
    }
}
