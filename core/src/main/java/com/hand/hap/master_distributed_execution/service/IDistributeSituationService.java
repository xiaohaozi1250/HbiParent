package com.hand.hap.master_distributed_execution.service;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IDistributeSituationService extends IBaseService<DistributeSituation>, ProxySelf<IDistributeSituationService> {

    //获取查询数据
    List<DistributeSituation> selectDistributeSituation(DistributeSituation dto, IRequest request, int page, int pagesize);

    String inAction() throws Exception;

    void invoke(List<DistributeSituation> dtoList) throws Exception;

    void WebSocketDemo(IRequest requestCtx, HttpSession session) throws CodeRuleException;

    void poiExport(HttpServletRequest request, List<DistributeSituation> dtoList);

    /**
     * 导入excel
     * @param iRequest
     * @param file
     * @return
     * @throws Exception
     * @Date 2017/3/2
     */
    boolean importExcel(IRequest iRequest, MultipartFile file) throws Exception;
}