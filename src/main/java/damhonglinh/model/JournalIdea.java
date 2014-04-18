package damhonglinh.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class JournalIdea implements Serializable {

    private static int curId = 1;
    private int id;
    private Journal journal;
    private String text;
    private String implication;
    private HashMap<Integer, UserIdea> userIdeas;

    public JournalIdea(Journal journal, String text, String implication) {
        id = curId++;
        this.journal = journal;
        this.text = text;
        this.implication = implication;
    }

    public static int getCurId() {
        return curId;
    }

    public static void setCurId(int curId) {
        JournalIdea.curId = curId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImplication() {
        return implication;
    }

    public void setImplication(String implication) {
        this.implication = implication;
    }

    public HashMap<Integer, UserIdea> getUserIdeas() {
        return userIdeas;
    }

    public void setUserIdeas(HashMap<Integer, UserIdea> userIdeas) {
        this.userIdeas = userIdeas;
    }
}
