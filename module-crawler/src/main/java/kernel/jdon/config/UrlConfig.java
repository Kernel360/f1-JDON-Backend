package kernel.jdon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Getter
@Configuration
@PropertySource("classpath:url.properties")
public class UrlConfig {
	@Value("${url.wanted.detail}")
	private String wantedJobDetailUrl;
	@Value("${url.wanted.api.list}")
	private String wantedApiJobListUrl;
	@Value("${url.wanted.api.detail}")
	private String wantedApiJobDetailUrl;
}
