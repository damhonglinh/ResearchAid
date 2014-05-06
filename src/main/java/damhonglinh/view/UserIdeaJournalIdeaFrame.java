package damhonglinh.view;

import damhonglinh.model.JournalIdea;
import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.useridea.NewUserIdeaFrame;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Dam Linh
 * Date: 19/04/14
 */
public class UserIdeaJournalIdeaFrame extends JFrame {

    private Box container;
    private JLabel title;
    private Box center;
    //    private JComboBox<String> newUserIdea;
    private KulButton add;
    private Model model;
    private Box addArea;
    private JScrollPane scroll;
    private JScrollPane addAreaScroll;
    private JournalIdea journalIdea;

    public UserIdeaJournalIdeaFrame(Model model, JournalIdea journalIdea) {
        this.model = model;
        this.journalIdea = journalIdea;

        setSize(new Dimension(600, 600));
        setUndecorated(true);
        setResizable(false);
        setTitle("Idea Tags");
        setLocationRelativeTo(null);

        container = new Box(BoxLayout.Y_AXIS);
        container.setOpaque(true);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 184, 135), 4),
                new LineBorder(Color.WHITE, 10)));
        container.setBackground(Color.WHITE);
        add(container);

        title = new JLabel("Idea tags");
        title.setFont(new Font("Arial", 0, 28));

        container.add(title);

        center = new Box(BoxLayout.Y_AXIS);
        scroll = new JScrollPane(center);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        container.add(scroll);

        addArea = new Box(BoxLayout.Y_AXIS);
//        addAreaScroll.setMaximumSize(new Dimension(5000, 300));
        addAreaScroll = new JScrollPane(addArea);
        addAreaScroll.getViewport().setBackground(Color.WHITE);
        addAreaScroll.setBorder(null);
        addAreaScroll.getVerticalScrollBar().setUnitIncrement(20);
        addAreaScroll.setMaximumSize(new Dimension(5000, 300));
        container.add(addAreaScroll);

        container.add(Box.createVerticalStrut(12));
        drawUserIdeaTagArea();
        drawAllUserIdea();

        //region addLine
        KulButton addUi = new KulButton("Add user idea", false);
        addUi.setFont(new Font("Arial", 0, 14));
        addUi.setPreferredSize(new Dimension(100, 30));
        addUi.setMaximumSize(new Dimension(100, 30));
        addUi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new NewUserIdeaFrame(UserIdeaJournalIdeaFrame.this.model).setVisible(true);
            }
        });
        Box addLine = new Box(BoxLayout.X_AXIS);
        addLine.setMaximumSize(new Dimension(5000, 35));
        addLine.add(Box.createHorizontalGlue());
        addLine.add(addUi);
        addLine.add(Box.createHorizontalGlue());
        container.add(addLine);
        //endregion

        getRootPane().setFocusable(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserIdeaJournalIdeaFrame.this.setVisible(false);
                UserIdeaJournalIdeaFrame.this.dispose();
            }
        });
    }

    private void drawAllUserIdea() {
        Iterator<Map.Entry<Integer, UserIdea>> iter = model.getUserIdeas().entrySet().iterator();

        while (iter.hasNext()) {
            UserIdea ui = iter.next().getValue();
            if (journalIdea.getUserIdeas().containsKey(ui.getId())) {
                drawEachUserIdea(ui);
            }
        }
    }

    //region drawEachUserIdea()
    private void drawEachUserIdea(final UserIdea ui) {
        Box line = new Box(BoxLayout.X_AXIS);
        line.setMaximumSize(new Dimension(5000, 80));

        JTextArea text = Utils.createTextArea(line, ui.getName());
        text.setEditable(false);
        text.setBackground(Color.WHITE);
        text.setBorder(null);

        KulButton remove = new KulButton("Remove", false);
        remove.setPreferredSize(new Dimension(80, 25));
        remove.setMaximumSize(new Dimension(80, 25));
        remove.setFont(new Font("Arial", 0, 14));
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.removeJournalIdeaFromUserIdea(ui, journalIdea);
                refresh();
            }
        });

        line.add(Box.createHorizontalStrut(5));
        line.add(remove);

        center.add(line);
        center.add(Box.createVerticalStrut(10));
    }
    //endregion

    //region refresh()
    private void refresh() {
        center.removeAll();
        addArea.removeAll();
        drawAllUserIdea();
        drawUserIdeaTagArea();
        addArea.revalidate();
        addArea.repaint();
        center.revalidate();
        center.repaint();
    }
    //endregion

    //region drawUserIdeaTagArea()
    private void drawUserIdeaTagArea() {
        Iterator<Map.Entry<Integer, UserIdea>> iter = model.getUserIdeas().entrySet().iterator();

        while (iter.hasNext()) {
            final Map.Entry<Integer, UserIdea> entry = iter.next();

            Box uiLine = new Box(BoxLayout.X_AXIS);
            uiLine.setMaximumSize(new Dimension(5000, 24));

            JTextField uiText = new JTextField(entry.getValue().getName(), 20);
            uiText.setEditable(false);
            uiText.setBorder(null);

            KulButton tag = new KulButton("Tag", false);
            tag.setFont(new Font("Arial", 0, 12));
            tag.setPreferredSize(new Dimension(60, 20));
            tag.setMaximumSize(new Dimension(60, 20));
            tag.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    add(entry.getValue());
                }
            });

            uiLine.add(tag);
            uiLine.add(Box.createHorizontalStrut(10));
            uiLine.add(uiText);

            addArea.add(uiLine);
            addArea.add(Box.createVerticalStrut(7));
        }
    }
    //endregion

    //region add()
    private void add(UserIdea ui) {
//        UserIdea ui = null;
//        Iterator<Map.Entry<Integer, UserIdea>> iter = model.getUserIdeas().entrySet().iterator();
//
//        int counter = 0;
//        while (iter.hasNext()) {
//            Map.Entry<Integer, UserIdea> entry = iter.next();
//            if (newUserIdea.getSelectedIndex() == counter) {
//                ui = entry.getValue();
//                break;
//            }
//            counter++;
//        }

        if (ui == null) {
            System.err.println("User Idea not found!");
            JOptionPane.showMessageDialog(this, "User idea not found!",
                    "User idea not found!", JOptionPane.INFORMATION_MESSAGE);
        }
        model.addJournalIdeaToUserIdea(ui, journalIdea);
        refresh();
    }
    //endregion
}
