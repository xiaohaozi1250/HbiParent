package practice.dynamicpoxy;

/**
 * Created by xiaohaozi on 2018/12/21.
 */
public class RealSubject implements  Subject {
    @Override
    public void request(){
        System.out.println("From real subject.");
    }
}
