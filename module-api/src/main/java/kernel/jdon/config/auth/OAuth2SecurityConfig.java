package kernel.jdon.config.auth;

import kernel.jdon.auth.service.JdonOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
// TODO: 이거하면 왜 HttpSecurity에서 Could not autowire. No beans of 'HttpSecurity' type found. 에러가 사라지는지 모르곘음.
@Configuration
public class OAuth2SecurityConfig {
    private final JdonOAuth2UserService jdonOAuth2UserService;
    private final JdonOAuth2AuthenticationSuccessHandler jdonOAuth2AuthenticationSuccessHandler;
    private final JdonAuthExceptionHandler jdonAuthExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:3000", "https://peaceful-sopapillas-36c089.netlify.app"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            config.setMaxAge(3600L);

            return config;
        }));
        http.exceptionHandling(exceptionConfig -> exceptionConfig
                .authenticationEntryPoint(jdonAuthExceptionHandler)
                .accessDeniedHandler(jdonAuthExceptionHandler));
        http.csrf().disable();
        http.authorizeHttpRequests(config -> config
                .requestMatchers("/api/v1/member").hasAnyRole("USER")
                .requestMatchers("api/**").permitAll()
                .anyRequest().permitAll());
        http.oauth2Login(oauth2Configurer -> oauth2Configurer
                .successHandler(jdonOAuth2AuthenticationSuccessHandler)
                .failureHandler(jdonAuthExceptionHandler)
                .userInfoEndpoint(userInfoEndpointConfigurer -> userInfoEndpointConfigurer
                        .userService(jdonOAuth2UserService)));
        http.logout(logoutConfigurer -> logoutConfigurer
                .logoutUrl("/api/v1/logout")
                .logoutSuccessUrl("http://localhost:3000/main") // TODO: 프론트와 상의 후 메인페이지 url로 변경
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));

        return http.build();
    }
}
