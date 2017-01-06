package ru.breathoffreedom.mvc.services.blog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private static Author firstAuthor;
    private static Author secondAuthor;

    private static Post firstPost;
    private static Post secondPost;

    private static Comment firstComment;
    private static Comment secondComment;
    private static Comment thirdComment;

    private AuthorFilter authorFilter;
    private PostFilter postFilter;
    private CommentFilter commentFilter;

    private String email;
    private String wrongEmail;
    private String nickName;
    private String wrongNickName;

    @Autowired
    private BlogService blogService;

    @Before
    public void setUp() throws Exception {
        email = "admin@gmail.com";
        wrongEmail = "jfsdfndskfnk@mail.ru";
        nickName = "Admin";
        wrongNickName = "BlackBoots";
        firstAuthor = new Author("Jhon", "Connor", "Saver",
                null, "jhon@gmail.ru",
                "$2a$08$MGcfwEVo6K3zc/NwHoHpQuG/IXzePpg8y0tvn0sgj7KRfylZLcpTW", true);
        secondAuthor = new Author("Bill", "Brown", "Grandfather",
                new Date(987654321L), "bill@gmail.ru",
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

        Author author = blogService.getAuthor(firstAuthor.getEmail());
//        if (author == null) {
//            firstAuthor = blogService.save(firstAuthor);
//        } else {
//            firstAuthor = blogService.save(author);
//        }
//        author = blogService.getAuthor(secondAuthor.getEmail());
//        if (author == null) {
//            secondAuthor = blogService.save(secondAuthor);
//        } else {
//            secondAuthor = blogService.save(author);
//        }
//        if (firstPost.getId() == 0) {
//            firstPost.setAuthor(firstAuthor);
//            firstPost = blogService.save(firstPost);
//        }
//        if (secondPost.getId() == 0) {
//            secondPost.setAuthor(secondAuthor);
//            secondPost = blogService.save(secondPost);
//        }
//        if (firstComment.getId() == 0) {
//            firstComment.setAuthor(secondAuthor);
//            firstComment = blogService.save(firstComment);
//        }
//        if (secondComment.getId() == 0) {
//            secondComment.setAuthor(firstAuthor);
//            secondComment = blogService.save(secondComment);
//        }
//        if (thirdComment.getId() == 0) {
//            thirdComment.setAuthor(firstAuthor);
//            thirdComment = blogService.save(thirdComment);
//        }

        authorFilter = new AuthorFilter();
        postFilter = new PostFilter();
        commentFilter = new CommentFilter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hasAuthorWithEmailAndNicknameTest() {
        assert blogService.hasAuthorWithEmail(email);
        assert !blogService.hasAuthorWithEmail(wrongEmail);
        assert blogService.hasAuthorWithNickName(nickName);
        assert !blogService.hasAuthorWithNickName(wrongNickName);
    }

    @Test
    public void getAuthorTest() {
        Author author = blogService.getAuthor(email);
        assert author != null;
        assert author.getId() != 0;
        assert author.getNickName().equals("Admin");
        assert author.getPassword().equals("12345");
        assert author.getEmail().equals("admin@gmail.com");
        assert author.isEnabled();
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
        checkAuthor(createdAuthor, author);
    }

    @Transactional
    public void updateAuthor(Author source, Author end) {
        int authorId = source.getId();
        assert !source.equals(end);
        source.setPassword(end.getPassword());
        source.setEnabled(end.isEnabled());
        source.setEmail(end.getEmail());
        source.setLastName(end.getLastName());
        source.setFirstName(end.getFirstName());
        source.setNickName(end.getNickName());
        source.setBirthday(end.getBirthday());
        blogService.save(source);

        source = blogService.getAuthor(authorId);
        assert source.getBirthday().equals(end.getBirthday());
        assert source.getEmail().equals(end.getEmail());
        assert source.getFirstName().equals(end.getFirstName());
        assert source.getLastName().equals(end.getLastName());
        assert source.getNickName().equals(end.getNickName());
        assert source.getPassword().equals(end.getPassword());
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
        int id = source.getId();
        assert !source.equals(end);
        source.setText(end.getText());
        source.setTitle(end.getTitle());
        source.setSubtitle(end.getSubtitle());
        source.setPublished(end.getPublished());
        source.setAuthor(end.getAuthor());
        source.setVisible(end.isVisible());
        blogService.save(source);

        source = blogService.getPost(id);
        assert source.getId() != end.getId();
        assert source.getAuthor().getId() == end.getAuthor().getId();
        assert source.getPublished().equals(end.getPublished());
        assert source.getTitle().equals(end.getTitle());
        assert source.getSubtitle().equals(end.getSubtitle());
        assert source.getText().equals(end.getText());
        assert source.isVisible() == end.isVisible();
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
        source.setAuthor(end.getAuthor());
        source.setText(end.getText());
        source.setPublished(end.getPublished());
        source.setPost(end.getPost());
        blogService.save(source);

        source = blogService.getComment(id);
        assert source.getId() != end.getId();
        assert source.getAuthor().getId() == end.getAuthor().getId();
        assert source.getPublished().equals(end.getPublished());
        assert source.getPost().getId() == end.getPost().getId();
        assert source.getText().equals(end.getText());
        source = temporary;
        blogService.save(source);
    }

    @Test
    public void addCommentTest()  throws Exception{
        createAuthor(firstAuthor);
        Comment comment = blogService.addComment(thirdComment, 2);
        checkComment(comment, thirdComment);
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

    @Test
    public void getPostByPageRequestTest() {
        PostFilter filter = new PostFilter();
        filter.setVisible(true);
        List<Post> posts = blogService.getPosts(filter, new PageRequest(0, 100,
                Sort.Direction.DESC, "published")).getContent();
        for (Post post : posts) {
            checkPost(post, filter);
        }

    }

    public void checkPost(Post post, PostFilter filter) {
        if (filter.isVisible()) {
            assert post.isVisible() == filter.isVisible();
        }
        if (filter.getSubtitle() != null) {
            assert post.getSubtitle().equals(filter.getSubtitle());
        }
        if (filter.getTitle() != null) {
            assert post.getTitle().equals(filter.getTitle());
        }
        if (filter.getPublished() != null) {
            assert post.getPublished().equals(filter.getPublished());
        }
        if (filter.getAuthor() != null) {
            assert post.getAuthor().equals(filter.getAuthor());
        }
    }

    public void checkComment(Comment commentForTest, Comment commentPattern) {
        if (commentPattern.getPost() != null) {
            assert commentForTest.getPost() == commentPattern.getPost();
        }
        if (commentPattern.getText() != null) {
            assert commentForTest.getText().equals(commentPattern.getText());
        }
        if (commentPattern.getPublished() != null) {
            assert commentForTest.getPublished().equals(commentPattern.getPublished());
        }
        if (commentPattern.getAuthor() != null) {
            assert commentForTest.getAuthor().equals(commentPattern.getAuthor());
        }
    }

    public void checkAuthor(Author createdAuthor, Author oldAuthor) {
        assert createdAuthor.getEmail().equals(oldAuthor.getEmail());
        assert createdAuthor.getPassword().equals(oldAuthor.getPassword());
        assert createdAuthor.getNickName().equals(oldAuthor.getNickName());
        if (createdAuthor.getFirstName() != null) {
            assert createdAuthor.getFirstName().equals(oldAuthor.getFirstName());
        } else {
            assert oldAuthor.getFirstName() == null;
        }
        if (createdAuthor.getLastName() != null) {
            assert createdAuthor.getLastName().equals(oldAuthor.getLastName());
        } else {
            assert oldAuthor.getLastName() == null;
        }
        if (createdAuthor.getBirthday() != null) {
            assert createdAuthor.getBirthday().equals(oldAuthor.getBirthday());
        } else {
            assert oldAuthor.getBirthday() == null;
        }
    }

}
