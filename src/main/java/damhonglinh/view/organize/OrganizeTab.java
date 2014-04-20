package damhonglinh.view.organize;

import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class OrganizeTab extends JPanel {

    private Model model;
    private IdeaListView ideaListView;
    private JPanel center;
    private JScrollPane scroll;

    public OrganizeTab(Model model) {
        this.model = model;
        setBackground(Color.WHITE);

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        ideaListView = new IdeaListView(model, this);
        add(ideaListView, BorderLayout.WEST);

        center = new JPanel(null);
        center.setBackground(Color.WHITE);
        scroll = new JScrollPane(center);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        add(scroll);

        center.addMouseListener(new OrganizeTabMouseListener());
    }

    protected void addNewUserIdeaBox(UserIdea ui, Point p) {
        UserIdeaBox uib = new UserIdeaBox(center, ui, p.x, p.y);

        center.add(uib);
        center.repaint(uib.getBounds());
    }

    private class OrganizeTabMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (ideaListView.getActiveUserIdea() != null) {
                addNewUserIdeaBox(ideaListView.getActiveUserIdea(), e.getPoint());
            }
        }
    }
}
