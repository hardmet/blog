package ru.breathoffreedom.mvc.models.blog;


import org.springframework.format.annotation.DateTimeFormat;
import ru.breathoffreedom.mvc.models.user.Author;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "post")
public class Post {

    private int id;
    private Author author;
    private Date published = new Date();
    private String title;
    private String subtitle;
    private String text;
    private boolean hasMainImage;
    private boolean isVisible;

    public Post() {
    }

    public Post(Author author, String title, String subtitle, String text) {
        this.author = author;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    public Author getAuthor() {
        return author;
    }

    @Column(columnDefinition = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getPublished() {
        return published;
    }

    @Size(min = 1, max = 128)
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    @Size(min = 1, max = 256)
    @Column(nullable = false)
    public String getSubtitle() {
        return subtitle;
    }

    @Size(min = 1, max = 8192)
    @Column(nullable = false)
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

    @Column(name = "has_main_image", columnDefinition = "boolean default false", nullable = false)
    public boolean isHasMainImage() {
        return hasMainImage;
    }

    public void setHasMainImage(boolean hasMainImage) {
        this.hasMainImage = hasMainImage;
    }

    @Column(name = "isVisible", columnDefinition = "boolean default false", nullable = false)
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", author=" + author +
                ", published=" + published +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", text='" + text + '\'' +
                ", hasMainImage=" + hasMainImage +
                ", isVisible=" + isVisible +
                '}';
    }
}
