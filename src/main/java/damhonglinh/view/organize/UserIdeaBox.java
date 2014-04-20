package damhonglinh.view.organize;

import damhonglinh.model.UserIdea;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
    protected static final int MIN_WIDTH = 100;
    protected static final int MIN_HEIGHT = 50;
    private final int CROSS_PADDING = 3;
    private final int CROSS_WIDTH = 8;
    private final int RESIZE_ZONE_WIDTH = 5;
    private boolean mouseOveredCross = false;
    private JPanel parent;
    private UserIdea userIdea;
    private OrganizeTab organizeTab;

    protected UserIdeaBox(OrganizeTab organizeTab, JPanel parent, UserIdea userIdea, int x, int y) {
        this.organizeTab = organizeTab;
        this.parent = parent;
        this.userIdea = userIdea;

        this.setText(userIdea.getName());
        this.setBorder(new CompoundBorder(new LineBorder(Color.ORANGE, 1), new EmptyBorder(5, 5, 5, 5)));
        this.setBounds(x, y, INIT_WIDTH, INIT_HEIGHT);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);

        MorphingMouseListener mouseAdapter = new MorphingMouseListener();
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    protected UserIdeaBox(OrganizeTab organizeTab, JPanel parent, UserIdea userIdea) {
        this(organizeTab, parent, userIdea, 0, 0);
    }

    //region getCrossArea()
    private Rectangle getCrossArea() {
        int x1 = getWidth() - CROSS_PADDING - CROSS_WIDTH;
        int y1 = CROSS_PADDING;

        return new Rectangle(x1, y1, CROSS_WIDTH, CROSS_WIDTH);
    }
    //endregion

    //region disappear()
    private void disappear() {
        setVisible(false);
        organizeTab.removeUserIdeaBox(this);
    }
    //endregion

    //region showUserIdeaDetail()
    private void showUserIdeaDetail() {
        new UserIdeaDetailFrame(organizeTab.getModel(), userIdea).setVisible(true);
    }
    //endregion

    //region getter
    public UserIdea getUserIdea() {
        return userIdea;
    }
    //endregion

    //region paintComponent()
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // region draw Cross
        int x1 = getCrossArea().x;
        int x2 = x1 + CROSS_WIDTH;
        int y1 = getCrossArea().y;
        int y2 = y1 + CROSS_WIDTH;
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y1, x1, y2);
        // endregion

        if (mouseOveredCross) {
            g.drawRect(x1, y1, CROSS_WIDTH, CROSS_WIDTH);
        }

        //region draw resize zone
        for (int i = RESIZE_ZONE_WIDTH * 2; i > 0; i -= 2) {
            g.setColor(Color.ORANGE);
            g.drawLine(getWidth(), getHeight() - i, getWidth() - i, getHeight());
        }
        //endregion
    }
    //endregion

    private class MorphingMouseListener extends MouseAdapter {
        //region class MorphingMouseListener
        private boolean inResizingZone = false;
        private boolean isResizing = false;
        private Point prevMousePosition;
        private Point prevMouseResizingPosition;

        @Override
        public void mouseReleased(MouseEvent e) {
            prevMousePosition = null;
            prevMouseResizingPosition = null;
            isResizing = false;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            prevMousePosition = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (isResizing) {
                if (prevMouseResizingPosition == null) return;
                int newWidth = getBounds().width + (e.getXOnScreen() - prevMouseResizingPosition.x);
                int newHeight = getBounds().height + (e.getYOnScreen() - prevMouseResizingPosition.y);

                prevMouseResizingPosition = e.getLocationOnScreen();

                if (newWidth >= MIN_WIDTH && newHeight >= MIN_HEIGHT) {
                    setBounds(getX(), getY(), newWidth, newHeight);
                    parent.repaint(getBounds());
                }

            } else { //moving position
                if (prevMousePosition == null) return;

                int newX = getBounds().x + (e.getXOnScreen() - prevMousePosition.x);
                int newY = getBounds().y + (e.getYOnScreen() - prevMousePosition.y);

                prevMousePosition = e.getLocationOnScreen();

                setBounds(newX, newY, getWidth(), getHeight());
                parent.repaint(getBounds());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) &&
                    getCrossArea().contains(e.getPoint())) {
                disappear();
                return;
            }

            prevMousePosition = e.getLocationOnScreen();

            if (inResizingZone) {
                isResizing = true;
                prevMouseResizingPosition = e.getLocationOnScreen();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // region checking cross area
            if (getCrossArea().contains(e.getPoint())) {
                mouseOveredCross = true;
                repaint();
                return;
            } else {
                mouseOveredCross = false;
                repaint();
            }
            //endregion

            int w = getBounds().width;
            int h = getBounds().height;
            int x = e.getPoint().x;
            int y = e.getPoint().y;
            int distX = x - w;
            int distY = y - h;

            if (distX >= -RESIZE_ZONE_WIDTH && distY >= -RESIZE_ZONE_WIDTH) {
                setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                inResizingZone = true;
            } else {
                inResizingZone = false;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            showUserIdeaDetail();
        }
        //endregion
    }
}
