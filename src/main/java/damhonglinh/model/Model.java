package damhonglinh.model;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class Model extends Observable {

    public static final String UPDATE_USER_IDEAS_LIST = "userIdeaList";
    public static final String UPDATE_JOURNALS_LIST = "journalList";
    public static final String UPDATE_JOURNAL_IDEAS_LIST = "journalIdeaList";
    public static final String APP_NAME = "Research Aid";
    private File dataFile;
    private HashMap<Integer, Journal> journals;
    private HashMap<Integer, JournalIdea> journalIdeas;
    private HashMap<Integer, UserIdea> userIdeas;

    public Model() {
        dataFile = new File(getDataDirectory(), "data.dat");
        loadData();
    }

    //region getDataDirectory()
    private String getDataDirectory() {
        String filepath = System.getProperty("user.home") + File.separator + APP_NAME + File.separator;
        File folder = new File(filepath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return filepath;
    }
    //endregion

    //region loadData()
    private void loadData() {
        ObjectInputStream input = null;
        try {
            FileInputStream fileInput = new FileInputStream(dataFile);
            input = new ObjectInputStream(fileInput);
            journals = (HashMap<Integer, Journal>) input.readObject();
            journalIdeas = (HashMap<Integer, JournalIdea>) input.readObject();
            userIdeas = (HashMap<Integer, UserIdea>) input.readObject();
            Journal.setCurId(input.readInt());
            JournalIdea.setCurId(input.readInt());
            UserIdea.setCurId(input.readInt());

        } catch (Exception e) {
            System.err.println(e.toString());
            journals = new HashMap<Integer, Journal>();
            journalIdeas = new HashMap<Integer, JournalIdea>();
            userIdeas = new HashMap<Integer, UserIdea>();
            Journal.setCurId(1);
            JournalIdea.setCurId(1);
            UserIdea.setCurId(1);
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) { /* skip */ }
        }
    }
    //endregion

    //region saveData();
    public void saveData() {
        ObjectOutputStream output = null;
        try {
            if (dataFile == null) {
                JOptionPane.showMessageDialog(null, "Fatal ERROR: file data not found!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            output = new ObjectOutputStream(new FileOutputStream(dataFile));
            output.writeObject(journals);
            output.writeObject(journalIdeas);
            output.writeObject(userIdeas);
            output.writeInt(Journal.getCurId());
            output.writeInt(JournalIdea.getCurId());
            output.writeInt(UserIdea.getCurId());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (output != null) output.close();
            } catch (IOException e) { /* skip */ }
        }
    }
    //endregion

    //region create new object methods
    public Journal createJournal(String title, String author, String reference) {
        Journal j = new Journal(title, author);
        j.setReference(reference);
        journals.put(j.getId(), j);
        saveData();

        updateJournalList();
        return j;
    }

    public JournalIdea createJournalIdea(Journal journal, String text, String implication) {
        JournalIdea ji = new JournalIdea(journal, text, implication);
        journalIdeas.put(ji.getId(), ji);
        saveData();

        updateJournalIdeasList();
        return ji;
    }

    public UserIdea createUserIdea(String name) {
        UserIdea ui = new UserIdea(name);
        userIdeas.put(ui.getId(), ui);
        saveData();

        updateUserIdeasList();
        return ui;
    }
    //endregion

    public void addJournalIdeaToUserIdea(UserIdea ui, JournalIdea ji) {
        ji.getUserIdeas().put(ui.getId(), ui);
        saveData();
        updateJournalIdeasList();
    }

    public void removeJournalIdeaFromUserIdea(UserIdea ui, JournalIdea ji) {
        ji.getUserIdeas().remove(ui.getId());
        saveData();
        updateJournalIdeasList();
    }

    public void deleteJournal(Journal j) {
        Iterator<Map.Entry<Integer, JournalIdea>> iter = journalIdeas.entrySet().iterator();
        while (iter.hasNext()) {
            JournalIdea ji = iter.next().getValue();
            if (ji.getJournal().getId() == j.getId()) {
                iter.remove();
            }
        }

        journals.remove(j.getId());
        saveData();

        updateJournalIdeasList();
        updateJournalList();
    }

    public void deleteUserIdea(UserIdea ui) {
        Iterator<Map.Entry<Integer, JournalIdea>> iterJI = journalIdeas.entrySet().iterator();
        while (iterJI.hasNext()) {
            JournalIdea ji = iterJI.next().getValue();
            if (ji.getUserIdeas().containsKey(ui.getId())) {
                ji.getUserIdeas().remove(ui.getId());
            }
        }

        userIdeas.remove(ui.getId());

        saveData();

        updateJournalIdeasList();
        updateUserIdeasList();
    }

    public void deleteJournalIdea(JournalIdea ji) {
        journalIdeas.remove(ji.getId());
        saveData();

        updateJournalIdeasList();
    }

    //region getters
    public HashMap<Integer, Journal> getJournals() {
        return journals;
    }

    public HashMap<Integer, JournalIdea> getJournalIdeas() {
        return journalIdeas;
    }

    public HashMap<Integer, UserIdea> getUserIdeas() {
        return userIdeas;
    }
    //endregion

    //region update methods
    public void updateJournalList() {
        setChanged();
        notifyObservers(UPDATE_JOURNALS_LIST);
    }

    public void updateUserIdeasList() {
        setChanged();
        notifyObservers(UPDATE_USER_IDEAS_LIST);
    }

    public void updateJournalIdeasList() {
        setChanged();
        notifyObservers(UPDATE_JOURNAL_IDEAS_LIST);
    }
    //endregion
}
