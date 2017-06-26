package shiro.model;

/**
 * Created by admin on 2017/4/26.
 */
public class Role {

    private Integer id;
    private String name;
    private String sn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                '}';
    }
}
