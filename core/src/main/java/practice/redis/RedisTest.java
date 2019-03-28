package practice.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaohaozi on 2019/3/14.
 */

public class RedisTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-beans.xml");

        RedisDemo redis = ctx.getBean(RedisDemo.class);
        redis.redisList();
    }


}
