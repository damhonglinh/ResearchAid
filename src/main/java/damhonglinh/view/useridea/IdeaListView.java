package damhonglinh.view.useridea;

import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.KulButton;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class IdeaListView extends JPanel {

    private IdeaTab ideaTab;
    private JLabel title = new JLabel("All Ideas");
    private Model model;
    private JScrollPane scroll;
    private Box center;
    private KulButton addBut;
    private int width;

    public IdeaListView(Model model, IdeaTab ideaTab) {
        this.ideaTab = ideaTab;
        this.model = model;

        setLayout(new BorderLayout(1, 10));
        setBackground(Color.WHITE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) dim.getWidth() * 2 / 7;
        setPreferredSize(new Dimension(width, (int) dim.getHeight()));
        setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, new Color(230, 230, 230)),
                new EmptyBorder(10, 10, 10, 10)));

        title.setFont(new Font("Arial", Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        center = new Box(BoxLayout.Y_AXIS);
        scroll = new JScrollPane(center);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        center.setAlignmentY(0.5f);

        addBut = new KulButton("New", false);
        addBut.setPreferredSize(new Dimension(100, 30));
        addBut.setMaximumSize(new Dimension(100, 30));
        addBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNewUserIdea();
            }
        });

        add(title, BorderLayout.NORTH);
        add(scroll);
        add(addBut, BorderLayout.SOUTH);

        addAllIdea();
    }

    private void addAllIdea() {
        HashMap<Integer, UserIdea> ideas = model.getUserIdeas();
        Iterator<Map.Entry<Integer, UserIdea>> iter = ideas.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, UserIdea> entry = iter.next();

            final UserIdea ui = entry.getValue();
            final JTextArea label = createTextArea(center, ui.getName());

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        ideaTab.showJournalIdea(ui);
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        int input = JOptionPane.showConfirmDialog(IdeaListView.this,
                                "Delete this idea tag?", "Delete this idea tag?", JOptionPane.OK_CANCEL_OPTION);
                        if (input == JOptionPane.OK_OPTION) {
                            model.deleteUserIdea(ui);
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setBorder(new LineBorder(new Color(230, 230, 230)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    if (ideaTab.getUserIdea() != null && ideaTab.getUserIdea().getId() == ui.getId()) return;
                    label.setBorder(new EmptyBorder(1, 1, 1, 1));
                }
            });
        }
    }

    //region helper method
    private JTextArea createTextArea(JComponent parent, String text) {
        JTextArea textArea = new JTextArea(text, 3, 10);
        textArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setBorder(null);
        scroll.setMinimumSize(new Dimension(50, 150));
        scroll.setPreferredSize(new Dimension(50, 150));

        parent.add(scroll);

        return textArea;
    }
    //endregion

    private void addNewUserIdea() {
        new NewUserIdeaFrame(model).setVisible(true);
    }

    protected void refreshUserList() {
        center.removeAll();
        addAllIdea();
        center.revalidate();
        center.repaint();
    }
}
