package khuvid19.vaccinated.config;

import khuvid19.vaccinated.BearerAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    private final BearerAuthInterceptor bearerAuthInterceptor;

    @Value("${resources.notload.list}")
    private List<String> notLoadList;

    public AppConfig(BearerAuthInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Set Interceptor {} ", bearerAuthInterceptor.getClass());

        registry.addInterceptor(bearerAuthInterceptor)
                .addPathPatterns("/board/**")
                .excludePathPatterns("/**")
                .excludePathPatterns("/");
    }

}
