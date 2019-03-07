package practice.aop;

import org.springframework.stereotype.Service;
import practice.annotation.userlog.UserLog;

/**
 * Created by xiaohaozi on 2018/12/25.
 */
@Service
public class People {


    public void eat() {
        System.out.println("happyheng开始吃饭啦");
    }

    @UserLog(menuName = "people",menuDesc = "play")
    public void play(){

    }
}
