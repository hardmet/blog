package ru.breathoffreedom.mvc.post;


import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "POST")
public class PostModel implements Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NotEmpty
    @Size(min = 5, max = 20)
    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "POST_DATE")
    private Date date;

    @NotEmpty
    @Size(min = 1, max = 30)
    @Column(name = "TITLE")
    private String title;

    @NotEmpty
    @Size(min = 1, max = 50)
    @Column(name = "SUBTITLE")
    private String subtitle;

    @NotEmpty
    @Size(min = 5, max = 10000)
    @Column(name = "TEXT")
    private String text;


    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
