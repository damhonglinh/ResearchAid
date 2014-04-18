package damhonglinh.model;

import java.io.Serializable;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class UserIdea implements Serializable {

    private static int curId = 1;
    private int id;
    private String name;

    public UserIdea(String name) {
        id = curId++;
        this.name = name;
    }

    public static int getCurId() {
        return curId;
    }

    public static void setCurId(int curId) {
        UserIdea.curId = curId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
