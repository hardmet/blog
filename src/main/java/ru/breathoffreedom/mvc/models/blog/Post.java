package ru.breathoffreedom.mvc.models.blog;


import javafx.beans.DefaultProperty;
import org.hibernate.annotations.ColumnDefault;
import ru.breathoffreedom.mvc.models.user.Author;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "post")
public class Post {

    private int id;
    private Author author;
    private Date published;
    private String title;
    private String subtitle;
    private String text;
    private Integer image;
    private boolean isVisible;

    public Post(Author author, String title, String subtitle, String text) {
        this.author = author;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }

    public Post() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    public Author getAuthor() {
        return author;
    }

    public Date getPublished() {
        return published;
    }

    @NotNull
    @Size(min = 1, max = 30)
    public String getTitle() {
        return title;
    }

    @NotNull
    @Size(min = 1, max = 50)
    public String getSubtitle() {
        return subtitle;
    }

    @NotNull
    @Size(min = 1, max = 50)
    public String getText() {
        return text;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    @Column(name = "visible", columnDefinition = "boolean default false", nullable = false)
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", published='" + published + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
