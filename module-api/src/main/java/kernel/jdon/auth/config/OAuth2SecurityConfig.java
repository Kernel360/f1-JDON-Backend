package kernel.jdon.auth.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kernel.jdon.auth.encrypt.AesUtil;
import kernel.jdon.auth.encrypt.HmacUtil;
import kernel.jdon.auth.service.JdonOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
// TODO: 이거하면 왜 HttpSecurity에서 Could not autowire. No beans of 'HttpSecurity' type found. 에러가 사라지는지 모르곘음.
@Configuration
public class OAuth2SecurityConfig {
	private final JdonOAuth2UserService jdonOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests(config -> config
			.anyRequest().permitAll());
		http.oauth2Login(oauth2Configurer -> oauth2Configurer
			.successHandler(oAuth2AuthenticationSuccessHandler())
			.userInfoEndpoint()
			.userService(jdonOAuth2UserService));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
			if (defaultOAuth2User.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"))) {
				Map<String, Object> attributes = defaultOAuth2User.getAttributes();
				String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");

				String encoded = null;
				try {
					encoded = AesUtil.encryptAESCBC(email + "&kakao");
					log.info("email=" + email + "&provider=kakao");
					log.info("encoded : " + encoded);
					log.info("hmac : " + HmacUtil.generateHMAC(encoded));
					encoded = "value=" + encoded + "&hmac=" + HmacUtil.generateHMAC(encoded);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String redirectUrl = "http://localhost:3000/oauth/info?" + encoded;
				response.sendRedirect(redirectUrl);
			}
		});
	}
}
