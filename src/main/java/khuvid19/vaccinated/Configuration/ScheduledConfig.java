package khuvid19.vaccinated.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    @Bean
    public TaskScheduler scheduler() {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler();
        return scheduler;
    }

}