package practice.staticpoxy;

/**
 * Created by xiaohaozi on 2018/12/21.
 */
public class RealISubject implements ISubject {
    @Override
    public void request(){
        System.out.println(" real subject.");
    }
}
