package khuvid19.vaccinated.DetailReview.Data;

import khuvid19.vaccinated.SimpleReview.Data.SimpleReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor @AllArgsConstructor
@Getter
@Entity
public class DetailReview {

    @Id
    @GeneratedValue
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private SimpleReview tags;
    private String contents;
}
