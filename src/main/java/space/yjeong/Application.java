package space.yjeong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class Application {  /* 메인 클래스 */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
