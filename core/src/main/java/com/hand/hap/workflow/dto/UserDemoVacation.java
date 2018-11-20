package com.hand.hap.workflow.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "dzh_demo_vacation")
public class UserDemoVacation extends BaseDTO {

     public static final String FIELD_ID = "id";
     public static final String FIELD_USER_CODE = "userCode";
     public static final String FIELD_START_DATE = "startDate";
     public static final String FIELD_NEED_DAYS = "needDays";
     public static final String FIELD_LEAVE_REASON = "leaveReason";


     @Id
     @GeneratedValue
     private Long id;

     @NotEmpty
     @Length(max = 225)
     private String userCode; //用户编码

     private Date startDate; //开始日期

     private Long needDays; //请假天数

     @Length(max = 255)
     private String leaveReason; //请假理由


     public void setId(Long id){
         this.id = id;
     }

     public Long getId(){
         return id;
     }

     public void setUserCode(String userCode){
         this.userCode = userCode;
     }

     public String getUserCode(){
         return userCode;
     }

     public void setStartDate(Date startDate){
         this.startDate = startDate;
     }

     public Date getStartDate(){
         return startDate;
     }

     public void setNeedDays(Long needDays){
         this.needDays = needDays;
     }

     public Long getNeedDays(){
         return needDays;
     }

     public void setLeaveReason(String leaveReason){
         this.leaveReason = leaveReason;
     }

     public String getLeaveReason(){
         return leaveReason;
     }

     }
