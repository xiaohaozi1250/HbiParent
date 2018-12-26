package practice.aop;

import org.springframework.stereotype.Service;

/**
 * Created by xiaohaozi on 2018/12/25.
 */
@Service
public class People {

    public void eat() {
        System.out.println("happyheng开始吃饭啦");
    }

    public void play(){

    }
}
