package ru.breathoffreedom.mvc.services.image.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.image.ImageFilter;
import ru.breathoffreedom.mvc.services.image.ImageRepository;
import ru.breathoffreedom.mvc.services.image.ImageSpec;
import ru.breathoffreedom.mvc.services.vfs.VFS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris_azanov on 27.11.16.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;
    private VFS vfs;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> getImages(Post post) {
        ImageFilter filter = new ImageFilter();
        filter.setPost(post);
        return getImages(filter);
    }

    @Override
    public List<Image> getImages(ImageFilter filter) {
        return imageRepository.findAll(ImageSpec.getSpec(filter));
    }

    @Override
    public Image getImage(int id) {
        return imageRepository.findOne(id);
    }

    @Override
    public List<Image> addImages(List<Image> images, int postId) {
        Post post = new Post();
        post.setId(postId);
        List<Image> resultImages = new ArrayList<>();
        for (Image image:images) {
            image.setPost(post);
            resultImages.add(save(image));
        }
        return resultImages;
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void remove(int id) {
        imageRepository.delete(id);
    }

    @Override
    public void remove(Post post) {
        imageRepository.delete(getImages(post));
    }

    @Override
    public void removePostImages(int postId) {
        imageRepository.deleteByPostId(postId);
    }

    @Override
    public void removePostImagesByAuthor(Author author) {
        imageRepository.deleteByAuthorId(author.getId());
    }

    @Override
    public void removePostImagesByAuthorId(int authorId) {
        imageRepository.deleteByAuthorId(authorId);
    }
}
