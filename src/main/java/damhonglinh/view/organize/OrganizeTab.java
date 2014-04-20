package damhonglinh.view.organize;

import damhonglinh.model.Model;
import damhonglinh.model.UserIdea;
import damhonglinh.view.KulButton;
import damhonglinh.view.KulFileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class OrganizeTab extends JPanel {

    private Model model;
    private IdeaListView ideaListView;
    private JPanel center;
    private JScrollPane scroll;
    private LinkedList<UserIdeaBox> uibs;

    public OrganizeTab(Model model) {
        this.model = model;
        setBackground(Color.WHITE);

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        ideaListView = new IdeaListView(model, this);
        add(ideaListView, BorderLayout.WEST);

        center = new JPanel(null);
        center.setBackground(new Color(235, 235, 235));
        scroll = new JScrollPane(center);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        add(scroll);

        center.addMouseListener(new OrganizeTabMouseListener());

        KulButton save = new KulButton("Save", false);
        save.setBounds(5, 5, 60, 20);
        center.add(save);
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                save();
            }
        });

        KulButton load = new KulButton("Load", false);
        load.setBounds(70, 5, 60, 20);
        center.add(load);
        load.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                load();
            }
        });

        KulButton export = new KulButton("Export", false);
        export.setBounds(135, 5, 60, 20);
        center.add(export);
        export.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                export();
            }
        });

        uibs = new LinkedList<>();
    }

    //region save/load
    private File obtainFile(boolean loadFile) {
        KulFileChooser fileChooser = new KulFileChooser("reaid");
        int returnValue = (loadFile) ? fileChooser.showOpenDialog(this) : fileChooser.showDialog(this, "Save");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private void load() {
        File file = obtainFile(true);
        if (file == null) return;

        UserIdeaBoxWrapper[] wrappers = null;

        ObjectInputStream input = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            input = new ObjectInputStream(new FileInputStream(file));
            wrappers = (UserIdeaBoxWrapper[]) input.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error when loading file",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) { /* skip */ }
        }

        removeAllUserIdeaBoxes();

        for (int i = 0; i < wrappers.length; i++) {
            UserIdeaBoxWrapper wrapper = wrappers[i];
            if (model.getUserIdeas().containsKey(wrapper.getUserIdea().getId()))
                addNewUserIdeaBox(wrapper.getUserIdea(), wrapper.getRectangle().getLocation())
                        .setBounds(wrapper.getRectangle());
        }
    }

    private void save() {
        File file = obtainFile(false);
        if (file == null) return;

        UserIdeaBoxWrapper[] wrappers = new UserIdeaBoxWrapper[uibs.size()];
        int i = 0;
        for (UserIdeaBox uib : uibs) {
            UserIdeaBoxWrapper wrapper = new UserIdeaBoxWrapper(uib.getUserIdea(), uib.getBounds());
            wrappers[i] = wrapper;
            i++;
        }

        ObjectOutputStream output = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(wrappers);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (output != null) output.close();
            } catch (IOException e) { /* skip */ }
        }
    }
    //endregion

    private void export() {
        new ExportFrame(model, uibs).setVisible(true);
    }

    protected UserIdeaBox addNewUserIdeaBox(UserIdea ui, Point p) {
        UserIdeaBox uib = new UserIdeaBox(this, center, ui, p.x, p.y);
        uibs.add(uib);

        center.add(uib);
        center.repaint(uib.getBounds());
        return uib;
    }

    private class OrganizeTabMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (ideaListView.getActiveUserIdea() != null) {
                addNewUserIdeaBox(ideaListView.getActiveUserIdea(), e.getPoint());
            }
        }
    }

    protected void removeUserIdeaBox(UserIdeaBox uib) {
        this.remove(uib);
        uibs.remove(uib);
    }

    private void removeAllUserIdeaBoxes() {
        for (UserIdeaBox uib : uibs) {
            uib.setVisible(false);
            center.remove(uib);
        }
        center.repaint();

        uibs.clear();
    }

    protected Model getModel() {
        return model;
    }

    //region refreshUserIdeaList()
    public void refreshUserIdeaList() {
        ideaListView.refreshUserList();

        Iterator<UserIdeaBox> iter = uibs.iterator();
        while (iter.hasNext()) {
            UserIdeaBox uib = iter.next();
            if (!model.getUserIdeas().containsKey(uib.getUserIdea().getId())) {
                center.remove(uib);
                iter.remove();
            }
        }
    }
    //endregion
}
