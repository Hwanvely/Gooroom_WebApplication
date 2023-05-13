package GooRoom.projectgooroom;

import GooRoom.projectgooroom.global.config.QueryDslConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Import(QueryDslConfig.class)
public class ProjectgooroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectgooroomApplication.class, args);
	}

}
