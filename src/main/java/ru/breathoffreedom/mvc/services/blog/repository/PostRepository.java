package ru.breathoffreedom.mvc.services.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.breathoffreedom.mvc.models.blog.Post;

/**
 * Created by boris_azanov on 26.11.16.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    Long deleteByAuthorId(int id);
}
