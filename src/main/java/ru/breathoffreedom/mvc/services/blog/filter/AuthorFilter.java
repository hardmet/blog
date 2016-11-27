package ru.breathoffreedom.mvc.services.blog.filter;

import java.util.Date;

/**
 * Created by boris_azanov on 26.11.16.
 */
public class AuthorFilter {

    private Date birthday;
    private String email;
    private  boolean enabled;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
