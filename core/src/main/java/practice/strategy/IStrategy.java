package practice.strategy;

/**
 * Created by xiaohaozi on 2019/10/17.
 */
public interface IStrategy {

    //构造报文
    public void buildJosn();

    //分发报文
    public void sendTo();
}
