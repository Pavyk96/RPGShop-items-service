package payk96.rpg_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class RpgShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpgShopApplication.class, args);
	}

}
