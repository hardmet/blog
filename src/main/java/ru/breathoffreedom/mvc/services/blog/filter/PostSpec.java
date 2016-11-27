package ru.breathoffreedom.mvc.services.blog.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.breathoffreedom.mvc.models.blog.Post;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris_azanov on 26.11.16.
 */
public final class PostSpec {

    public static Specification<Post> getSpec(PostFilter filter) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(filter.getTitle() != null) {
                predicates.add(builder.equal(root.get("title"), filter.getTitle()));
            }
            if(filter.getPublished() != null) {
                predicates.add(builder.equal(root.get("published"), filter.getPublished()));
            }
            if (filter.getAuthor() != null) {
                predicates.add(builder.equal(root.get("author"), filter.getAuthor()));
            }
            if (filter.getSubtitle() != null) {
                predicates.add(builder.equal(root.get("subtitle"), filter.getSubtitle()));
            }
            if (filter.isVisible() != null) {
                predicates.add(builder.equal(root.get("visible"), filter.isVisible()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
