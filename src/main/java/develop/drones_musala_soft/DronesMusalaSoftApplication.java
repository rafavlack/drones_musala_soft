package develop.drones_musala_soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesMusalaSoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(DronesMusalaSoftApplication.class, args);
    }

}
