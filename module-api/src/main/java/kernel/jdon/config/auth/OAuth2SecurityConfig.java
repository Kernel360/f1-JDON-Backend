package kernel.jdon.config.auth;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import kernel.jdon.auth.service.JdonOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class OAuth2SecurityConfig {
	private final JdonOAuth2UserService jdonOAuth2UserService;
	private final JdonOAuth2AuthenticationSuccessHandler jdonOAuth2AuthenticationSuccessHandler;
	private final JdonAuthExceptionHandler jdonAuthExceptionHandler;
	private final LogoutRedirectUrlConfig logoutRedirectUrlConfig;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		String[] permitAllGET = {
			"/oauth2/authorization/**",
			"/api/v1/coffeechats/**",
			"/api/v1/skills/hot",
			"/api/v1/skills/job-category/**",
			"/api/v1/job-categories",
			"/api/v1/faqs",
			"/api/v1/skills/search",
		};
		String[] permitAllPOST = {
			"/api/v1/register",
			"/api/v1/nickname/duplicate",
		};
		String[] authenticatedGET = {
			"/api/v1/coffeechats/guest",
			"/api/v1/coffeechats/host",
			"/api/v1/skills/member",
			"/api/v1/logout"
		};

		http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("http://localhost:3000", "https://localhost:3000",
				"https://jdon.kr", "https://jdon.netlify.app"));
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
			config.setAllowedHeaders(List.of("*"));
			config.setAllowCredentials(true);
			config.setMaxAge(3600L);

			return config;}));
		http.exceptionHandling(exceptionConfig -> exceptionConfig
			.authenticationEntryPoint(jdonAuthExceptionHandler)
			.accessDeniedHandler(jdonAuthExceptionHandler));
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false));
		http.authorizeHttpRequests(config -> config
			.requestMatchers(HttpMethod.GET, permitAllGET).permitAll()
			.requestMatchers(HttpMethod.POST, permitAllPOST).permitAll()
			.requestMatchers(HttpMethod.GET, authenticatedGET).authenticated()
			.anyRequest().authenticated());
		http.oauth2Login(oauth2Configurer -> oauth2Configurer
			.successHandler(jdonOAuth2AuthenticationSuccessHandler)
			.failureHandler(jdonAuthExceptionHandler)
			.userInfoEndpoint(userInfoEndpointConfigurer -> userInfoEndpointConfigurer
				.userService(jdonOAuth2UserService)));
		http.logout(logoutConfigurer -> logoutConfigurer
			.logoutUrl("/api/v1/logout")
			.logoutSuccessUrl(logoutRedirectUrlConfig.getSuccess())
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID"));

		return http.build();
	}
}
