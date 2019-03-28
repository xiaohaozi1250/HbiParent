package practice.annotation.userlog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by xiaohaozi on 2019/3/7.
 */
@Aspect
@Component
public class UserLogAdvice {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserLogAdvice.class.getName());


    @Pointcut("execution (* practice.aop.People.play(..)) && @annotation(userLog)")
    public void controllerAspect(UserLog userLog) {
    }

    /**
     * 拦截的方法执行之前就执行
     *
     * @param joinPoint 拦截的方法传入的参数
     * @param userLog   自定义注解
     */
    @Before("controllerAspect(userLog)")
    public void addBeforeLogger(JoinPoint joinPoint, UserLog userLog) {
        LocalDateTime now = LocalDateTime.now();
        logger.info(now.toString() + "执行插入报表访问记录：[" + userLog.menuName() + ", " + userLog.menuDesc() + "]开始");
        System.out.println(now.toString() + "执行插入报表访问记录：[" + userLog.menuName() + ", " + userLog.menuDesc() + "]开始");


        //具体处理

    }

}
