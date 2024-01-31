package org.universe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * http://localhost:8080/home
 */
@SpringBootApplication
public class imatchApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(imatchApp.class, args);
    }

}
