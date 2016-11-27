package ru.breathoffreedom.mvc.services.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.breathoffreedom.mvc.models.blog.Comment;
import ru.breathoffreedom.mvc.models.blog.Post;

/**
 * Created by boris_azanov on 26.11.16.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    Long deleteByPostId(int id);

    Long deleteByAuthorId(int id);
}
