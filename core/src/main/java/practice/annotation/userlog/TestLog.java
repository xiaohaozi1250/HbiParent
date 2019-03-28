package practice.annotation.userlog;

import jodd.util.URLDecoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import practice.aop.People;

/**
 * Created by xiaohaozi on 2019/3/7.
 */
public class TestLog {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-beans.xml");

        People people = ctx.getBean(People.class);
        people.eat();
        people.play();

        String myclassPath = URLDecoder.decode(com.hand.hap.core.annotation.AuditEnabled.class
                .getProtectionDomain().getCodeSource()
                .getLocation().getFile(), "UTF-8");
        System.out.println(myclassPath);


    }
}
