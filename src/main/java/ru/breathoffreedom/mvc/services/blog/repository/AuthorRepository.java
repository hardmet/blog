package ru.breathoffreedom.mvc.services.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.models.user.Role;

import java.util.List;

/**
 * Created by boris_azanov on 26.11.16.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {

    List<Author> findAuthorByEmail(String email);

    List<Author> findAuthorByNickName(String nickName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE authorities a SET a.access_group = :role WHERE a.useremail = :email", nativeQuery = true)
    int setAuthorRole(@Param("email") String email, @Param("role") String role);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO authorities (useremail, access_group) VALUES(:email, :role)", nativeQuery = true)
    int addAuthority(@Param("email") String email, @Param("role") String role);
}
