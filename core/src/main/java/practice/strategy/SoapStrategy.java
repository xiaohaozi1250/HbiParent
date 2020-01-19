package practice.strategy;

/**
 * Created by xiaohaozi on 2019/10/17.
 */
public class SoapStrategy implements IStrategy {
    @Override
    public void buildJosn() {
       System.out.println("构建XML报文");
    }

    @Override
    public void sendTo() {
        System.out.println("发送XML报文");
    }
}
