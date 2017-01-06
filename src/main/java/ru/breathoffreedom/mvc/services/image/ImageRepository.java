package ru.breathoffreedom.mvc.services.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.file.Image;

import java.util.List;

/**
 * Created by boris_azanov on 27.11.16.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>, JpaSpecificationExecutor<Image> {

    @Modifying
    @Transactional
    @Query(value="delete from Image i where i.post.author.id=?1")
    void deleteByAuthorId(int id);

    @Transactional
    Long deleteByPostId(int id);

    List<Image> findByPost(int postId);
}
