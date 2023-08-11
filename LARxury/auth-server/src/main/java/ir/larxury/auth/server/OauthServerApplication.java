package ir.larxury.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "ir.larxury.*")
@SpringBootApplication
public class OauthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthServerApplication.class, args);
	}

}
