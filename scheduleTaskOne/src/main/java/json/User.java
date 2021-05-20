package json;

import java.util.List;

/**
 * @author
 * @data 2021/5/18 15:36
 * @Description
 */
public class User {

    private String name;
    private String value;
    private String type;
    private List<User> child;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getChild() {
        return child;
    }

    public void setChild(List<User> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", child=" + child +
                '}';
    }
}
