package ru.breathoffreedom.mvc.services.image;

import ru.breathoffreedom.mvc.models.blog.Post;

/**
 * Created by boris_azanov on 27.11.16.
 */
public class ImageFilter {
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
