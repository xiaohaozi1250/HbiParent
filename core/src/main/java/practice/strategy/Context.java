package practice.strategy;

/**
 * Created by xiaohaozi on 2019/10/17.
 */
public class Context {

    private IStrategy iStrategy;

    public Context(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    public void  send(){
        iStrategy.buildJosn();
        iStrategy.sendTo();
    }
}
