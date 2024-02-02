package kernel.jdon.config.auth;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

		http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("http://localhost:3000", "https://localhost:3000",
				"https://jdon.kr", "https://jdon.netlify.app"));
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
			config.setAllowedHeaders(List.of("*"));
			config.setAllowCredentials(true);
			config.setMaxAge(3600L);

			return config;
		}));
		http.exceptionHandling(exceptionConfig -> exceptionConfig
			.authenticationEntryPoint(jdonAuthExceptionHandler)
			.accessDeniedHandler(jdonAuthExceptionHandler));
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false)
		);
		http.authorizeHttpRequests(config -> config
			//TODO: 테스트용으로 임시로 모든 요청을 허용하도록 설정
			// .requestMatchers("/api/v1/member").hasAnyRole("USER")
			.requestMatchers("api/**").permitAll()
			.anyRequest().permitAll());
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
