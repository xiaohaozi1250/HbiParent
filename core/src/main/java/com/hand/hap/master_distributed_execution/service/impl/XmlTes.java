package com.hand.hap.master_distributed_execution.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.intergration.exception.HapApiException;
import com.hand.hap.intergration.util.JSONAndMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/4
 */
public class XmlTes {
    public static void main(String[] args) {
        String xml = "";
        String inbound = "{arg0:11,agr1:22}";
        try {
            if (inbound != null) {
                xml = JSONAndMap.jsonToXml(inbound, "");
            }
        } catch (Exception e) {
            System.out.println("1111");
        }
        System.out.println(xml);
    }
}
