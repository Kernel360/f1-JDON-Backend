package kernel.jdon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@EntityScan(basePackages = "kernel.jdon")
public class ModuleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

}
