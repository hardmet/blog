package ru.breathoffreedom.mvc.services.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.breathoffreedom.mvc.models.blog.Comment;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.models.user.Role;
import ru.breathoffreedom.mvc.services.blog.filter.AuthorFilter;
import ru.breathoffreedom.mvc.services.blog.filter.CommentFilter;
import ru.breathoffreedom.mvc.services.blog.filter.PostFilter;

import java.util.List;

/**
 * Created by boris_azanov on 26.11.16.
 */
public interface BlogService {

    Comment getComment(int commentId);

    List<Comment> getComments(Post post);

    List<Comment> getComments(CommentFilter filter);

    Comment save(Comment comment);

    Comment addComment(Comment comment, int postId);

    void removeComment(int commentId);

    void removeComments(Post post);


    List<Post> getPosts(Author author);

    List<Post> getPosts(PostFilter filter);

    Page<Post> getPosts(PostFilter filter, Pageable page);

    Post getPost(int id);

    Post save(Post post);

    void removePost(int postId);

    void removePosts(Author author);


    Author getAuthor(int id);

    List<Author> getAuthors(AuthorFilter filter);

    Author save(Author author);

    void removeAuthor(int authorId);

    boolean hasAuthorWithEmail(String email);

    boolean hasAuthorWithNickName(String nickname);

    int setAuthorRole(Author author, Role role);

    Author getAuthor(String email);

}
