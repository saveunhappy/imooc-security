package com.imooc.comtroller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.Callable;


@RestController
public class AsyncController {
    @Resource
    private MockQueue mockQueue;
    @Resource
    private DeferredResultHolder holder;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {
        logger.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> result = new DeferredResult<>();
        holder.getMap().put(orderNumber,result);
//        Callable<String> callable = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                logger.info("副线程开始");
//                Thread.sleep(1000);
//                logger.info("副线程结束");
//
//                return "success";
//            }
//        };

        logger.info("主线程返回");
        return result;
    }
}
