package az.job;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(":security")
public class UserManagement {

    public static void main(String[] args) {
        SpringApplication.run(UserManagement.class, args);

    }
}