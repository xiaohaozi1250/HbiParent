package com.hand.hap.ws;

import com.hand.hap.message.IMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by liuneng on 2017/2/20.
 */
@WebService(
        endpointInterface = "com.hand.hap.ws.HelloWs",
        serviceName = "hello"
)
public class HelloWsImpl implements HelloWs {
    @Autowired
    private IMessagePublisher publisher;

    @Override
    public String publishHello(@WebParam(name = "message") String message ,@WebParam(name = "name") String name) {
        publisher.publish("com.hand.hap:hello", "hello " + message + " name " + name);
        return "success";
    }
}
