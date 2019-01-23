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
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.service.IDistributeSituationService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * Soap接口调用1
     */
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

    /**
     * Soap接口调用2
     *
     * @param dtoList
     */
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
    }

    /**
     * webSocket Demo
     *
     * @param requestCtx
     * @param session
     */
    @Autowired
    private IMessagePublisher messagePublisher;

    public void WebSocketDemo(IRequest requestCtx, HttpSession session) throws CodeRuleException {
        //HttpSession session =requestCtx.getAttribute()
        //初始化CommandMessage
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

    /**
     * 设置字体样式
     *
     * @param Titlestyle
     * @param font
     * @param fontsize
     * @param bold
     * @param Alignment
     */
    void setContFontStyle(HSSFCellStyle Titlestyle, HSSFFont font, int fontsize, String bold, short Alignment) {
        //设置水平对齐方式
        Titlestyle.setAlignment(Alignment);
        //设置垂直对齐方式 垂直居中
        Titlestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Titlestyle.setWrapText(true);
        //设置边框大小
        Titlestyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        Titlestyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Titlestyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        Titlestyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        if ("Y".equals(bold)) {
            font.setBold(true);
        }
        font.setFontName("宋体");
        //设置字号
        font.setFontHeightInPoints((short) fontsize);
        Titlestyle.setFont(font);
        //不启用自动换行
        Titlestyle.setWrapText(false);
    }

    /**
     * 创建String类型的单元格
     *
     * @param row
     * @param columnNum
     * @param style
     * @param cellValue
     */
    void createStringCell(HSSFRow row, int columnNum, HSSFCellStyle style, String cellValue) {
        HSSFCell cell = row.createCell(columnNum);
        if (row.getRowNum() == 3 && columnNum == 1)
            style.setWrapText(true);
        //赋值
        cell.setCellValue(cellValue);
        //设置单元格样式
        cell.setCellStyle(style);
    }

    /**
     * 创建Double类型的单元格
     *
     * @param row
     * @param columnNum
     * @param style
     * @param cellValue
     */
    void createDoubleCell(HSSFRow row, int columnNum, HSSFCellStyle style, Double cellValue) {
        HSSFCell cell = row.createCell(columnNum);
        if (cellValue == null) {
            cellValue = 0D;
        }
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell.setCellValue(cellValue);
        cell.setCellStyle(style);
    }

    /**
     * 转换日期为字符串
     *
     * @param var
     */
    private String formatDateNull(Date var) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (var == null) {
            return " ";
        } else {
            String str = sdf.format(var);
            return str;
        }
    }

    // POI 报表创建
    public void poiExport(HttpServletRequest request, List<DistributeSituation> dtoList) {
        //文件存放路径
        String filePath = "D:\\poitest1.xls";
        //创建Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表sheet，默认sheet名字为sheet0
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //两种方法创建sheet页
        //HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //设置默认宽度、高度值
        sheet.setDefaultColumnWidth(18);
        sheet.setDefaultRowHeightInPoints(20);
        //设置样式字体大小,居中
        HSSFCellStyle centerCuststyle = workbook.createCellStyle();

        HSSFFont custfont = workbook.createFont();

        setContFontStyle(centerCuststyle, custfont, 15, "N", HSSFCellStyle.ALIGN_CENTER);
        // 继续创建工作表，表名为test2
        //sheet = workbook.createSheet("test2");
        //创建行，从0开始
        HSSFRow row1 = sheet.createRow(0);

        //创建行的单元格从0开始
        HSSFCell cell1 = row1.createCell(0);
        //设置单元格内容
/*        row1.createCell(0).setCellValue("数据编码");
        row1.createCell(1).setCellValue("数据名称");
        row1.createCell(2).setCellValue("批次");*/
        createStringCell(row1, 0, centerCuststyle, "数据编码");
        createStringCell(row1, 1, centerCuststyle, "数据名称");
        createStringCell(row1, 2, centerCuststyle, "批次");
        createStringCell(row1, 3, centerCuststyle, "分发日期");

        createStringCell(row1, 4, centerCuststyle, "金额");
        for (int i = 0; i < dtoList.size(); i++) {
            int index = i + 1;
            //创建行，从1开始
            HSSFRow row = sheet.createRow(index);
            // 创建行的单元格从0开始
            //HSSFCell cell = row.createCell(index);
/*            row.createCell(0).setCellValue(dtoList.get(i).getItemCode());//设置单元格内容
            row.createCell(1).setCellValue(dtoList.get(i).getItemName());//设置单元格内容
            row.createCell(2).setCellValue(dtoList.get(i).getBatchNum());//设置单元格内容*/
            createStringCell(row, 0, centerCuststyle, dtoList.get(i).getItemCode());
            createStringCell(row, 1, centerCuststyle, dtoList.get(i).getItemName());
            createStringCell(row, 2, centerCuststyle, dtoList.get(i).getBatchNum());
            createStringCell(row, 3, centerCuststyle, formatDateNull(dtoList.get(i).getDistributionDate()));
            Long amount = 50L;
            //金额掩码
            createDoubleCell(row, 4, centerCuststyle, (double) amount);
            try {
                FileOutputStream out = new FileOutputStream(filePath);
                // 保存Excel文件
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}