package travelu.restserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application booting spring-server.
 */
@SpringBootApplication
public class TraveluApplication {
	/**
	 * Start spring server.
	 *
	 * @param args starting main
	 */
	public static void main(final String... args) {
		SpringApplication.run(TraveluApplication.class, args);
	}

}
