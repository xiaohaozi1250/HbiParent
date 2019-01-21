package com.hand.hap.master_distributed_execution.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.dto.HapInterfaceHeader;
import com.hand.hap.intergration.service.IHapApiService;
import com.hand.hap.intergration.service.IHapInterfaceHeaderService;
import com.hand.hap.master_distributed_execution.dto.XmlDistributeSituation;
import com.hand.hap.master_distributed_execution.dto.XmlDistributeSituationList;
import com.hand.hap.master_distributed_execution.mapper.DistributeSituationMapper;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.message.websocket.CommandMessage;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.service.IDistributeSituationService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
@Component
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
    public void invoke(List<DistributeSituation> dtoList) throws Exception {

        XmlDistributeSituationList xmlList = new XmlDistributeSituationList();
        List<XmlDistributeSituation> xmlDistributeSituationList = new ArrayList<>();
        //获取参数值
        for (DistributeSituation dto : dtoList) {
            XmlDistributeSituation xmlDistributeSituation = new XmlDistributeSituation();
            xmlDistributeSituation.setHeaderId(dto.getHeaderId());
            xmlDistributeSituation.setItemCode(dto.getItemCode());
            xmlDistributeSituation.setName(dto.getName());
            xmlDistributeSituationList.add(xmlDistributeSituation);
        }
        xmlList.setDistributeSituationList(xmlDistributeSituationList);

        JSONObject Response_Json;
        JSONObject jsonObj;
        JSONArray jsonArray;
        try {
            JsonConfig jsonConfig = new JsonConfig();
            // List转换为Json数组
            jsonArray = JSONArray.fromObject(xmlList, jsonConfig);
            jsonObj = jsonArray.getJSONObject(0);
            System.out.println("请求报文：" + jsonObj.toString());
            //获取接口信息
            HapInterfaceHeader hapInterfaceHeader = this.headerService.getHeaderAndLine("DistributeWs", "DistributeWs");
            //调用Ws,并获取返回报文
            Response_Json = this.restService.invoke(hapInterfaceHeader, jsonObj);
            System.out.println("返回报文：" + Response_Json);
            if (Response_Json != null) {
                logger.debug("返回报文1 : ", Response_Json.toString());
            }
            //解析报文
            String soapEnvelope = Response_Json.getString("soap:Envelope");
            Response_Json = JSONObject.fromObject(soapEnvelope, jsonConfig);
            String soapBody = Response_Json.getString("soap:Body");
            Response_Json = JSONObject.fromObject(soapBody, jsonConfig);
            String selectDistributeResponse = Response_Json.getString("ns2:selectDistributeResponse");
            Response_Json = JSONObject.fromObject(selectDistributeResponse, jsonConfig);
            JSONArray itemArray = Response_Json.getJSONArray("item");
        } catch (Exception e) {
            logger.error("SynRemotesInsertMainError : ", e);
            throw e;
        }
        //return distributeSituationLsit;
    }

    @Autowired
    private IMessagePublisher messagePublisher;

    public void WedSocketDemo(IRequest requestCtx, HttpSession session) throws CodeRuleException {
        //HttpSession session =requestCtx.getAttribute()
        CommandMessage commandMessage = new CommandMessage();
        commandMessage.setUserName(requestCtx.getUserName());
        commandMessage.setAction("WedSocketDemo");
        String wSid = requestCtx.getAttribute("SessionId");
        //发布消息
        for (int i = 1; i <= 3; i++) {
            Map<String, Object> map = new HashMap<>();
            commandMessage.setSessionId(wSid);
            //commandMessage.setSessionId(session.getId());
            ((Map) map).put("MSG", "VAl:" + i);
            commandMessage.setParameter(map);
            messagePublisher.publish(HmdmWebSocketDemo.CHANNEL_WEB_SOCKET, commandMessage);
        }
    }
}