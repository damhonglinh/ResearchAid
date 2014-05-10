package damhonglinh.view;

import damhonglinh.model.JournalIdea;
import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Dam Linh
 * Date: 10/05/14
 */
public class JournalIdeaDetailFrame extends JFrame {

    private Box container;
    private Model model;
    private JournalIdea journalIdea;
    private int thisWidth = 800;
    private int thisHeight = 600;

    public JournalIdeaDetailFrame(Model model, JournalIdea journalIdea) {
        this.model = model;
        this.journalIdea = journalIdea;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        setSize(new Dimension(thisWidth = screenSize.width - 50, screenSize.height - screenInsets.bottom));
        setUndecorated(true);
        setResizable(false);
        setTitle("Journal Idea Detail");
        setLocationRelativeTo(null);

        container = new Box(BoxLayout.X_AXIS);
        container.setOpaque(true);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 184, 135), 4),
                new LineBorder(Color.WHITE, 10)));
        container.setBackground(Color.WHITE);
        add(container);

        drawDetail();
        getRootPane().setFocusable(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JournalIdeaDetailFrame.this.setVisible(false);
                JournalIdeaDetailFrame.this.dispose();
            }
        });
    }

    private void drawDetail() {
        Box outerLine = new Box(BoxLayout.Y_AXIS);

        JLabel userIdeaTitle = new JLabel("Idea Tags: ");
        userIdeaTitle.setFont(new Font("Arial", 0, 14));

        JTextField userIdeas = new JTextField(getUserIdeaStringOfJournalIdea());
        userIdeas.setBorder(null);
        userIdeas.setEditable(false);
        userIdeas.setBackground(Color.WHITE);

        Box line1 = new Box(BoxLayout.X_AXIS);
        line1.add(userIdeaTitle);
        line1.add(Box.createHorizontalStrut(10));
        line1.add(userIdeas);
        line1.add(Box.createHorizontalGlue());
        line1.setPreferredSize(new Dimension(5000, 20));
        line1.setMaximumSize(new Dimension(5000, 20));

        Box line2 = new Box(BoxLayout.X_AXIS);

        final JTextArea text = new JTextArea(journalIdea.getText());
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setToolTipText("Journal's words");

        JScrollPane textScroll = new JScrollPane(text);
        textScroll.getViewport().setBackground(Color.WHITE);
        textScroll.setBorder(new LineBorder(new Color(210, 210, 210), 1));
        textScroll.getVerticalScrollBar().setUnitIncrement(20);

        final JTextArea implication = new JTextArea(journalIdea.getImplication());
        implication.setLineWrap(true);
        implication.setWrapStyleWord(true);
        implication.setToolTipText("In your words");

        JScrollPane implicationScroll = new JScrollPane(implication);
        implicationScroll.getViewport().setBackground(Color.WHITE);
        implicationScroll.setBorder(new LineBorder(new Color(210, 210, 210), 1));
        implicationScroll.getVerticalScrollBar().setUnitIncrement(20);

        line2.add(textScroll);
        line2.add(Box.createHorizontalStrut(3));
        line2.add(Utils.createArrowIcon());
        line2.add(Box.createHorizontalStrut(3));
        line2.add(implicationScroll);

        outerLine.add(line1);
        outerLine.add(Box.createVerticalStrut(5));
        outerLine.add(line2);

        container.add(outerLine);
    }

    //region helper method
    private String getUserIdeaStringOfJournalIdea() {
        String s = "";
        Iterator<Map.Entry<Integer, UserIdea>> iter = journalIdea.getUserIdeas().entrySet().iterator();

        while (iter.hasNext()) {
            UserIdea ui = iter.next().getValue();
            String temp;
            if (ui.getName().length() > 28) {
                temp = ui.getName().substring(0, 28) + "...";
            } else {
                temp = ui.getName();
            }

            s += temp + "; ";
        }

        return s;
    }
    //endregion

}
