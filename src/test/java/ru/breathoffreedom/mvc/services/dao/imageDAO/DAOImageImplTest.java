package ru.breathoffreedom.mvc.services.dao.imageDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.ImageModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is class for test DAOImageImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated method contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class DAOImageImplTest {

//    @PersistenceContext(name = "dataSource")
//    private EntityManager entityManager;

    @Autowired
    private DAOImageInterface daoImageService;

    private int imageId;
    private int postId;
    private String path;
    private String[] images;
    private ArrayList<Integer> imageIds;

    @Before
    public void setUp() throws Exception {
        imageId = 3;
        postId = 1;
        path = "root/book/etc";
        images = new String[]{"root/book/etc", "root/book/etc2", "root/book/etc3",
                "root/book/etc4", "root/book/etc5"};
        imageIds = new ArrayList<>();
        imageIds.add(2);
        imageIds.add(4);
        imageIds.add(5);
    }

    @After
    public void tearDown() throws Exception {
        imageId = 0;
        postId = 0;
        path = null;
        imageIds = null;
        images = null;
    }

    @Test
    public void findAllImagesTest() {
        System.out.println("Test findAllImagesTest start");
        List allImages = daoImageService.findAllImages();
        assert allImages.size() != 0;
    }

    @Test
    public void findImagesByPostIdTest() {
        System.out.println("Test findImagesByPostIdTest start");
        int numberOfImages = 3;
        int postIdForTest = 3;
        daoImageService.insertImages(postIdForTest, path, 3);
        List allImages = daoImageService.findImagesByPostId(postIdForTest);
        assert numberOfImages == allImages.size();
        for (int i = 0; i < numberOfImages; i++) {
            assert (((ImageModel) allImages.get(i)).getPathToImage().equals(path));
        }
        assert daoImageService.deleteImagesByPostId(postIdForTest);
    }

    @Test
    public void findImageByIdTest() {
        System.out.println("Test findImageById startTest");
        ImageModel image = daoImageService.findImageById(imageId);
        assert image != null;
        assert image.getPathToImage() != null;
    }

    @Test
    @Transactional
    public void updatePathToImagesTest() {
        System.out.println("Test updatePathToImagesTest start");
        List<ImageModel> imagesToUpdate = new ArrayList<>();
        for (Integer imageToUpdateId : imageIds) {
            imagesToUpdate.add(daoImageService.findImageById(imageToUpdateId));
        }
        String startPath = imagesToUpdate.get(0).getPathToImage();
        assert daoImageService.updatePathToImages(imageIds, path);
        assert daoImageService.updatePathToImages(imageIds, startPath);
    }

    @Test
    @Transactional
    public void insertAndDeleteImagesByPostIdTest() {
        System.out.println("Test insertImagesTest start");
        int[] idsOfInsertedImages = daoImageService.insertImages(postId, path, images.length);
        for (int id : idsOfInsertedImages) {
            assert daoImageService.findImageById(id) != null;
            assert daoImageService.deleteImageById(id);
        }
    }

    @Test
    @Transactional
    public void deleteImagesByPathTest() {
        System.out.println("Test deleteImagesByPathTest start");
        int numberOfRemovedImages = 4;
        String pathToRemove = path + "/folder1";
        int[] idsOfInsertedImages = daoImageService
                .insertImages(postId, pathToRemove, numberOfRemovedImages);
        for (int id : idsOfInsertedImages) {
            assert daoImageService.findImageById(id) != null;
        }
        daoImageService.deleteImagesByPath(pathToRemove);
        for (int id : idsOfInsertedImages) {
            assert daoImageService.findImageById(id) == null;
        }
    }
}