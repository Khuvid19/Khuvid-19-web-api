package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
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

    public static Specification<Review> inoculatedBetween (Date start, Date end) {
        if (start == null) { start = new Date(1); }
        if (end == null) { end = new Date(); }
        Date finalStart = start;
        Date finalEnd = end;
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("inoculatedDate"), finalStart, finalEnd);
    }
}
