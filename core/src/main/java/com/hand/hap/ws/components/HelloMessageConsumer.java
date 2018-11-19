package com.hand.hap.ws.components;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import org.springframework.stereotype.Component;

/**
 * Created by liuneng on 2017/2/20.
 */
@Component
@TopicMonitor(channel = "com.hand.hap:hello")
public class HelloMessageConsumer implements IMessageConsumer<String> {

    @Override
    public void onMessage(String message , String pattern) {
        System.out.println("------------------------------------------");
        System.out.println(message);
        System.out.println("------------------------------------------");
    }
}
