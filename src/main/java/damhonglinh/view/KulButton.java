package damhonglinh.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class KulButton extends JButton {

    private Color defaultColor;
    private Color mouseOveredColor;
    private Color overColor;
    private Border defaultBorder;
    private Border mouseOveredBorder;
    private String textDisplay;
    private boolean isColorMode;
    private int moving;

    public KulButton(String textDisplay, Color defaultColor, Color mouseOveredColor, boolean isColorMode) {
        this.textDisplay = textDisplay;
        this.defaultColor = defaultColor;
        this.mouseOveredColor = mouseOveredColor;
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setFont(new Font("Arial", Font.PLAIN, 14));
        this.overColor = new Color(0, 0, 0, 0);
        this.isColorMode = isColorMode;

        mouseOveredBorder = new LineBorder(mouseOveredColor, 2);
        defaultBorder = new LineBorder(defaultColor, 1);

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(defaultBorder);

        addMouseListener(new Listener());
    }

    public KulButton() {
        this("", true);
    }

    public KulButton(String textDisplay, boolean isColorMode) {
        this(textDisplay, new Color(244, 164, 96), new Color(222, 184, 135), isColorMode);
    }

    //<editor-fold defaultstate="collapsed" desc="setters getters">
    public void setTextDisplay(String text) {
        this.textDisplay = text;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public void setDefaultBorder(Border defaultBorder) {
        this.defaultBorder = defaultBorder;
        setBorder(defaultBorder);
    }

    public void setMouseOveredBorder(Border mouseOveredBorder) {
        this.mouseOveredBorder = mouseOveredBorder;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
        defaultBorder = new LineBorder(defaultColor);
        setBorder(defaultBorder);
    }

    public void setMouseOveredColor(Color mouseOveredColor) {
        this.mouseOveredColor = mouseOveredColor;
        mouseOveredBorder = new LineBorder(mouseOveredColor, 1);
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public Color getMouseOveredColor() {
        return mouseOveredColor;
    }
    //</editor-fold>

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fm = g2d.getFontMetrics();

        int height = fm.getAscent() - fm.getDescent();
        int width = fm.stringWidth(textDisplay);

        // horizontal position of text
        int x;
        if (getHorizontalAlignment() == SwingConstants.CENTER) {
            x = getWidth() / 2 - width / 2 - moving;
        } else if (getHorizontalAlignment() == SwingConstants.RIGHT) {
            x = getWidth() - width - moving;
        } else {
            x = 1 - moving;
        }
        // vertical position of text
        int y;
        if (getVerticalAlignment() == SwingConstants.CENTER) {
            y = getHeight() / 2 + height / 2 - moving;
        } else if (getVerticalAlignment() == SwingConstants.TOP) {
            y = getHeight() - height - moving;
        } else {
            y = 1 - moving;
        }

        g2d.drawString(textDisplay, x, y);

        if (isColorMode) {
            g2d.setColor(overColor);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="listeners">
    private class Listener extends MouseAdapter {

        private boolean isMouseOvered;

        @Override
        public void mousePressed(MouseEvent e) {
            if (!isColorMode) {
                KulButton.this.setBorder(defaultBorder);
            } else {
                overColor = new Color(255, 160, 122, 90);
            }
            moving = -2;
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isMouseOvered) {
                if (!isColorMode) {
                    KulButton.this.setBorder(mouseOveredBorder);
                } else {
                    overColor = new Color(244, 164, 96, 40);
                }
            }
            moving = 0;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!isColorMode) {
                KulButton.this.setBorder(mouseOveredBorder);
            } else {
                overColor = new Color(244, 164, 96, 40);
            }
            isMouseOvered = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!isColorMode) {
                KulButton.this.setBorder(defaultBorder);
            } else {
                overColor = new Color(0, 0, 0, 0);
            }
            isMouseOvered = false;
        }
    }
    //</editor-fold>
}