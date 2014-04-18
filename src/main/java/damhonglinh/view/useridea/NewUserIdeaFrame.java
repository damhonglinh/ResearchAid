package damhonglinh.view.useridea;

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
public class NewUserIdeaFrame extends JFrame {

    private Box container;
    private JLabel nameTitle;
    private JTextArea name;
    private KulButton add;
    private Model model;

    public NewUserIdeaFrame(Model model) {
        this.model = model;
        setSize(new Dimension(600, 250));
        setUndecorated(true);
        setResizable(false);
        setTitle("New Idea");
        setLocationRelativeTo(null);

        container = new Box(BoxLayout.Y_AXIS);
        container.setOpaque(true);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 184, 135), 4),
                new LineBorder(Color.WHITE, 10)));
        container.setBackground(Color.WHITE);
        add(container);

        nameTitle = new JLabel("Name: ");
        nameTitle.setFont(new Font("Arial", 0, 14));
        name = new JTextArea(3, 10);
        JScrollPane scroll = new JScrollPane(name);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        Box nameBox = new Box(BoxLayout.X_AXIS);
        nameBox.add(nameTitle);
        nameBox.add(scroll);

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

        container.add(nameBox);
        container.add(Box.createVerticalStrut(12));
        container.add(add);

        getRootPane().setFocusable(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewUserIdeaFrame.this.setVisible(false);
                NewUserIdeaFrame.this.dispose();
            }
        });
    }

    private void add() {
        if (!name.getText().isEmpty())
            model.createUserIdea(name.getText());
        NewUserIdeaFrame.this.setVisible(false);
        NewUserIdeaFrame.this.dispose();
    }
}
