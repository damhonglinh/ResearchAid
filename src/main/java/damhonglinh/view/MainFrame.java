package damhonglinh.view;

import damhonglinh.model.Model;
import damhonglinh.view.journal.JournalTab;
import damhonglinh.view.useridea.IdeaTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class MainFrame extends JFrame {

    private OrganizeTab organizeTab;
    private IdeaTab ideaTab;
    private JournalTab journalTab;

    private JPanel center;
    private CardLayout cardCenter;

    public MainFrame() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(900, 600));
        setVisible(true);
        setTitle(Model.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setBackground(Color.WHITE);

        Model model = new Model();

        organizeTab = new OrganizeTab(model);
        ideaTab = new IdeaTab(model);
        journalTab = new JournalTab(model);

        cardCenter = new CardLayout();
        center = new JPanel(cardCenter);
        center.setBackground(Color.WHITE);
        add(center);
        center.add(organizeTab, "organizeTab");
        center.add(ideaTab, "ideaTab");
        center.add(journalTab, "journalTab");

        TabButton tabButton = new TabButton(this);
        add(tabButton, BorderLayout.NORTH);


        showIdeaTab();
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Exit?", "Exit?", JOptionPane.OK_CANCEL_OPTION);
                if (input == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

    }

    protected void showOrganizeTab() {
        cardCenter.show(center, "organizeTab");
    }

    protected void showIdeaTab() {
        cardCenter.show(center, "ideaTab");
    }

    protected void showJournalTab() {
        cardCenter.show(center, "journalTab");
    }
}
