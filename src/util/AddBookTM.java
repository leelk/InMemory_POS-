package util;

import javafx.scene.control.Button;

import java.awt.*;

public class AddBookTM {
    private String bookID;
    private String title;
    private String author;
    private Button btnDelete;

    public AddBookTM(String bookID, String title, String author, Button btnDelete) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.btnDelete = btnDelete;
    }

    public AddBookTM(String bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
    }

    public AddBookTM() {
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
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

    public Button getBtnDelete() {
        return btnDelete;
    }

    @Override
    public String toString() {
        return "AddBookTM{" +
                "bookID='" + bookID + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", btnDelete=" + btnDelete +
                '}';
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }
}
