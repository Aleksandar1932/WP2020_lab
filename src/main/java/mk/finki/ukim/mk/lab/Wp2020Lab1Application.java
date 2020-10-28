package mk.finki.ukim.mk.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

@SpringBootApplication
@ServletComponentScan
public class Wp2020Lab1Application {

    public static void main(String[] args) {
        SpringApplication.run(Wp2020Lab1Application.class, args);
    }

}
