package kernel.jdon.moduleapi.domain.auth.core;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduleapi.global.exception.AuthException;
import kernel.jdon.modulecommon.error.ErrorCode;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService {
    private final HttpSession httpSession;
    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final OAuth2ProviderComposite oauth2ProviderComposite;

    @Override
    @Transactional
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User user = super.loadUser(userRequest);

        return getOAuth2User(userRequest, user);
    }

    private DefaultOAuth2User getOAuth2User(final OAuth2UserRequest userRequest, final OAuth2User user) {
        final SessionUserInfo userInfo = oauth2ProviderComposite.getOAuth2Strategy(getSocialProvider(userRequest))
            .getUserInfo(user);
        final Member findMember = memberReader.findByEmail(userInfo.getEmail());
        final List<SimpleGrantedAuthority> authorities = getAuthorities(userInfo, findMember);

        return new JdonOAuth2User(authorities, user.getAttributes(), getUserNameAttributeName(userRequest),
            userInfo.getEmail(), userInfo.getSocialProvider());
    }

    private List<SimpleGrantedAuthority> getAuthorities(final SessionUserInfo userInfo, final Member member) {
        if (Objects.nonNull(member)) {
            checkAccountStatusActive(userInfo, member);
            checkRightSocialProvider(member, userInfo.getSocialProvider(),
                AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE);
            updateMemberLoginData(userInfo, member);
            return List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name()));
        }
        return List.of(new SimpleGrantedAuthority(MemberRole.ROLE_GUEST.name()));
    }

    private void updateMemberLoginData(final SessionUserInfo userInfo, final Member member) {
        httpSession.setAttribute("USER", userInfo.getMemberSession(member));
        memberStore.updateLastLoginDate(member);
    }

    private void checkAccountStatusActive(final SessionUserInfo userInfo, final Member member) {
        if (member.isWithDrawMember()) {
            checkRightSocialProvider(member, userInfo.getSocialProvider(),
                AuthErrorCode.CONFLICT_WITHDRAW_BY_OTHER_SOCIAL_PROVIDER);
            throw new AuthException(AuthErrorCode.CONFLICT_WITHDRAW_ACCOUNT);
        }
    }

    private void checkRightSocialProvider(final Member member, final SocialProviderType socialProvider,
        final ErrorCode errorCode) {
        if (!member.isRightSocialProvider(socialProvider)) {
            throw new AuthException(errorCode);
        }
    }

    private SocialProviderType getSocialProvider(final OAuth2UserRequest userRequest) {
        return SocialProviderType.valueOf((userRequest.getClientRegistration().getRegistrationId()).toUpperCase());
    }

    private String getUserNameAttributeName(final OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();
    }

    @Override
    public boolean sendDeleteRequestToOAuth2(final MemberCommand.WithdrawRequest command) {
        return oauth2ProviderComposite.getOAuth2Strategy(command.getSocialProvider()).unlinkOAuth2Account(command);
    }
}
