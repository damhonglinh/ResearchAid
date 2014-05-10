package damhonglinh.view.useridea;

import damhonglinh.model.Journal;
import damhonglinh.model.JournalIdea;
import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.JournalIdeaDetailFrame;
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
import java.util.*;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class IdeaTab extends JPanel {

    private Model model;
    private IdeaListView ideaListView;
    private JPanel center;
    private Box journalIdeaArea;
    private Box addNewArea;
    private JComboBox journals;
    private UserIdea userIdea;
    private JLabel title;

    public IdeaTab(Model model) {
        this.model = model;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        ideaListView = new IdeaListView(model, this);
        center = new JPanel(new BorderLayout());
        center.setBackground(Color.WHITE);

        add(ideaListView, BorderLayout.WEST);
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

        JLabel journalTitle = new JLabel("Journal");
        journalTitle.setFont(new Font("Arial", 0, 14));
        journals = new JComboBox();
        journals.setPreferredSize(new Dimension(100, 20));
        refreshJournalComboBox();

        Box line1 = new Box(BoxLayout.X_AXIS);
        line1.add(journalTitle);
        line1.add(Box.createHorizontalStrut(10));
        line1.add(journals);
        line1.add(Box.createHorizontalGlue());

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

        addNewArea.add(line1);
        addNewArea.add(Box.createVerticalStrut(5));
        addNewArea.add(line2);

        //region addMouseListener for add button
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                HashMap<Integer, Journal> allJournals = model.getJournals();
                Iterator<Map.Entry<Integer, Journal>> iter = allJournals.entrySet().iterator();
                Journal journal = null;

                int counter = 0;
                while (iter.hasNext()) {
                    Map.Entry<Integer, Journal> entry = iter.next();
                    if (journals.getSelectedIndex() == counter) {
                        journal = entry.getValue();
                        break;
                    }
                    counter++;
                }
                if (journal == null) {
                    System.err.println("Journal not found!");
                    JOptionPane.showMessageDialog(IdeaTab.this, "Journal not found!",
                            "Journal not found!", JOptionPane.INFORMATION_MESSAGE);
                } else if (text.getText().isEmpty()) {
                    System.err.println("Quotation text cannot be empty!");
                    JOptionPane.showMessageDialog(IdeaTab.this, "Quotation text cannot be empty!",
                            "Empty quotation!", JOptionPane.INFORMATION_MESSAGE);
                } else {
//                    if (implication.getText().isEmpty()) {
//                        implication.setText(text.getText());
//                    }
//                    model.addJournalIdeaToUserIdea(userIdea,
//                            model.createJournalIdea(journal, text.getText(), implication.getText()));
//                    text.setText("");
//                    implication.setText("");

                    String textText = text.getText().replaceAll("\n", " ").replaceAll("\r", " ");
                    textText = textText.trim().replaceAll(" +", " ");

                    String implicationText = implication.getText();

                    if (implicationText.isEmpty()) {
                        implicationText = textText;
                    }
                    model.addJournalIdeaToUserIdea(userIdea,
                            model.createJournalIdea(journal, textText, implicationText));
                    text.setText("");
                    implication.setText("");
                }
            }
        });
        //endregion
    }
    //endregion

    //region drawAllJournalIdeas()
    private void drawAllJournalIdeas() {
        if (userIdea == null) return;

        SortedMap<Integer, JournalIdea> keys = new TreeMap<>(model.getJournalIdeas());

        for (JournalIdea ji : keys.values()) {
            Iterator<Map.Entry<Integer, UserIdea>> ideas = ji.getUserIdeas().entrySet().iterator();
            while (ideas.hasNext()) {
                UserIdea ui = ideas.next().getValue();
                if (ui.getId() == userIdea.getId()) {
                    drawJournalIdeaLine(ji);
                    break;
                }
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

        line2.add(Box.createHorizontalStrut(3));

        JLabel arrowIcon = Utils.createArrowIcon();
        arrowIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new JournalIdeaDetailFrame(model, ji).setVisible(true);
            }
        });

        line2.add(arrowIcon);
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
                int input = JOptionPane.showConfirmDialog(IdeaTab.this,
                        "Delete this idea?", "Delete this idea?", JOptionPane.OK_CANCEL_OPTION);
                if (input == JOptionPane.OK_OPTION) {
                    model.deleteJournalIdea(ji);
                }
            }
        });

    }
    //endregion

    //region showJournalIdea()
    protected void showJournalIdea(UserIdea ui) {
        System.out.println("Show idea details");
        title.setText(ui.getName());
        userIdea = ui;
        refreshJournalIdeaList();
    }
    //endregion

    //region refresh()
    public void refresh() {
        refreshJournalComboBox();
        refreshJournalIdeaList();
        refreshUserIdeaList();
    }
    //endregion

    //region refreshUserIdeaList()
    public void refreshUserIdeaList() {
        ideaListView.refreshUserList();
    }
    //endregion

    //region refreshJournalIdeaList()
    public void refreshJournalIdeaList() {
        journalIdeaArea.removeAll();
        drawAllJournalIdeas();
        journalIdeaArea.revalidate();
        journalIdeaArea.repaint();
    }
    //endregion

    //region refreshJournalComboBox()
    public void refreshJournalComboBox() {
        journals.removeAllItems();
        HashMap<Integer, Journal> allJournals = model.getJournals();
        Iterator<Map.Entry<Integer, Journal>> iter = allJournals.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, Journal> entry = iter.next();
            journals.addItem(entry.getValue().getTitle() + ". Author: " + entry.getValue().getAuthor());
        }
    }
    //endregion

    protected UserIdea getUserIdea() {
        return userIdea;
    }
}
