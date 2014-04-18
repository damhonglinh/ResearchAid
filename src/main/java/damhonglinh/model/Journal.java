package damhonglinh.model;

import java.io.Serializable;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class Journal implements Serializable{

    private static int curId = 1;
    private int id;
    private String title;
    private String author;
    private String reference;

    public Journal(String title, String author) {
        id = curId++;
        this.title = title;
        this.author = author;
    }

    public static int getCurId() {
        return curId;
    }

    public static void setCurId(int curId) {
        Journal.curId = curId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
