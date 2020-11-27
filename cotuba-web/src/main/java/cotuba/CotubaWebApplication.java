package cotuba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("cotuba")
@SpringBootApplication
public class CotubaWebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CotubaWebApplication.class, args);
	}

}
