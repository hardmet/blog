package ru.breathoffreedom.mvc.models.blog;


import ru.breathoffreedom.mvc.models.user.Author;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    private int id;
    private Author author;
    private Date published = new Date();
    private String text;
    private Post post;

    public Comment(Author author, String text, Post post) {
        this.author = author;
        this.text = text;
        this.post = post;
    }

    public Comment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Column(columnDefinition = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    @Size(min = 3, max = 768)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    @JoinColumn(name = "post_id")
    public Post getPost() {
        return post;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", author_id='" + author + '\'' +
                ", published='" + published + '\'' +
                ", text='" + text + '\'' +
                ", post_id='" + post + '\'' +
                '}';
    }
}
