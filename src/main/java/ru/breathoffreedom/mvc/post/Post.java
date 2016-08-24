package ru.breathoffreedom.mvc.post;


import java.util.Date;

public interface Post {

    String getAuthor();

    Date getDate();

    String getText();

    void setAuthor(String author);

    void setDate(Date date);

    void setText(String text);

}
