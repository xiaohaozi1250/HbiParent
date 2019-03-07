package practice.annotation.userlog;

import java.lang.annotation.*;

/**
 * Created by xiaohaozi on 2019/3/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLog {
    String menuName();

    String menuDesc();
}
