package damhonglinh.view.journal;

import damhonglinh.model.Model;
import damhonglinh.view.KulButton;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class NewJournalFrame extends JFrame {

    private Box container;
    private JLabel titleTitle;
    private JLabel authorTitle;
    private JLabel referenceTitle;
    private JTextField titleLabel;
    private JTextField author;
    private JTextField reference;
    private KulButton add;
    private Model model;

    public NewJournalFrame(Model model) {
        this.model = model;
        setSize(new Dimension(600, 170));
        setUndecorated(true);
        setResizable(false);
        setTitle("New Journal");
        setLocationRelativeTo(null);

        container = new Box(BoxLayout.Y_AXIS);
        container.setOpaque(true);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 184, 135), 4),
                new LineBorder(Color.WHITE, 10)));
        container.setBackground(Color.WHITE);
        add(container);

        titleTitle = createTitleLabel("Title: ");
        titleLabel = new JTextField(5);
        Box titleBox = new Box(BoxLayout.X_AXIS);
        titleBox.add(titleTitle);
        titleBox.add(titleLabel);

        authorTitle = createTitleLabel("Author: ");
        author = new JTextField(5);
        Box authorBox = new Box(BoxLayout.X_AXIS);
        authorBox.add(authorTitle);
        authorBox.add(author);

        referenceTitle = createTitleLabel("Reference: ");
        reference = new JTextField(5);
        Box referenceBox = new Box(BoxLayout.X_AXIS);
        referenceBox.add(referenceTitle);
        referenceBox.add(reference);

        add = new KulButton("Add", false);
        add.setFont(new Font("Arial", 0, 14));
        add.setPreferredSize(new Dimension(100, 30));
        add.setMaximumSize(new Dimension(100, 30));
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                add();
            }
        });

        container.add(titleBox);
        container.add(Box.createVerticalStrut(10));
        container.add(authorBox);
        container.add(Box.createVerticalStrut(10));
        container.add(referenceBox);
        container.add(Box.createVerticalStrut(12));
        container.add(add);

        getRootPane().setFocusable(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewJournalFrame.this.setVisible(false);
                NewJournalFrame.this.dispose();
            }
        });
    }

    //region convenient method
    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", 0, 14));
        label.setPreferredSize(new Dimension(80, 20));
        label.setMaximumSize(new Dimension(80, 20));

        return label;
    }
    //endregion

    private void add() {
        if (!titleLabel.getText().isEmpty() && !author.getText().isEmpty()) {
            model.createJournal(titleLabel.getText(), author.getText(), reference.getText());
            model.updateJournalList();
        }
        NewJournalFrame.this.setVisible(false);
        NewJournalFrame.this.dispose();
    }
}
