package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.ReviewType;
import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.Data.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Review {

    @Id @GeneratedValue
    Long id;

    @CreatedDate
    Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date inoculatedDate;

    VaccineType vaccine;

    ReviewType reviewTargetType;

    Long reviewTargetId;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    List<SideEffectType> sideEffects;

    String etcSideEffect;

    Boolean haveDisease;
    String diseaseDisc;


    @Column(length = 5000)
    String detailDisc;
}
