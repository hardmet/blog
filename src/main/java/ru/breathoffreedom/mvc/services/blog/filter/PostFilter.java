package ru.breathoffreedom.mvc.services.blog.filter;

import ru.breathoffreedom.mvc.models.user.Author;

import java.util.Date;

/**
 * Created by boris_azanov on 26.11.16.
 */
public class PostFilter {
    private Author author;
    private Date published;
    private String title;
    private String subtitle;
    private Boolean isVisible;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Boolean isVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}
