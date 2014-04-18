package damhonglinh.view.useridea;

import damhonglinh.model.Journal;
import damhonglinh.model.JournalIdea;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        center.add(journalIdeaArea);

        JLabel title = new JLabel("Supporting Ideas");
        title.setFont(new Font("Arial", 0, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(10, 0, 0, 0));
        center.add(title, BorderLayout.NORTH);

        drawAddNewArea();
    }

    //region drawAddNewArea()
    private void drawAddNewArea() {
        addNewArea = new Box(BoxLayout.Y_AXIS);
        addNewArea.setBorder(new CompoundBorder(new MatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
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

        final JTextArea text = createTextArea(line2, "");
        text.setToolTipText("Quotation text");

        line2.add(Box.createHorizontalStrut(3));
        line2.add(createArrowIcon());
        line2.add(Box.createHorizontalStrut(3));

        final JTextArea implication = createTextArea(line2, "");
        implication.setToolTipText("Your ideas that the text implies");

        KulButton add = new KulButton("Add", false);
        add.setPreferredSize(new Dimension(60, 25));
        add.setMaximumSize(new Dimension(60, 25));

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
                } else {
                    model.createJournalIdea(journal, text.getText(), implication.getText());
                }
            }
        });
        //endregion
    }
    //endregion

    //region drawAllJournalIdeas()
    private void drawAllJournalIdeas(UserIdea ui) {
        journalIdeaArea.removeAll();

        HashMap<Integer, JournalIdea> journalIdeas = model.getJournalIdeas();
        Iterator<Map.Entry<Integer, JournalIdea>> iter = journalIdeas.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, JournalIdea> entry = iter.next();
            JournalIdea ji = entry.getValue();

            Iterator<Map.Entry<Integer, UserIdea>> ideas = ji.getUserIdeas().entrySet().iterator();
            while (ideas.hasNext()) {
                UserIdea uiInLoop = ideas.next().getValue();
                if (uiInLoop.getId() == ui.getId()) {
                    drawJournalIdeaLine(ji);
                }
            }
        }
    }
    //endregion

    private void drawJournalIdeaLine(final JournalIdea ji) {
//        int journalIdeaAreaWidth = journalIdeaArea.getWidth();
        Box line = new Box(BoxLayout.X_AXIS);

        final JTextArea text = createTextArea(line, ji.getText());
//        text.setBackground(Color.WHITE);
//        text.setEditable(false);
//        text.setPreferredSize(new Dimension(journalIdeaAreaWidth * 2 / 5, 80));
//        text.setMaximumSize(new Dimension(journalIdeaAreaWidth * 2 / 5, 80));

        final JTextArea implication = createTextArea(line, ji.getImplication());
//        text.setBackground(Color.WHITE);
//        text.setEditable(false);
//        text.setPreferredSize(new Dimension(journalIdeaAreaWidth * 2 / 5, 80));
//        text.setMaximumSize(new Dimension(journalIdeaAreaWidth * 2 / 5, 80));

        KulButton save = new KulButton("Save", false);
        save.setPreferredSize(new Dimension(70, 30));
        save.setMaximumSize(new Dimension(70, 30));

        KulButton addTag = new KulButton("Add tag", false);
        addTag.setPreferredSize(new Dimension(100, 30));
        addTag.setMaximumSize(new Dimension(100, 30));

//        line.add(text);
//        line.add(implication);
        line.add(save);
        line.add(addTag);
    }

    //region helper method
    private JTextArea createTextArea(JComponent parent, String text) {
        JTextArea textArea = new JTextArea(text, 4, 10);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(210, 210, 210), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        parent.add(scroll);

        return textArea;
    }

    private JLabel createArrowIcon() {
        URL imageUrl = this.getClass().getClassLoader().getResource("arrow.png");
        ImageIcon icon = new ImageIcon(imageUrl);

        Image img1 = icon.getImage();
        Image newImg1 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(newImg1));

        return image;
    }
    //endregion

    protected void showJournalIdea(UserIdea ui) {
        System.out.println("Show idea detailss");
        drawAllJournalIdeas(ui);
    }

    private void refresh() {

        refreshJournalComboBox();
    }

    //region refreshJournalComboBox
    private void refreshJournalComboBox() {
        journals.removeAllItems();
        HashMap<Integer, Journal> allJournals = model.getJournals();
        Iterator<Map.Entry<Integer, Journal>> iter = allJournals.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, Journal> entry = iter.next();
            journals.addItem(entry.getValue().getTitle() + ". Author: " + entry.getValue().getAuthor());
        }
    }
    //endregion
}
