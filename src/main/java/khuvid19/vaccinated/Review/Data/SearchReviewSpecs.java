package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class SearchReviewSpecs {

    public static Specification<Review> initial () {
        return Specification.where(null);
    }

    public static Specification<Review> vaccineContains (List<VaccineType> types) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("vaccine").in(types));
    }

    public static Specification<Review> sideEffectContain(SideEffectType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(type, root.get("sideEffects"));
    }

    public static Specification<Review> genderContains (List<Gender> genders) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("author").get("gender").in(genders));
    }

    public static Specification<Review> ageContains (List<AgeType> ages) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("author").get("age").in(ages));
    }

    public static Specification<Review> inoculatedBetween (Date start, Date end) {
        if (start == null) { start = new Date(1); }
        if (end == null) { end = new Date(); }
        Date finalStart = start;
        Date finalEnd = end;
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("inoculatedDate"), finalStart, finalEnd);
    }

    public static Specification<Review> searchTextContains (String substr) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("detailDisc"), "%" + substr + "%");
    }
}
