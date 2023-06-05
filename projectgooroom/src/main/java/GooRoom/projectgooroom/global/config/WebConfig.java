package GooRoom.projectgooroom.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://gooroom-frontend.vercel.app",
                        "https://gooroom-frontend-git-develop-clap-0.vercel.app"
                )
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}

