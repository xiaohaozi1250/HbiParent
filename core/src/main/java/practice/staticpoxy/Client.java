package practice.staticpoxy;


/**
 * Created by xiaohaozi on 2018/12/21.
 */
public class Client {

    public static void main(String[] args) throws Throwable{
        // TODO Auto-generated method stub

        Subject rs=new RealSubject();//这里指定被代理类
        StaticSubject staticSubject = new StaticSubject(rs);
        System.out.println("\n\n"+"运行结果为：");
        staticSubject.request();
    }
}