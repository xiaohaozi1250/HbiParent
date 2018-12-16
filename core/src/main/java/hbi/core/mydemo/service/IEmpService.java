package hbi.core.mydemo.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hbi.core.mydemo.dto.Emp;
import hbi.core.mydemo.dto.EmpBaseResponse;
import hbi.core.mydemo.dto.EmpRestInvoke;
import hbi.core.mydemo.exception.EmpBaseException;

import java.util.List;

public interface IEmpService extends IBaseService<Emp>, ProxySelf<IEmpService> {

    //针对单条数据处理
    EmpBaseResponse handleEmp(IRequest request, Emp emp) throws EmpBaseException;

    //针对全部数据处理
    List<EmpBaseResponse> handleEmp(IRequest request, List<Emp> emp) throws EmpBaseException;

    EmpBaseResponse handleEmp1(IRequest request, List<Emp> emp) throws EmpBaseException;

    Emp inAction(IRequest request, Emp emp) throws Exception;

    void inAction2(IRequest request) throws Exception;

    void WebSocketDemo(IRequest requestCtx);
}