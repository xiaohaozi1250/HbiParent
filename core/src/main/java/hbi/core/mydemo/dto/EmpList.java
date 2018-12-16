package hbi.core.mydemo.dto;

import java.util.List;

/**
 * Created by La on 2018/10/30.
 */
public class EmpList {
    /*    public EmpList(){}

        public EmpList(List<Emp> emp){
            this();
            this.emp = emp;
        }*/
    private List<Emp> emp;

    public List<Emp> getEmp() {
        return emp;
    }

    public void setEmp(List<Emp> emp) {
        this.emp = emp;
    }

    private List<EmpBaseResponse> empBaseResponse;

    public List<EmpBaseResponse> getEmpBaseResponse() {
        return empBaseResponse;
    }

    public void setEmpBaseResponse(List<EmpBaseResponse> empBaseResponse) {
        this.empBaseResponse = empBaseResponse;
    }
}
