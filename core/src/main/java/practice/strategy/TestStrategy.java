package practice.strategy;

/**
 * Created by xiaohaozi on 2019/10/17.
 */
public class TestStrategy {


    public static void main(String[] args) {
        Context context = null;
        context = new Context(new SoapStrategy());
        context.send();

        context = new Context(new RestStrategy());
        context.send();
    }

}
