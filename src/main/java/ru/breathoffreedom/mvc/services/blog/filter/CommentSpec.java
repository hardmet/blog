package ru.breathoffreedom.mvc.services.blog.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.breathoffreedom.mvc.models.blog.Comment;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris_azanov on 26.11.16.
 */
public final class CommentSpec {

    public static Specification<Comment> getSpec(CommentFilter filter) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(filter.getAuthor() != null) {
                predicates.add(builder.equal(root.get("author"), filter.getAuthor()));
            }
            if(filter.getPost() != null) {
                predicates.add(builder.equal(root.get("post"), filter.getPost()));
            }
            if (filter.getPublished() != null) {
                predicates.add(builder.equal(root.get("published"), filter.getPublished()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
