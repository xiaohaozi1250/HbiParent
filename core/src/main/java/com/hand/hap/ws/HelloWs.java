package com.hand.hap.ws;

import javax.jws.WebService;

/**
 * Created by liuneng on 2017/2/20.
 */
@WebService
public interface HelloWs {
    String publishHello( String message,String name);
}
