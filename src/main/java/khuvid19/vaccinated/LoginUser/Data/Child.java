package khuvid19.vaccinated.LoginUser.Data;

import khuvid19.vaccinated.Constants.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Child {
    @Id
    @Column(name = "child_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

//    @Column
//    private Long age;


}
