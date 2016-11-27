package ru.breathoffreedom.mvc.services.image;

import org.springframework.data.jpa.domain.Specification;
import ru.breathoffreedom.mvc.models.file.Image;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris_azanov on 27.11.16.
 */
public class ImageSpec {

    public static Specification<Image> getSpec(ImageFilter filter) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(filter.getPost() != null) {
                predicates.add(builder.equal(root.get("post"), filter.getPost()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
