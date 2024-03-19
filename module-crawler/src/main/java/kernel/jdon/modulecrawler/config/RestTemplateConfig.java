package kernel.jdon.modulecrawler.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.modulecrawler.global.error.code.CrawlerServerErrorCode;
import kernel.jdon.modulecrawler.global.error.exception.CrawlerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        final int connectTimeoutSecond = 5;
        final int readTimeoutSecond = 5;
        return restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(connectTimeoutSecond))
            .setReadTimeout(Duration.ofSeconds(readTimeoutSecond))
            .additionalInterceptors(clientHttpRequestInterceptor())
            .build();
    }

    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        final int retryCount = 3;
        return (request, body, execution) -> {
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(retryCount));
            try {
                return retryTemplate.execute(context -> execution.execute(request, body));
            } catch (Throwable throwable) {
                log.error("AppConfig.clientHttpRequestInterceptor {}", "restTemplate retry 중 Error 발생");
                throw new CrawlerException((CrawlerServerErrorCode.INTERNAL_SERVER_ERROR_REST_TEMPLATE_RETRY));
            }
        };
    }
}