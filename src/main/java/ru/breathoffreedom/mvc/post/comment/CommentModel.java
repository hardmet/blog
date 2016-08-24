package ru.breathoffreedom.mvc.post.comment;


import org.hibernate.validator.constraints.NotEmpty;
import ru.breathoffreedom.mvc.post.Post;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "COMMENT")
public class CommentModel implements Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NotEmpty
    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "COMMENT_DATE")
    private Date date;

    @NotEmpty
    @Size(min = 5, max = 3000)
    @Column(name = "TEXT")
    private String text;

    @Column(name = "POST_ID")
    private int post;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPost() {
        return post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPost(int post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", post_id='" + post + '\'' +
                '}';
    }

    public CommentModel(String author, String text, int post) {
        this.author = author;
        this.text = text;
        this.post = post;
    }

    public CommentModel() {}
}
