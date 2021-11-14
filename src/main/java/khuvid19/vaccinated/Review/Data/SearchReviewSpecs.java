package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class SearchReviewSpecs {

    public static Specification<Review> initial () {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root);
    }

    public static Specification<Review> vaccineEqual (VaccineType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("vaccine"), type);
    }

    public static Specification<Review> sideEffectContains (List<SideEffectType> types) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("sideEffects").in(types));
    }
}
