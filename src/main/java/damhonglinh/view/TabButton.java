package damhonglinh.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class TabButton extends Box {

    private MainFrame mainFrame;
    private Color color1 = new Color(222, 184, 135);
    private Color color2 = new Color(20, 208, 255);
    private Color color3 = new Color(216, 191, 216);
    private Color lineColor = color1;
    private KulButton organizeBut;
    private KulButton ideaBut;
    private KulButton journalBut;

    private int w = 135;
    private int h = 40;
    private int wStrut = 20;
    private int x1 = wStrut;
    private int y1 = h + 15;
    private int x2 = wStrut + w;
    private Timer timer;


    public TabButton(MainFrame mainFrame) {
        super(BoxLayout.X_AXIS);
        this.mainFrame = mainFrame;

        init();
        addKeyListenerForGUI();
        addMouseListener();
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new MatteBorder(0, 0, 2, 0, Color.WHITE)));
    }

    private void init() {
        setPreferredSize(new Dimension(5000, h + 23));

        organizeBut = new KulButton("Organize", false);
        organizeBut.setMouseOveredBorder(new LineBorder(new Color(255, 231, 193)));
        initButton(organizeBut, color1);

        ideaBut = new KulButton("Ideas", false);
        ideaBut.setMouseOveredBorder(new LineBorder(new Color(255, 231, 193)));
        initButton(ideaBut, color2);

        journalBut = new KulButton("Journals", false);
        journalBut.setMouseOveredBorder(new LineBorder(new Color(255, 231, 193)));
        initButton(journalBut, color3);

        add(Box.createHorizontalStrut(wStrut));
        add(organizeBut);
        add(Box.createHorizontalStrut(wStrut));
        add(ideaBut);
        add(Box.createHorizontalStrut(wStrut));
        add(journalBut);
    }

    //<editor-fold defaultstate="collapsed" desc="convenient method">
    private void initButton(KulButton but, Color color) {
        but.setPreferredSize(new Dimension(w, h));
        but.setMaximumSize(new Dimension(w, h));
        but.setFont(new Font("Arial", 0, 16));
        but.setOpaque(true);
        but.setBackground(color);
        but.setDefaultColor(new Color(255, 231, 193));
    }
    //</editor-fold>

    private void addKeyListenerForGUI() {
        organizeBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x1Des = wStrut;
                int x2Des = w + wStrut;
                moveLineWithAnimation(1, x1Des, x2Des);
            }
        });

        ideaBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x1Des = 2 * wStrut + w;
                int x2Des = 2 * (w + wStrut);
                moveLineWithAnimation(2, x1Des, x2Des);
            }
        });


        journalBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x1Des = 3 * wStrut + 2 * w;
                int x2Des = 3 * (w + wStrut);
                moveLineWithAnimation(3, x1Des, x2Des);
            }
        });
    }

    private void moveLineWithAnimation(final int desBut, final int x1Des, final int x2Des) {
        int distance = x1Des - x1;
        int loopTimes = 39;
        int delay = 2;
        if (Math.abs(distance) > 350 && Math.abs(distance) < 500) {
            loopTimes = 49;
            delay = 1;
        } else if (Math.abs(distance) >= 500) {
            loopTimes = 79;
            delay = 1;
        }

        final int dividor = loopTimes;
        final float d = distance / dividor;

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(delay, new ActionListener() {
            private int count = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= dividor + 9) {
                    x1 = x1Des;
                    x2 = x2Des;
                    TabButton.this.repaint();
                    timer.stop();
                    return;
                } else if (count == dividor + 1) {
                    timer.setDelay(timer.getDelay() + 3);
                } else if (count == dividor / 2) {
                    switch (desBut) {
                        case 1:
                            lineColor = color1;
                            break;
                        case 2:
                            lineColor = color2;
                            break;
                        default:
                            lineColor = color3;
                    }
                    x1 = x1 + (int) d;
                    x2 = x2 + (int) d;
                } else {
                    x1 = x1 + (int) d;
                    x2 = x2 + (int) d;
                }
                count++;
                TabButton.this.repaint();
            }
        });
        timer.start();
    }

    //<editor-fold defaultstate="collapsed" desc="mouse listeners">
    private void addMouseListener() {
        organizeBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainFrame.showOrganizeTab();
            }
        });

        ideaBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainFrame.showIdeaTab();
            }
        });

        journalBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainFrame.showJournalTab();
            }
        });
    }
    //</editor-fold>

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(lineColor);
        g.fillRect(x1 - 1, y1, x2 - x1 + 2, 4);

    }
}
