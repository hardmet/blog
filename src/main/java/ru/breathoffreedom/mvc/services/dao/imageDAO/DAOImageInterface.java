package ru.breathoffreedom.mvc.services.dao.imageDAO;

import ru.breathoffreedom.mvc.models.ImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface describe main methods to work with Images data
 */
public interface DAOImageInterface {

    List findAllImages();

    List findImagesByPostId(int postId);

    ImageModel findImageById(int imageId);

    boolean updatePathToImages(ArrayList<Integer> imageIds, String path);

    int[] insertImages(int postId, String path, int numberOfImages);

    boolean deleteImagesByPostId(int idPost);

    boolean deleteImagesByPath(String path);

    boolean deleteImageById (int imageId);
}
