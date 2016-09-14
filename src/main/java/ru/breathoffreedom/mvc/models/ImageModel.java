package ru.breathoffreedom.mvc.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This is model class for images uploaded from users
 */

@Entity
@Table(name = "Image")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "POST_ID")
    private int postId;

    @NotNull
    @Column(name = "PATH")
    private String pathToImage;

    public ImageModel(int postId, String pathToImage) {
        this.postId = postId;
        this.pathToImage = pathToImage;
    }

    public ImageModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }
}
