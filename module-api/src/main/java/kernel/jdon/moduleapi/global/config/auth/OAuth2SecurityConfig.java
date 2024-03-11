package kernel.jdon.moduleapi.global.config.auth;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletRequest;
import kernel.jdon.moduleapi.domain.auth.core.CustomOAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class OAuth2SecurityConfig {
    private final CustomOAuth2UserServiceImpl customOAuth2UserServiceImpl;
    private final JdonOAuth2AuthenticationSuccessHandler jdonOAuth2AuthenticationSuccessHandler;
    private final JdonAuthExceptionHandler jdonAuthExceptionHandler;
    private final AllowOriginProperties allowOriginProperties;

    private static AuthorizationManager<RequestAuthorizationContext> hasIpAddress(List<String> ipAddress) {
        List<IpAddressMatcher> matcherList = ipAddress.stream().map(IpAddressMatcher::new).toList();
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            boolean isIpMatch = matcherList.stream()
                .anyMatch(matcher -> matcher.matches(request));

            return new AuthorizationDecision(isIpMatch);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final String[] permitAllGET = {
            "/oauth2/authorization/**",
            "/api/v1/coffeechats/**",
            "/api/v1/skills/hot",
            "/api/v1/skills/job-category/**",
            "/api/v1/job-categories",
            "/api/v1/faqs",
            "/api/v1/skills/search",
            "/api/v1/jds/**",
            "/api/v1/authenticate"
        };
        final String[] permitAllPOST = {
            "/api/v1/register",
            "/api/v1/nickname/duplicate",
        };
        final String[] authenticatedGET = {
            "/api/v1/coffeechats/guest",
            "/api/v1/coffeechats/host",
            "/api/v1/skills/member",
            "/api/v1/logout"
        };

        http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(allowOriginProperties.getUrl());
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
            .maxSessionsPreventsLogin(false));
        http.authorizeHttpRequests(config -> config
            .requestMatchers(HttpMethod.GET, permitAllGET).permitAll()
            .requestMatchers(HttpMethod.POST, permitAllPOST).permitAll()
            .requestMatchers(HttpMethod.GET, authenticatedGET).authenticated()
            .requestMatchers("/actuator/**").access(hasIpAddress(allowOriginProperties.getMonitoring()))
            .anyRequest().authenticated());
        http.oauth2Login(oauth2Configurer -> oauth2Configurer
            .successHandler(jdonOAuth2AuthenticationSuccessHandler)
            .failureHandler(jdonAuthExceptionHandler)
            .userInfoEndpoint(userInfoEndpointConfigurer -> userInfoEndpointConfigurer
                .userService(customOAuth2UserServiceImpl)));
        http.logout(logoutConfigurer -> logoutConfigurer
            .logoutUrl("/api/v1/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl(allowOriginProperties.getOrigin()));

        return http.build();
    }
}
