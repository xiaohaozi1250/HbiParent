package hbi.core.empws.exception;

/**
 * Created by La on 2018/10/22.
 */
public class EmpBaseException extends Exception{
    private int code;
    private String value;

    public EmpBaseException(String message, int code, String value) {
        super(message);
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


}
