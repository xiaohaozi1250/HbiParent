package com.hand.hap.ws;

import com.hand.hap.core.IRequest;
import com.hand.hap.master_distributed_execution.dto.DistributeSituation;
import com.hand.hap.master_distributed_execution.dto.XmlDistributeSituation;
import com.hand.hap.master_distributed_execution.dto.XmlDistributeSituationList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/7
 */
@WebService
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WsDistribute {
    @WebMethod
    List<DistributeSituation> selectDistribute(@WebParam(partName = "distribute", name = "InputParameters") XmlDistributeSituationList dtoList);
}
