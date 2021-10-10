package khuvid19.vaccinated.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String accessToken;

    public User(String email, String name, String accessToken) {
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
    }
}
