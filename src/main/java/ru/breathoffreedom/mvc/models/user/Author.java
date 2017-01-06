package ru.breathoffreedom.mvc.models.user;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "author")
public class Author {

    private int id;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthday;
    private String email;
    private String password;
    private  boolean enabled;

    public Author() {}

    public Author(String firstName, String lastName, String nickName,
                  Date birthday, String email, String password, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false, unique = true)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TIMESTAMP")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(unique = true, nullable = false)
    @Size(max = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    @Size(min = 32, max = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(columnDefinition = "boolean default false", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
