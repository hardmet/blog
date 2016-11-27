package ru.breathoffreedom.mvc.services.image;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.blog.filter.PostFilter;
import ru.breathoffreedom.mvc.services.blog.filter.PostSpec;
import ru.breathoffreedom.mvc.services.blog.repository.PostRepository;
import ru.breathoffreedom.mvc.services.image.service.ImageService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is class for test DAOImageImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated method contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private PostRepository postRepository;

    private Author firstAuthor;
    private Author secondAuthor;

    private Post firstPost;
    private Post secondPost;

    private ImageFilter filter;

    private Image firstImage;
    private Image secondImage;
    private Image thirdImage;
    private Image fourthImage;
    private List<Image> imagesList;
    private Image fifthImage;
    private Image sixthImage;
    private int id = 2;

    @Before
    public void setUp() throws Exception {
        firstAuthor = new Author();
        firstAuthor.setId(1);
        secondAuthor = new Author();
        secondAuthor.setId(2);

        firstPost = new Post();
        firstPost.setId(1);
        secondPost = new Post();
        secondPost.setId(2);
        filter = new ImageFilter();
        filter.setPost(firstPost);

        firstImage = new Image();
        secondImage = new Image();
        thirdImage = new Image(firstPost);
        fourthImage = new Image();
        fifthImage = new Image(firstPost);
        sixthImage = new Image(secondPost);

        imagesList = new ArrayList<>();
        imagesList.add(firstImage);
        imagesList.add(secondImage);
        imagesList.add(fourthImage);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getImagesByPostTest() {
        List<Image> images = imageService.getImages(firstPost);
        testListImages(images);
    }

    @Test
    public void getImagesByFilterTest() {
        List<Image> images = imageService.getImages(filter);
        testListImages(images);
    }

    @Test
    public void getImageTest() {
        Image image = imageService.getImage(id);
        checkImage(id, image, true);
        assert image.getPost().getId() == firstPost.getId();
    }

    @Test
    @Transactional
    public void addImageAndUpdateTest() {
        createImage();
        updateImage();
        removeImageById();
    }

    @Test
    @Transactional
    public void addImages() {
        int countImages = imageService.getImages(secondPost).size();
        List<Image> afterAddImages = imageService.addImages(imagesList, 2);
        assert afterAddImages.size() > countImages;
    }

    @Test
    @Transactional
    public void removeImageByPostTest() {
        imageService.remove(secondPost);
        List<Image> images = imageService.getImages(secondPost);
        assert images.size() == 0;
    }

    @Test
    @Transactional
    public void removePostImagesTest() {
        imageService.removePostImages(1);
        List<Image> images = imageService.getImages(filter);
        assert images.size() == 0;
    }

    @Test
    @Transactional
    public void removePostImagesByAuthor() {
        imageService.removePostImagesByAuthor(firstAuthor);
        checkRemovingListImages(firstAuthor);
    }

    @Test
    @Transactional
    public void removePostImagesByAuthorId() {
        imageService.removePostImagesByAuthorId(2);
        checkRemovingListImages(secondAuthor);
    }

    @Transactional
    private void createImage() {
        int id = thirdImage.getId();
        assert id == 0;
        thirdImage = imageService.save(thirdImage);
        checkImage(id, thirdImage, false);
        assert thirdImage.getPost().getId() == firstPost.getId();
    }

    @Transactional
    private void updateImage() {
        Image image = imageService.getImage(id);
        checkImage(id, image, true);
        int postId = image.getPost().getId();
        assert postId != 0;

        image.setPost(secondPost);
        image = imageService.save(image);
        assert image.getPost().getId() != postId;

        image.setPost(firstPost);
        image = imageService.save(image);
        checkImage(id, image, true);
        assert image.getPost().getId() == postId;
    }

    @Transactional
    private void removeImageById() {
        int thirdImageId = thirdImage.getId();
        Image image = imageService.getImage(thirdImageId);
        checkImage(thirdImageId, image, true);
        imageService.remove(thirdImageId);
        assert imageService.getImage(thirdImageId) == null;
    }

    private void testListImages(List <Image> images) {
        assert images != null;
        assert images.size() > 0;
        for (Image image : images) {
            assert image.getId() != 0;
        }
    }

    @Transactional
    private void checkImage(int imageId, Image image, boolean isEqual) {
        assert image != null;
        if (isEqual) {
            assert image.getId() == imageId;
        } else {
            assert image.getId() != imageId;
        }
    }

    @Transactional
    private void checkRemovingListImages(Author author) {
        PostFilter postFilter = new PostFilter();
        postFilter.setAuthor(author);
        List<Post> posts = postRepository.findAll(PostSpec.getSpec(postFilter));
        for (Post post:posts) {
            assert imageService.getImages(post).size() == 0;
        }
    }
}