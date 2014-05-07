package damhonglinh.view.organize;

import damhonglinh.model.Journal;
import damhonglinh.model.JournalIdea;
import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.KulButton;
import damhonglinh.view.KulFileChooser;
import damhonglinh.view.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by damho_000 on 4/20/14.
 */
public class ExportFrame extends JFrame {

    private Model model;
    private Box center;
    private LinkedList<UserIdeaBox> userIdeaBoxes;
    private JTextArea text;

    public ExportFrame(Model model, LinkedList<UserIdeaBox> userIdeaBoxes) {
        this.model = model;
        this.userIdeaBoxes = userIdeaBoxes;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Exporting Ideas");
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        setSize(new Dimension(1024, screenSize.height - screenInsets.bottom));
        setLocationRelativeTo(null);

        getRootPane().setFocusable(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportFrame.this.setVisible(false);
                ExportFrame.this.dispose();
            }
        });

        center = new Box(BoxLayout.Y_AXIS);
        add(center);

        text = Utils.createTextArea(center, "");
        drawTextArea();

        center.add(Box.createVerticalStrut(15));
        drawButtonLine();
        center.add(Box.createVerticalStrut(15));
    }

    private void drawTextArea() {
        Collections.sort(userIdeaBoxes, new Comparator<UserIdeaBox>() {
            @Override
            public int compare(UserIdeaBox o1, UserIdeaBox o2) {
                Point p1 = o1.getBounds().getLocation();
                Point p2 = o2.getBounds().getLocation();

                if (p1.y < p2.y) return -1;
                else if (p1.y > p2.y) return 1;
                else {
                    if (p1.x < p2.x) return -1;
                    else if (p1.x > p2.x) return 1;
                    else return 0;
                }
            }
        });

        StringBuilder allText = new StringBuilder();
        HashMap<Integer, Journal> usedJournal = new HashMap<>();

        for (UserIdeaBox uib : userIdeaBoxes) {
            UserIdea ui = uib.getUserIdea();
            allText.append("* " + ui.getName() + "\n");


            SortedMap<Integer, JournalIdea> keys = new TreeMap<>(model.getJournalIdeas());

            for (JournalIdea ji : keys.values()) {
                if (ji.getUserIdeas().containsKey(ui.getId())) {
                    String exactQuote = "";
                    if (!ji.getImplication().equals(ji.getText())) {
                        exactQuote = "\n\t\tExact text: >" + ji.getText() + "<";
                    }
                    allText.append("\t+ " + ji.getImplication()
                            + exactQuote + "(" + ji.getJournal().getAuthor() + ")\n");
                    usedJournal.put(ji.getJournal().getId(), ji.getJournal());
                }
            }

            allText.append("\n");
        }

        allText.append("----------------\nREFERENCES:\n");

        Iterator<Journal> iter = usedJournal.values().iterator();
        LinkedList<String> references = new LinkedList<>();
        while (iter.hasNext()) {
            Journal j = iter.next();
            if (!j.getReference().isEmpty()) {
                references.add(j.getReference());
            }
        }

        Collections.sort(references);
        for (String s : references) {
            allText.append(s + "\n");
        }

        text.setText(allText.toString());
    }

    private void drawButtonLine() {
        Box butLine = new Box(BoxLayout.X_AXIS);
        center.add(butLine);

        KulButton export = new KulButton("Export", false);
        export.setPreferredSize(new Dimension(80, 30));
        export.setMaximumSize(new Dimension(80, 30));

        butLine.add(export);
        export.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                export();
            }
        });
    }

    private void export() {
        KulFileChooser fileChooser = new KulFileChooser("txt");
        int returnValue = fileChooser.showDialog(this, "Export");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            DataOutputStream output = null;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                output = new DataOutputStream(new FileOutputStream(file));
                output.writeBytes(text.getText());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Error when exporting file",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            } finally {
                try {
                    if (output != null) output.close();
                } catch (IOException e) { /* skip */ }
            }
        }
    }
}
