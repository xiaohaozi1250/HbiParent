package practice.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaohaozi on 2018/12/25.
 */
public class aopTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-beans.xml");

        People people = ctx.getBean(People.class);
        people.eat();
    }
}
