package ru.breathoffreedom.mvc.services.blog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.blog.Comment;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.blog.filter.AuthorFilter;
import ru.breathoffreedom.mvc.services.blog.filter.CommentFilter;
import ru.breathoffreedom.mvc.services.blog.filter.PostFilter;

import java.util.Date;
import java.util.List;


/**
 * Created by boris_azanov on 26.11.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class BlogServiceTest {

    private Author firstAuthor;
    private Author secondAuthor;

    private Post firstPost;
    private Post secondPost;

    private Comment firstComment;
    private Comment secondComment;
    private Comment thirdComment;

    private AuthorFilter authorFilter;
    private PostFilter postFilter;
    private CommentFilter commentFilter;

    @Autowired
    private BlogService blogService;

    @Before
    public void setUp() throws Exception {
        firstAuthor = new Author("Jhon", "Connor", "Saver",
                new Date(123456789L), "jhon@gmail.ru",
                "$2a$08$MGcfwEVo6K3zc/NwHoHpQuG/IXzePpg8y0tvn0sgj7KRfylZLcpTW", true);
        secondAuthor = new Author("Bill", "Brown", "Grandfather",
                new Date(987654321L), "jhon@gmail.ru",
                "$2a$08$TE.n99nO.6.ba1WkcpmWZ.p84cb/QVBI4rWj37yKdQWlH/13ira7u", false);

        firstPost = new Post(firstAuthor, "Great adventure", "After several niths I realised id!",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                        " labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                        "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                        "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                        " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        secondPost = new Post(secondAuthor, "Beautiful skyline", "It was dangerous but we did it!",
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque " +
                        "laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi " +
                        "architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit " +
                        "aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione " +
                        "voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet," +
                        " consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et " +
                        "dolore magnam aliquam quaerat voluptatem.");

        firstComment = new Comment(secondAuthor, "Wow! Really?! It's would be a great!", firstPost);
        secondComment = new Comment(firstAuthor, "But I must explain to you how all this mistaken idea of " +
                "denouncing pleasure and praising pain was born and I will give you a complete account of the system, " +
                "and expound the actual teachings of the great explorer of the truth, the master-builder of human " +
                "happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because" +
                " those who do not know how to pursue pleasure rationally encounter consequences that are " +
                "extremely painful", secondPost);
        thirdComment = new Comment(firstAuthor, "The wise man therefore always holds in these matters to this" +
                " principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures" +
                " pains to avoid worse pains.", secondPost);

        authorFilter = new AuthorFilter();
        postFilter = new PostFilter();
        commentFilter = new CommentFilter();
    }

    @After
    public void tearDown() throws Exception {
        blogService.removeAuthor(firstAuthor.getId());
        blogService.removeAuthor(secondAuthor.getId());
        blogService.removePost(firstPost.getId());
        blogService.removePost(secondPost.getId());
        blogService.removeComment(firstComment.getId());
        blogService.removeComment(secondComment.getId());
        blogService.removeComment(thirdComment.getId());
    }

    @Transactional
    @Test
    public void insertAndDeleteCommentTest() throws Exception {
        createAuthor(firstAuthor);
        createPost(firstPost);
        createComment(firstComment);
        updateComment(firstComment, secondComment);
        updatePost(firstPost, secondPost);
        updateAuthor(firstAuthor, secondAuthor);
        createComment(secondComment);
        createComment(thirdComment);
        updateComment(secondComment, thirdComment);
    }

    @Transactional
    public void createAuthor(Author author) throws Exception {
        assert (author.getId() == 0);
        blogService.save(author);
        int authorId = author.getId();
        assert (authorId != 0);
        Author createdAuthor = blogService.getAuthor(authorId);
        assert createdAuthor.equals(author);
    }

    @Transactional
    public void updateAuthor(Author source, Author end) {
        Author temporary = source;
        int authorId = source.getId();
        assert !source.equals(end);
        source = end;
        source.setId(authorId);
        blogService.save(source);

        source = blogService.getAuthor(authorId);
        assert source.equals(end);
//        assert source.getBirthday().equals(end.getBirthday());
//        assert source.getEmail().equals(end.getEmail());
//        assert source.getFirstName().equals(end.getFirstName());
//        assert source.getLastName().equals(end.getLastName());
//        assert source.getNickName().equals(end.getNickName());
//        assert source.getPassword().equals(end.getPassword());
        source = temporary;
        blogService.save(source);
    }


    @Transactional
    public void deleteAuthor(Author toDelete) {
        assert toDelete != null;
        int id = toDelete.getId();
        blogService.removeAuthor(toDelete.getId());
        assert blogService.getAuthor(id) == null;
    }

    @Transactional
    public void createPost(Post entity) throws Exception {
        assert (entity.getId() == 0);
        blogService.save(entity);
        int id = entity.getId();
        assert (id != 0);
        Post createdPost = blogService.getPost(id);
        assert createdPost.equals(entity);
    }

    @Transactional
    public void updatePost(Post source, Post end) {
        Post temporary = source;
        int id = source.getId();
        assert !source.equals(end);
        source = end;
        source.setId(id);
        blogService.save(source);

        source = blogService.getPost(id);
        assert source.equals(end);
        source = temporary;
        blogService.save(source);
    }

    @Transactional
    public void deletePost(Post toDelete) {
        assert toDelete != null;
        int id = toDelete.getId();
        blogService.removePost(toDelete.getId());
        assert blogService.getAuthor(id) == null;
    }


    @Transactional
    public void createComment(Comment entity) throws Exception {
        assert (entity.getId() == 0);
        blogService.save(entity);
        int id = entity.getId();
        assert (id != 0);
        Comment createdComment = blogService.getComment(id);
        assert createdComment.equals(entity);
    }

    @Transactional
    public void updateComment(Comment source, Comment end) {
        Comment temporary = source;
        int id = source.getId();
        assert !source.equals(end);
        source = end;
        source.setId(id);
        blogService.save(source);

        source = blogService.getComment(id);
        assert source.equals(end);
        source = temporary;
        blogService.save(source);
    }

    @Transactional
    public void deleteComment(Comment toDelete) {
        assert toDelete != null;
        int id = toDelete.getId();
        blogService.removeComment(toDelete.getId());
        assert blogService.getAuthor(id) == null;
    }


    @Test
    public void getAuthorsTest() {
        List<Author> authors = blogService.getAuthors(authorFilter);
        assert authors.size() != 0;
        for (Author author : authors) {
            assert author.getPassword() != null;
            assert author.getNickName() != null;
            assert author.getFirstName() != null;
            assert author.getLastName() != null;
        }
    }

    @Test
    public void getPostsTest() {
        List<Post> posts = blogService.getPosts(postFilter);
        int firstSize = posts.size();
        assert firstSize != 0;
        for (Post post : posts) {
            assert post.getAuthor() != null;
            assert post.getPublished() != null;
            assert post.getTitle() != null;
            assert post.getSubtitle() != null;
            assert post.getText() != null;
        }
        postFilter.setVisible(false);
        assert firstSize != blogService.getPosts(postFilter).size();
    }

    @Test
    public void getCommentsTest() {
        List<Comment> comments = blogService.getComments(commentFilter);
        assert comments.size() != 0;
        for (Comment comment : comments) {
            assert comment.getAuthor() != null;
            assert comment.getPublished() != null;
            assert comment.getText() != null;
            assert comment.getPost() != null;
        }
    }

}
