package ru.breathoffreedom.mvc.services.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.breathoffreedom.mvc.models.blog.Comment;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.blog.filter.*;
import ru.breathoffreedom.mvc.services.blog.repository.AuthorRepository;
import ru.breathoffreedom.mvc.services.blog.repository.CommentRepository;
import ru.breathoffreedom.mvc.services.blog.repository.PostRepository;
import ru.breathoffreedom.mvc.services.image.ImageRepository;
import ru.breathoffreedom.mvc.services.image.service.ImageService;

import java.util.List;

/**
 * Created by boris_azanov on 26.11.16.
 */
@Service
public class BlogServiceImpl implements BlogService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private AuthorRepository authorRepository;

    private ImageService imageService;

    @Autowired
    public BlogServiceImpl(PostRepository postRepository, CommentRepository commentRepository,
                           AuthorRepository authorRepository, ImageService imageService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
        this.imageService = imageService;
    }

    @Override
    public Comment getComment(int commentId) {
        return commentRepository.findOne(commentId);
    }

    @Override
    public List<Comment> getComments(Post post) {
        CommentFilter filter = new CommentFilter();
        filter.setPost(post);
        return getComments(filter);
    }

    @Override
    public List<Comment> getComments(CommentFilter filter) {
        return commentRepository.findAll(CommentSpec.getSpec(filter));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void removeComment(int commentId) {
        commentRepository.delete(commentId);
    }

    @Override
    public void removeComments(Post post) {
        commentRepository.delete(getComments(post));
    }

    @Override
    public List<Post> getPosts(Author author) {
        PostFilter filter = new PostFilter();
        filter.setAuthor(author);
        return getPosts(filter);
    }

    @Override
    public List<Post> getPosts(PostFilter filter) {
        return postRepository.findAll(PostSpec.getSpec(filter));
    }

    @Override
    public Post getPost(int id) {
        return postRepository.findOne(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void removePost(int postId) {
        imageService.removePostImages(postId);
        commentRepository.deleteByPostId(postId);
        postRepository.delete(postId);
    }

    @Override
    public void removePosts(Author author) {
        imageService.removePostImagesByAuthor(author);
        postRepository.delete(getPosts(author));
    }


    @Override
    public Author getAuthor(int id) {
        return authorRepository.findOne(id);
    }

    @Override
    public List<Author> getAuthors(AuthorFilter filter) {
        return authorRepository.findAll(AuthorSpec.getSpec(filter));
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void removeAuthor(int authorId) {
        commentRepository.deleteByAuthorId(authorId);
        imageService.removePostImagesByAuthorId(authorId);
        postRepository.deleteByAuthorId(authorId);
        authorRepository.delete(authorId);
    }
}
