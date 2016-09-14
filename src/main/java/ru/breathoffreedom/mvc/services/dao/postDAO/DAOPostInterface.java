package ru.breathoffreedom.mvc.services.dao.postDAO;

import ru.breathoffreedom.mvc.models.PostModel;

import java.util.List;

public interface DAOPostInterface {

    List<PostModel> findAllPosts();

    PostModel findPostById(int id);

    boolean updatePost(int postId, String title, String subtitle, String text);

    int insertPost(String author, String title, String subtitle, String text);

    boolean deletePost(int idPost);

    long getLastId();

}
