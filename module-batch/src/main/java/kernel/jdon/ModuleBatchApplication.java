package kernel.jdon;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableBatchProcessing
@EntityScan(basePackages = "kernel.jdon")
public class ModuleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    class ModuleBatchApplicationRunner implements ApplicationRunner {
        private static final String MODULE_NAME = "module-batch";

        private final SlackSender slackSender;
        private final ApplicationContext applicationContext;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            sendServerStart();
        }

        private void sendServerStart() {
            slackSender.sendServerStart(MODULE_NAME);
        }
    }
}
