package ru.breathoffreedom.mvc.models.file;

import ru.breathoffreedom.mvc.models.blog.Post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This is model class for images uploaded from users
 */

@Entity
@Table(name = "image")
public class Image {

    private int id;
    private Post post;

    public Image(Post post) {
        this.post = post;
    }

    public Image() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
