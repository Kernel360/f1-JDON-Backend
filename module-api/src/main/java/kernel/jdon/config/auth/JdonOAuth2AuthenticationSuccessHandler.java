package kernel.jdon.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel.jdon.auth.dto.JdonOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static kernel.jdon.auth.encrypt.AesUtil.encryptAESCBC;
import static kernel.jdon.auth.encrypt.HmacUtil.generateHMAC;
import static kernel.jdon.util.StringUtil.createQueryString;
import static kernel.jdon.util.StringUtil.joinToString;

@Component
@RequiredArgsConstructor
@Slf4j
public class JdonOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final LoginRedirectUrlConfig loginRedirectUrlConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        JdonOAuth2User jdonOAuth2User = (JdonOAuth2User) authentication.getPrincipal();
        // return 활용
        if (isTemporaryUser(jdonOAuth2User)) {
            String query = createUserInfoString(jdonOAuth2User.getEmail(), jdonOAuth2User.getSocialProviderType());
            String encodedQueryString = createEncryptQueryString(query);;
            response.sendRedirect(joinToString(loginRedirectUrlConfig.getSuccess().getGuest(), encodedQueryString));
        } else {
            response.sendRedirect(loginRedirectUrlConfig.getSuccess().getMember());
        }
    }

    private boolean isTemporaryUser(JdonOAuth2User jdonOAuth2User) {
        // 상수 처리
        return jdonOAuth2User.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"));
    }

    private String createUserInfoString(String email, String provider) {
        return joinToString(createQueryString("email", email), createQueryString("provider", provider));
    }

    private String createEncryptQueryString(String info) {
        String encoded = null;
        try {
            encoded = encryptAESCBC(info);
            encoded = joinToString(createQueryString("value", URLEncoder.encode(encoded, StandardCharsets.UTF_8)),
                    createQueryString("hmac", URLEncoder.encode(generateHMAC(encoded), StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return encoded;
    }
}
