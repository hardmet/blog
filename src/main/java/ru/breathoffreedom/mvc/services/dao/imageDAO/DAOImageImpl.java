package ru.breathoffreedom.mvc.services.dao.imageDAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.ImageModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is implement process of data wired with ImageModels using Hibernate ORM.
 */
@Repository
public class DAOImageImpl implements DAOImageInterface {

    /**
     * this context described on applicationContext.xml
     * this manager is Hibernate EntityManager
     */
    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    private DAOImageImpl() {
    }

    /**
     * finds all images at the IMAGE table in Data Base
     * @return - List of all images in Data Base table IMAGE
     */
    @Override
    public List<ImageModel> findAllImages() {
        System.out.println("DAOImageImpl findAllImages called!");
        return entityManager.createQuery("from ImageModel order by id asc", ImageModel.class)
                .getResultList();
    }

    /**
     * finds all Images which are related with one POST by Id of Post
     * @param postId - id of POST
     * @return - List of all images related with it POST
     */
    @Override
    public List findImagesByPostId(int postId) {
        System.out.println("DAOImageImpl findImagesByPostId called!");
        return entityManager.createQuery(
                "from ImageModel as image where image.postId = :postId")
                .setParameter("postId", postId)
                .getResultList();
    }

    /**
     * Finds image using id of Image. Searching at the IMAGE table at the Data Base
     * @param imageId - id of needed Image
     * @return ImageModel of Image from Data Base
     */
    @Override
    public ImageModel findImageById(int imageId) {
        System.out.println("DAOImageImpl findImageById called!");
        return entityManager.find(ImageModel.class, imageId);
    }

    /**
     * updates some paths to images with id which contained at the imageIds
     * @param imageIds - array of Ids what needed to change path
     * @param changedPath - new path of images
     * @return - result of updating
     */
    @Override
    @Transactional
    public boolean updatePathToImages(ArrayList<Integer> imageIds, String changedPath) {
        System.out.println("DAOImageImpl updatePathToImages called!");
        List allImages = entityManager.createQuery("from ImageModel order by id asc", ImageModel.class)
                .getResultList();
        for (Object imageToUpdate : allImages) {
            ImageModel image = (ImageModel) imageToUpdate;
            if (imageIds.contains(image.getId())) {
                image.setPathToImage(changedPath);
            }
        }
        entityManager.flush();
        return true;
    }

    /**
     * inserts new Images to the Data Base IMAGE table
     * @param postId - id of post related with Image
     * @param path - path to Image File
     * @param numberOfImages - number of images which related with this id post
     * @return - array of ids in IMAGE table of inserted Image files
     */
    @Override
    @Transactional
    public int[] insertImages(int postId, String path, int numberOfImages) {
        System.out.println("DAOImageImpl insertImages called!");
        int[] imageIds = new int[numberOfImages];
        for (int i = 0; i < numberOfImages; i++) {
            ImageModel image = new ImageModel(postId, path);
            entityManager.persist(image);
            imageIds[i] = image.getId();
        }
        entityManager.flush();
        return imageIds;
    }

    /**
     * deleting all images what related with postId
     * NOTE! this method doesn't delete files from file system
     * @param postId - id of post
     * @return - result of deleting images from Data Base
     */
    @Override
    @Transactional
    public boolean deleteImagesByPostId(int postId) {
        System.out.println("DAOImageImpl deleteImagesByPostId called!");
        List imagesToRemove = entityManager.createQuery(
                "from ImageModel as image where image.postId = :postId")
                .setParameter("postId", postId)
                .getResultList();
        for (Object image : imagesToRemove) {
            entityManager.remove(image);
        }
        entityManager.flush();
        return true;
    }

    /**
     * deleting all images what related with path from Data Base
     * NOTE! this method doesn't delete files from file system
     * @param path - where images were saved on File System,
     *             this path is relative path from root directory of VFS
     * @return - result of deleting images
     */
    @Override
    @Transactional
    public boolean deleteImagesByPath(String path) {
        System.out.println("DAOImageImpl deleteImagesByPath called!");
        List imagesToRemove = entityManager.createQuery(
                "from ImageModel as image where image.pathToImage = :path")
                .setParameter("path", path)
                .getResultList();
        for (Object image : imagesToRemove) {
            entityManager.remove(image);
        }
        entityManager.flush();
        return true;
    }

    /**
     * this method delete image from Data Base using image ID
     * NOTE! this method doesn't delete files from file system
     * @param imageId - id in the Data Base
     * @return - result of deleting
     */
    @Override
    @Transactional
    public boolean deleteImageById (int imageId) {
        System.out.println("DAOImageImpl deleteImageById called!");
        entityManager.remove(entityManager.find(ImageModel.class, imageId));
        entityManager.flush();
        return true;
    }
}
