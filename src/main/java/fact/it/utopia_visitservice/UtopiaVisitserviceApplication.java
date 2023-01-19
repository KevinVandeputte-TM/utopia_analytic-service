package fact.it.utopia_visitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class UtopiaVisitserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtopiaVisitserviceApplication.class, args);
	}

}
