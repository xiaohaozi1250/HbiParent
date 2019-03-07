package practice.annotation.simulationsql;

/**
 * Created by xiaohaozi on 2019/3/7.
 */
@TableAnnotation("tb_test")
public class DtoAnnotation {

    @Deprecated
    private String tt;

    @ColAnnotation("table_id")
    private String id;

    @ColAnnotation("username")
    private String name;

    public DtoAnnotation(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
