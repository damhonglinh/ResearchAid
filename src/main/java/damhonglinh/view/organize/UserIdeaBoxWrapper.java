package damhonglinh.view.organize;


import damhonglinh.model.UserIdea;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by damho_000 on 4/20/14.
 */
public class UserIdeaBoxWrapper implements Serializable {

    private UserIdea userIdea;
    private Rectangle rectangle;

    public UserIdeaBoxWrapper(UserIdea userIdea, Rectangle rectangle) {
        this.userIdea = userIdea;
        this.rectangle = rectangle;
    }

    public UserIdea getUserIdea() {
        return userIdea;
    }

    public void setUserIdea(UserIdea userIdea) {
        this.userIdea = userIdea;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
