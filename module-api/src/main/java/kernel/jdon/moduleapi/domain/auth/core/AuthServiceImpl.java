package kernel.jdon.moduleapi.domain.auth.core;

import static kernel.jdon.auth.util.HmacUtil.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.auth.util.AesUtil;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.member.core.MemberFactory;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final MemberFactory memberFactory;

	@Override
	@Transactional
	public AuthInfo.RegisterResponse register(AuthCommand.RegisterRequest command) {
		final Map<String, String> userInfo = getUserInfoFromAuthProvider(command.getHmac(), command.getEncrypted());
		final Member savedMember = memberFactory.save(command, userInfo);

		return AuthInfo.RegisterResponse.of(savedMember.getId());
	}

	private Map<String, String> getUserInfoFromAuthProvider(String hmac, String encrypted) {
		String emailAndProvider = getEmailAndProviderString(hmac, encrypted);

		return parseQueryString(emailAndProvider);
	}

	private String getEmailAndProviderString(String hmac, String encrypted) {
		String emailAndProvider = null;
		try {
			if (isValidHMAC(hmac, encrypted)) {
				emailAndProvider = AesUtil.decryptAESCBC(encrypted);
			} else {
				throw new ApiException(MemberErrorCode.UNAUTHORIZED_EMAIL_OAUTH2);
			}
		} catch (Exception e) {
			throw new ApiException(MemberErrorCode.BAD_REQUEST_INVALID_ENCRYPT_STRING);
		}
		return emailAndProvider;
	}

	private Map<String, String> parseQueryString(String queryString) {
		Map<String, String> params = new HashMap<>();
		String[] pairs = queryString.split("&");
		try {
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				String key = URLDecoder.decode(keyValue[0], "UTF-8");
				String value = URLDecoder.decode(keyValue[1], "UTF-8");
				params.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
			throw new ApiException(MemberErrorCode.BAD_REQUEST_FAIL_PARSE_QUERY_STRING);
		}
		return params;
	}

}
