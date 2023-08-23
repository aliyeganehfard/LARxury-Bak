package ir.larxury.message.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "ir.larxury.*")
@AutoConfiguration
@SpringBootApplication
public class MessageDispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageDispatcherApplication.class, args);
	}

}
