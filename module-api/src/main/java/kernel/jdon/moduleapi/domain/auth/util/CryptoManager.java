package kernel.jdon.moduleapi.domain.auth.util;

import static kernel.jdon.auth.util.HmacUtil.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import kernel.jdon.auth.util.AesUtil;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoManager {
	public Map<String, String> getUserInfoFromAuthProvider(final String hmac, final String encrypted) {
		final String emailAndProvider = getEmailAndProviderString(hmac, encrypted);

		return parseQueryString(emailAndProvider);
	}

	private String getEmailAndProviderString(final String hmac, final String encrypted) {
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

	private Map<String, String> parseQueryString(final String queryString) {
		final Map<String, String> params = new HashMap<>();
		final String[] pairs = queryString.split("&");
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
