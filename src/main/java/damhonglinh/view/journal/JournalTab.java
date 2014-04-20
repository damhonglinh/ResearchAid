package damhonglinh.view.journal;

import damhonglinh.model.Journal;
import damhonglinh.model.JournalIdea;
import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.KulButton;
import damhonglinh.view.UserIdeaJournalIdeaFrame;
import damhonglinh.view.Utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
public class JournalTab extends JPanel {

    private Model model;
    private JournalListView journalListView;
    private JPanel center;
    private Box journalIdeaArea;
    private Box addNewArea;
    private Journal journal;
    private JLabel title;

    public JournalTab(Model model) {
        this.model = model;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        journalListView = new JournalListView(model, this);
        center = new JPanel(new BorderLayout());
        center.setBackground(Color.WHITE);

        add(journalListView, BorderLayout.WEST);
        add(center);

        journalIdeaArea = new Box(BoxLayout.Y_AXIS);
        JScrollPane scroll = new JScrollPane(journalIdeaArea);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        center.add(scroll);

        Box titleLine = new Box(BoxLayout.X_AXIS);
        titleLine.setBorder(new EmptyBorder(3, 0, 3, 0));
        titleLine.setOpaque(true);
        titleLine.setBackground(new Color(235, 235, 235));
        titleLine.setMaximumSize(new Dimension(5000, 30));
        titleLine.setPreferredSize(new Dimension(5000, 30));
        center.add(titleLine, BorderLayout.NORTH);

        JLabel titleTitle = new JLabel("Supporting Ideas ");
        titleTitle.setFont(new Font("Arial", 0, 14));
        title = new JLabel("");
        title.setFont(new Font("Arial", 0, 18));

        titleLine.add(Box.createHorizontalGlue());
        titleLine.add(titleTitle);
        titleLine.add(title);
        titleLine.add(Box.createHorizontalGlue());

        drawAddNewArea();
    }

    //region drawAddNewArea()
    private void drawAddNewArea() {
        addNewArea = new Box(BoxLayout.Y_AXIS);
        addNewArea.setOpaque(true);
        addNewArea.setBackground(new Color(235, 235, 235));
        addNewArea.setBorder(new CompoundBorder(new MatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
                new EmptyBorder(7, 10, 15, 7)));
        center.add(addNewArea, BorderLayout.SOUTH);

        Box line2 = new Box(BoxLayout.X_AXIS);

        final JTextArea text = Utils.createTextArea(line2, "");
        text.setToolTipText("Journal's words");

        line2.add(Box.createHorizontalStrut(3));
        line2.add(Utils.createArrowIcon());
        line2.add(Box.createHorizontalStrut(3));

        final JTextArea implication = Utils.createTextArea(line2, "");
        implication.setToolTipText("In your words");

        KulButton add = new KulButton("Add", false);
        add.setPreferredSize(new Dimension(70, 25));
        add.setMaximumSize(new Dimension(70, 25));

        line2.add(Box.createHorizontalStrut(5));
        line2.add(add);

        addNewArea.add(line2);

        //region addMouseListener for add button
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (journal == null) {
                    System.err.println("Journal not found!");
                    JOptionPane.showMessageDialog(JournalTab.this, "Journal not found!",
                            "Journal not found!", JOptionPane.INFORMATION_MESSAGE);
                } else if (text.getText().isEmpty()) {
                    System.err.println("Quotation text cannot be empty!");
                    JOptionPane.showMessageDialog(JournalTab.this, "Quotation text cannot be empty!",
                            "Empty quotation!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (implication.getText().isEmpty()) {
                        implication.setText(text.getText());
                    }
                    model.createJournalIdea(journal, text.getText(), implication.getText());
                    text.setText("");
                    implication.setText("");
                }
            }
        });
        //endregion
    }
    //endregion

    //region helper method
    private String getUserIdeaStringOfJournalIdea(JournalIdea ji) {
        String s = "";
        Iterator<Map.Entry<Integer, UserIdea>> iter = ji.getUserIdeas().entrySet().iterator();

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

    //region drawAllJournalIdeas()
    private void drawAllJournalIdeas() {
        if (journal == null) return;

        HashMap<Integer, JournalIdea> journalIdeas = model.getJournalIdeas();
        Iterator<Map.Entry<Integer, JournalIdea>> iter = journalIdeas.entrySet().iterator();

        while (iter.hasNext()) {
            JournalIdea ji = iter.next().getValue();

            if (ji.getJournal().getId() == journal.getId()) {
                drawJournalIdeaLine(ji);
            }
        }
    }
    //endregion

    //region drawJournalIdeaLine()
    private void drawJournalIdeaLine(final JournalIdea ji) {
        Box outerLine = new Box(BoxLayout.Y_AXIS);
        outerLine.setBorder(new CompoundBorder(new MatteBorder(1, 0, 0, 0, new Color(215, 215, 215)),
                new EmptyBorder(7, 7, 7, 7)));
        outerLine.setMaximumSize(new Dimension(5000, 115));

        JLabel userIdeaTitle = new JLabel("Idea Tags: ");
        userIdeaTitle.setFont(new Font("Arial", 0, 14));

        JTextField userIdeas = new JTextField(getUserIdeaStringOfJournalIdea(ji));
        userIdeas.setBorder(null);
        userIdeas.setEditable(false);
        userIdeas.setBackground(Color.WHITE);

        Box line1 = new Box(BoxLayout.X_AXIS);
        line1.add(userIdeaTitle);
        line1.add(Box.createHorizontalStrut(10));
        line1.add(userIdeas);
        line1.add(Box.createHorizontalGlue());

        Box line2 = new Box(BoxLayout.X_AXIS);

        final JTextArea text = Utils.createTextArea(line2, ji.getText());
        text.setToolTipText("Journal's words");

        line2.add(Box.createHorizontalStrut(3));
        line2.add(Utils.createArrowIcon());
        line2.add(Box.createHorizontalStrut(3));

        final JTextArea implication = Utils.createTextArea(line2, ji.getImplication());
        implication.setToolTipText("In your words");

        KulButton save = new KulButton("Save", false);
        save.setToolTipText("Save changed");
        save.setPreferredSize(new Dimension(70, 23));
        save.setMaximumSize(new Dimension(70, 23));

        KulButton tagIdea = new KulButton("Tag Ideas", false);
        tagIdea.setToolTipText("Tag this journal idea to your ideas");
        tagIdea.setPreferredSize(new Dimension(70, 23));
        tagIdea.setMaximumSize(new Dimension(70, 23));

        KulButton delete = new KulButton("Delete", false);
        delete.setToolTipText("Delete this idea");
        delete.setPreferredSize(new Dimension(70, 23));
        delete.setMaximumSize(new Dimension(70, 23));

        Box butCol = new Box(BoxLayout.Y_AXIS);
        butCol.setMaximumSize(new Dimension(90, 100));
        butCol.add(save);
        butCol.add(Box.createVerticalStrut(4));
        butCol.add(tagIdea);
        butCol.add(Box.createVerticalStrut(4));
        butCol.add(delete);

        line2.add(Box.createHorizontalStrut(5));
        line2.add(butCol);

        outerLine.add(line1);
        outerLine.add(Box.createVerticalStrut(5));
        outerLine.add(line2);

        journalIdeaArea.add(outerLine);
        journalIdeaArea.add(Box.createVerticalStrut(5));

        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ji.setText(text.getText());
                ji.setImplication(implication.getText());
                model.saveData();
            }
        });

        tagIdea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new UserIdeaJournalIdeaFrame(model, ji).setVisible(true);
            }
        });

        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int input = JOptionPane.showConfirmDialog(JournalTab.this,
                        "Delete this idea?", "Delete this idea?", JOptionPane.OK_CANCEL_OPTION);
                if (input == JOptionPane.OK_OPTION) {
                    model.deleteJournalIdea(ji);
                }
            }
        });

    }
    //endregion

    //region showJournalIdea()
    protected void showJournalIdea(Journal j) {
        title.setText(j.getTitle() + " by " + j.getAuthor());
        journal = j;
        refreshJournalIdeaList();
    }
    //endregion

    //region refresh methods
    public void refreshJournalIdeaList() {
        journalIdeaArea.removeAll();
        drawAllJournalIdeas();
        journalIdeaArea.revalidate();
        journalIdeaArea.repaint();
    }

    public void refreshJournalList() {
        journalListView.refreshJournalList();
    }
    //endregion

    protected Journal getJournal() {
        return journal;
    }
}
