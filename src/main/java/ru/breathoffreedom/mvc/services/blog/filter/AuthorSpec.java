package ru.breathoffreedom.mvc.services.blog.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.breathoffreedom.mvc.models.user.Author;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris_azanov on 26.11.16.
 */
public final class AuthorSpec {

    public static Specification<Author> getSpec(AuthorFilter filter) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(filter.getFirstName() != null) {
                predicates.add(builder.equal(root.get("firstName"), filter.getFirstName()));
            }
            if(filter.getLastName() != null) {
                predicates.add(builder.equal(root.get("lastName"), filter.getLastName()));
            }
            if (filter.getEmail() != null) {
                predicates.add(builder.equal(root.get("email"), filter.getEmail()));
            }
            if (filter.getNickName() != null) {
                predicates.add(builder.equal(root.get("nickName"), filter.getNickName()));
            }
            if (filter.getBirthday() != null) {
                predicates.add(builder.equal(root.get("birthday"), filter.getBirthday()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
