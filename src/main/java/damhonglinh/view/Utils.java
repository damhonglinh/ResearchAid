package damhonglinh.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

/**
 * User: Dam Linh
 * Date: 19/04/14
 */
public class Utils {

    public static JTextArea createTextArea(JComponent parent, String text) {
        JTextArea textArea = new JTextArea(text, 4, 10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(210, 210, 210), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        parent.add(scroll);

        return textArea;
    }

    public static JLabel createArrowIcon() {
        URL imageUrl = Utils.class.getClassLoader().getResource("arrow.png");
        ImageIcon icon = new ImageIcon(imageUrl);

        Image img1 = icon.getImage();
        Image newImg1 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(newImg1));

        return image;
    }
}
