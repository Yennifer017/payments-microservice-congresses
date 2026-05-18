package ayd2.ps2026.demo;

import ayd2.ps2026.demo.common.config.env.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) {
		new EnvConfig();
		SpringApplication.run(DemoApplication.class, args);
	}

}
