package com.hand.hap.master_distributed_execution.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.service.IHapApiService;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import com.hand.hap.master_distributed_execution.mapper.DistributeSituationMapper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.service.IDistributeSituationService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class DistributeSituationServiceImpl extends BaseServiceImpl<DistributeSituation> implements IDistributeSituationService {
    @Autowired
    private DistributeSituationMapper distributeSituationMapper;
    //在日志输出时可打印出日志信息所在类
    private final Logger logger = LoggerFactory.getLogger(DistributeSituationServiceImpl.class);
    @Autowired
    private IHapInterfaceHeaderService headerService;
    @Resource(name = "soapBean")
    private IHapApiService restService;

    //获取查询数据
    public List<DistributeSituation> selectDistributeSituation(DistributeSituation dto, IRequest request, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return distributeSituationMapper.selectDistributionData(dto);
    }
    //事务回滚
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String inAction() throws Exception {
        String returnMsg;
        JSONObject Response_Json;
        JSONObject jsonObj;
        String string = "{arg0:11,arg1:222}";
        logger.debug("请求 : ", string);
        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonObj = JSONObject.fromObject(string, jsonConfig);
            HapInterfaceHeader hapInterfaceHeader = this.headerService.getHeaderAndLine("HelloWs", "HELLOWS");
            Response_Json = this.restService.invoke(hapInterfaceHeader, jsonObj);
            System.out.println("请求报文：" + jsonObj);
            System.out.println("返回报文：" + Response_Json);
            if (Response_Json != null) {
                logger.debug("返回报文 : ", Response_Json.toString());
            }
            String soapEnvelope = Response_Json.getString("soap:Envelope");
            Response_Json = JSONObject.fromObject(soapEnvelope, jsonConfig);
            String soapBody = Response_Json.getString("soap:Body");

            Response_Json = JSONObject.fromObject(soapBody, jsonConfig);
            String publishHelloResponse = Response_Json.getString("ns2:publishHelloResponse");

            Response_Json = JSONObject.fromObject(publishHelloResponse, jsonConfig);
            returnMsg = Response_Json.getString("return");
        } catch (Exception e) {
            logger.error("SynRemotesInsertMainError : ", e);
            throw e;
        }
        return returnMsg;
    }
    //事务回滚
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void invoke(DistributeSituation dto) throws Exception {
        List<DistributeSituation> distributeSituationLsit = new ArrayList<>();

        JSONObject Response_Json;
        JSONObject jsonObj;

        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonObj = JSONObject.fromObject(dto, jsonConfig);
            HapInterfaceHeader hapInterfaceHeader = this.headerService.getHeaderAndLine("DistributeWs", "DistributeWs");
            Response_Json = this.restService.invoke(hapInterfaceHeader, jsonObj);
            System.out.println("请求报文：" + jsonObj);
            System.out.println("返回报文：" + Response_Json);
            if (Response_Json != null) {
                logger.debug("返回报文 : ", Response_Json.toString());
            }
/*            String soapEnvelope = Response_Json.getString("soap:Envelope");
            Response_Json = JSONObject.fromObject(soapEnvelope, jsonConfig);
            String soapBody = Response_Json.getString("soap:Body");

            Response_Json = JSONObject.fromObject(soapBody, jsonConfig);
            String publishHelloResponse = Response_Json.getString("ns2:selectDistributeResponse");

            Response_Json = JSONObject.fromObject(publishHelloResponse, jsonConfig);*/
          //  returnMsg = Response_Json.getString("return");
        } catch (Exception e) {
            logger.error("SynRemotesInsertMainError : ", e);
            throw e;
        }
        //return distributeSituationLsit;
    }
}