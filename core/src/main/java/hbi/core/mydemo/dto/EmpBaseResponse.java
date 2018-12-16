package hbi.core.mydemo.dto;

import com.hand.hap.system.dto.ResponseData;

import java.util.List;

/**
 * Created by La on 2018/10/22.
 */
public class EmpBaseResponse extends ResponseData {

    public EmpBaseResponse(){}

    public EmpBaseResponse(List<Emp> emp){
        this();
        this.emp = emp;
    }
    private List<Emp> emp;
    public List<Emp> getData() {
        return emp;
    }

    public void setData(List<Emp> emp) {
        this.emp = emp;
    }

    private Status status = new Status();


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Status {
        private int code = 0;
        private String message = "success";

        public Status() {

        }

        public Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
