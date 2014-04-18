package damhonglinh.view.journal;

import damhonglinh.model.Journal;
import damhonglinh.model.Model;
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
public class JournalListView extends JPanel {

    private JournalTab journalTab;
    private JLabel title = new JLabel("All Journals");
    private Model model;
    private JScrollPane scroll;
    private Box center;
    private KulButton addBut;
    private int width;

    public JournalListView(Model model, JournalTab journalTab) {
        this.journalTab = journalTab;
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
                addNewJournal();
            }
        });

        add(title, BorderLayout.NORTH);
        add(scroll);
        add(addBut, BorderLayout.SOUTH);

        drawAllJournals();
    }

    private void drawAllJournals() {
        HashMap<Integer, Journal> journals = model.getJournals();
        Iterator<Map.Entry<Integer, Journal>> iter = journals.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, Journal> entry = iter.next();

            final Journal j = entry.getValue();
            final JTextArea label = createTextArea(center, j.getTitle() + ". Author: " + j.getAuthor());

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    journalTab.showJournalIdea(j);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setBorder(new LineBorder(new Color(230, 230, 230)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setBorder(new EmptyBorder(1, 1, 1, 1));
                }
            });
        }
    }

    //region helper method
    private JTextArea createTextArea(JComponent parent, String text) {
        JTextArea textArea = new JTextArea(text, 3, 10);

        textArea.setEditable(false);
//        textArea.setBorder(new EmptyBorder(1, 1, 1, 1));

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setBorder(null);

        parent.add(scroll);

        return textArea;
    }
    //endregion

    private void addNewJournal() {
        new NewJournalFrame(model).setVisible(true);
    }

    protected void refresh() {

    }
}
