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
    private Box userIdeaTagArea;
    private Model model;
    private Box taggingArea;
    private JScrollPane userIdeaTagAreaScroll;
    private JScrollPane taggingAreaScroll;
    private JournalIdea journalIdea;
    private int thisWidth = 1024;
    private int thisHeight;

    public UserIdeaJournalIdeaFrame(Model model, JournalIdea journalIdea) {
        this.model = model;
        this.journalIdea = journalIdea;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        setSize(new Dimension(thisWidth, thisHeight = screenSize.height - screenInsets.bottom));
        setUndecorated(true);
        setResizable(false);
        setTitle("Idea Tags");
        setLocationRelativeTo(null);

        container = new Box(BoxLayout.X_AXIS);
        container.setOpaque(true);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 184, 135), 4),
                new LineBorder(Color.WHITE, 10)));
        container.setBackground(Color.WHITE);
        add(container);

        userIdeaTagArea = new Box(BoxLayout.Y_AXIS);
        userIdeaTagAreaScroll = new JScrollPane(userIdeaTagArea);
        userIdeaTagAreaScroll.getViewport().setBackground(Color.WHITE);
        userIdeaTagAreaScroll.setBorder(null);
        userIdeaTagAreaScroll.getVerticalScrollBar().setUnitIncrement(20);
        container.add(userIdeaTagAreaScroll);

        container.add(Box.createHorizontalStrut(12));

        taggingArea = new Box(BoxLayout.Y_AXIS);
        taggingAreaScroll = new JScrollPane(taggingArea);
//        taggingAreaScroll.setMaximumSize(new Dimension(thisWidth / 2, thisHeight));
        taggingAreaScroll.getViewport().setBackground(Color.WHITE);
        taggingAreaScroll.setBorder(null);
        taggingAreaScroll.getVerticalScrollBar().setUnitIncrement(20);
        container.add(taggingAreaScroll);

        drawUserIdeaTagArea();
        drawAllUserIdea();

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
        title = new JLabel("Idea tags");
        title.setFont(new Font("Arial", 0, 28));
        userIdeaTagArea.add(title);

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

        userIdeaTagArea.add(line);
        userIdeaTagArea.add(Box.createVerticalStrut(10));
    }
    //endregion

    //region refresh()
    private void refresh() {
        userIdeaTagArea.removeAll();
        taggingArea.removeAll();
        drawAllUserIdea();
        drawUserIdeaTagArea();
        taggingArea.revalidate();
        taggingArea.repaint();
        userIdeaTagArea.revalidate();
        userIdeaTagArea.repaint();
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

            taggingArea.add(uiLine);
            taggingArea.add(Box.createVerticalStrut(7));
        }


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
//        Box addLine = new Box(BoxLayout.X_AXIS);
//        addLine.setMaximumSize(new Dimension(5000, 35));
//        addLine.add(Box.createHorizontalGlue());
//        addLine.add(addUi);
//        addLine.add(Box.createHorizontalGlue());
        taggingArea.add(addUi);
        //endregion
    }
    //endregion

    //region add()
    private void add(UserIdea ui) {
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
