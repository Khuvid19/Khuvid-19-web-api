package khuvid19.vaccinated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VaccinatedApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccinatedApplication.class, args);
	}

}
