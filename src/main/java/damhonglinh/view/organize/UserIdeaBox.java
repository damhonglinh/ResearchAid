package damhonglinh.view.organize;

import damhonglinh.model.UserIdea;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Dam Linh
 * Date: 19/04/14
 */
public class UserIdeaBox extends JLabel {

    protected static final int INIT_WIDTH = 200;
    protected static final int INIT_HEIGHT = 50;
    private JPanel parent;
    private UserIdea userIdea;
    //    private boolean isMousePressed = false;
    private Point prevMousePosition;

    protected UserIdeaBox(JPanel parent, UserIdea userIdea, int x, int y) {
        this.parent = parent;
        this.userIdea = userIdea;

        this.setText(userIdea.getName());
        this.setBorder(new LineBorder(Color.RED, 1));
        this.setBounds(x, y, INIT_WIDTH, INIT_HEIGHT);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);

        this.addMouseListener(new UserIdeaBoxMouseListener());
        this.addMouseMotionListener(new UserIdeaBoxMouseListener());
    }

    protected UserIdeaBox(JPanel parent, UserIdea userIdea) {
        this(parent, userIdea, 0, 0);
    }


    private class UserIdeaBoxMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
//            isMousePressed = false;
            prevMousePosition = null;
        }


        @Override
        public void mouseExited(MouseEvent e) {
//            isMousePressed = false;
            prevMousePosition = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (prevMousePosition == null) return;

            System.out.println("Prev: " + prevMousePosition.x + ", " + prevMousePosition.y +
                    " -- Curr: " + e.getPoint().x + ", " + e.getPoint().y);

            int newX = getBounds().x + (e.getX() - prevMousePosition.x - 1);
            int newY = getBounds().y + (e.getY() - prevMousePosition.y - 1);

            prevMousePosition = e.getPoint();

            setBounds(newX, newY, getWidth(), getHeight());
            parent.repaint(getBounds());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevMousePosition = e.getPoint();

//                int w = UserIdeaBox.this.getWidth();
//                int h = UserIdeaBox.this.getHeight();
//                int x = e.getPoint().x;
//                int y = e.getPoint().y;
//                Insets ins = UserIdeaBox.this.getInsets();
//                boolean inBorder = (x < ins.left ||
//                        x > w - ins.right ||
//                        y < ins.top ||
//                        y > h - ins.bottom);
//                if (inBorder) {
//                    System.out.println(e.getPoint());
//                } else {
//                    System.out.println("Ignore!");
//                }
        }
    }
}
