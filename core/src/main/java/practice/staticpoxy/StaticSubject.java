package practice.staticpoxy;

/**
 * Created by xiaohaozi on 2018/12/21.
 */
public class StaticSubject implements Subject{

    private Subject subject;

    public StaticSubject(Subject subject) {
        this.subject = subject;
    }


    @Override
    public void request() {
        System.out.println("subject begin");
        subject.request();
        System.out.println("subject end");
    }




}
