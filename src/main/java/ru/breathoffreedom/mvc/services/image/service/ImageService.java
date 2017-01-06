package ru.breathoffreedom.mvc.services.image.service;

import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.image.ImageFilter;
import ru.breathoffreedom.mvc.services.image.ImageFormat;

import java.util.List;

/**
 * Created by boris_azanov on 27.11.16.
 */
public interface ImageService {

    List<Image> getImages(Post post);

    List<Image> getImages(int postId);

    List<Image> getImages(ImageFilter filter);

    Image getImage(int id);

    List<Image> addImages(List<Image> images, int postId);

    Image save(Image image);

    void remove(int id);

    void remove(Post post);

    void removePostImages(int postId);

    void removePostImagesByAuthor(Author author);

    void removePostImagesByAuthorId(int authorId);

    List<String> getImagesFromFileSystem(ImageFormat[] formats, Post post);

    List<String> getImagesFromFileSystem(Post post);

    String getPostMainImage(int postId);

    String getImageFromFileSystem(Image image, ImageFormat format);
}
