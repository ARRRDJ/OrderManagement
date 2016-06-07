package nl.avisi.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
@EnableScheduling
@EnableAutoConfiguration
public class OrderManagement {
    public static void main(String[] args) {
        SpringApplication.run(OrderManagement.class, args);
    }
}
