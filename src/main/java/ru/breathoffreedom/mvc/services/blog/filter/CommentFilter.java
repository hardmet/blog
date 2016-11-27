package ru.breathoffreedom.mvc.services.blog.filter;

import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.user.Author;

import java.util.Date;

/**
 * Created by boris_azanov on 26.11.16.
 */
public class CommentFilter {
    private Author author;
    private Date published;
    private Post post;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
