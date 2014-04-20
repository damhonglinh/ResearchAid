package damhonglinh.view.organize;

import damhonglinh.model.JournalIdea;
import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.Utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by damho_000 on 4/20/14.
 */
public class UserIdeaDetailFrame extends JFrame {

    private Model model;
    private Box container;
    private JLabel userIdeaTitle;
    private JTextArea userIdeaText;
    private JLabel journalIdeasTitle;
    private Box center;
    private JScrollPane scroll;
    private UserIdea userIdea;

    public UserIdeaDetailFrame(Model model, UserIdea userIdea) {
        this.model = model;
        this.userIdea = userIdea;

        setSize(new Dimension(1024, 700));
        setUndecorated(true);
        setResizable(false);
        setTitle(userIdea.getName());
        setLocationRelativeTo(null);

        container = new Box(BoxLayout.Y_AXIS);
        container.setOpaque(true);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 184, 135), 4),
                new LineBorder(Color.WHITE, 10)));
        container.setBackground(Color.WHITE);
        add(container);

        userIdeaTitle = new JLabel("Your idea:");
        userIdeaTitle.setFont(new Font("Arial", 0, 18));
        container.add(userIdeaTitle);

        userIdeaText = Utils.createTextArea(container, userIdea.getName());
        userIdeaText.setEditable(false);
        userIdeaText.setPreferredSize(new Dimension(500, 100));
        container.add(Box.createVerticalStrut(20));

        journalIdeasTitle = new JLabel("Supporting ideas from journals:");
        journalIdeasTitle.setFont(new Font("Arial", 0, 18));
        container.add(journalIdeasTitle);

        center = new Box(BoxLayout.Y_AXIS);
        scroll = new JScrollPane(center);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        container.add(scroll);

        container.add(Box.createVerticalStrut(10));
        drawAllJournalIdeas();

        getRootPane().setFocusable(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserIdeaDetailFrame.this.setVisible(false);
                UserIdeaDetailFrame.this.dispose();
            }
        });
    }

    private void drawAllJournalIdeas() {
        Iterator<Map.Entry<Integer, JournalIdea>> iter = model.getJournalIdeas().entrySet().iterator();

        while (iter.hasNext()) {
            JournalIdea ji = iter.next().getValue();
            if (ji.getUserIdeas().containsKey(userIdea.getId())) {
                drawEachJournalIdea(ji);
            }
        }
    }

    //region drawEachJournalIdea()
    private void drawEachJournalIdea(final JournalIdea ji) {
        Box outerLine = new Box(BoxLayout.Y_AXIS);
        outerLine.setBorder(new CompoundBorder(new MatteBorder(1, 0, 0, 0, new Color(215, 215, 215)),
                new EmptyBorder(7, 7, 7, 7)));
        outerLine.setMaximumSize(new Dimension(5000, 115));

        JLabel journalTitle = new JLabel("Found in journal: ");
        journalTitle.setFont(new Font("Arial", 0, 14));

        JTextField journals = new JTextField(ji.getJournal().getTitle() +
                ". Author: " + ji.getJournal().getAuthor());
        journals.setBorder(null);
        journals.setEditable(false);
        journals.setBackground(Color.WHITE);

        Box line1 = new Box(BoxLayout.X_AXIS);
        line1.add(journalTitle);
        line1.add(Box.createHorizontalStrut(10));
        line1.add(journals);
        line1.add(Box.createHorizontalGlue());

        Box line2 = new Box(BoxLayout.X_AXIS);

        final JTextArea text = Utils.createTextArea(line2, ji.getText());
        text.setToolTipText("Journal's words");
        text.setEditable(false);

        line2.add(Box.createHorizontalStrut(3));
        line2.add(Utils.createArrowIcon());
        line2.add(Box.createHorizontalStrut(3));

        final JTextArea implication = Utils.createTextArea(line2, ji.getImplication());
        implication.setToolTipText("In your words");
        implication.setEditable(false);

        outerLine.add(line1);
        outerLine.add(Box.createVerticalStrut(5));
        outerLine.add(line2);

        center.add(outerLine);
        center.add(Box.createVerticalStrut(5));
    }
    //endregion
}
