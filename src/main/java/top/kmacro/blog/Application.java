package top.kmacro.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ms-zk on 2017-08-20.
 */
@SpringBootApplication
@ServletComponentScan
@PropertySource("classpath:jdbc.properties")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
