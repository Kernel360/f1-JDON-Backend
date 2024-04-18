package kernel.jdon;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ModuleCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleCrawlerApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    class ModuleCrawlerApplicationRunner implements ApplicationRunner {
        private static final String MODULE_NAME = "module-crawler";

        private final SlackSender slackSender;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            sendServerStart();
        }

        private void sendServerStart() {
            slackSender.sendServerStart(MODULE_NAME);
        }
    }

}
