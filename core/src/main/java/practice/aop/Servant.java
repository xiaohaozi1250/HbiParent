package practice.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by xiaohaozi on 2018/12/25.
 */
@Aspect
public class Servant {


    @Pointcut(value="execution(* practice.aop.People.eat(..))")
    public void Servant() {
    }

    /**
     *  * 在吃饭之前
     *  
     */
    @Before(value = "Servant()")
    public void prepareFood() {
        System.out.println("准备食物");
    }

    /**
     *  * 在吃饭之后
     *  
     */
    @After(value = "Servant()")
    public void clean() {
        System.out.println("打扫");
    }

}
