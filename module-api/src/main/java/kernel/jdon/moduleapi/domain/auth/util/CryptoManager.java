package kernel.jdon.moduleapi.domain.auth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoManager {

    private final CryptoProperties cryptoProperties;

    public String encryptAESCBC(String message) throws Exception {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decryptAESCBC(String message) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(message));

        return new String(decrypted);
    }

    private Cipher getCipher(int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(
            cryptoProperties.getAesPrivateKey().getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec IV = new IvParameterSpec(cryptoProperties.getAesPrivateKey().substring(0, 16).getBytes());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, IV);

        return cipher;
    }

    public String generateHMAC(String data) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(
            cryptoProperties.getHmacPrivateKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmac.init(secretKeySpec);
        byte[] hmacBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public boolean isValidHMAC(String receivedHMAC, String data) throws Exception {
        String calculatedHMAC = generateHMAC(data);

        return receivedHMAC.equals(calculatedHMAC);
    }

    public Map<String, String> getUserInfoFromAuthProvider(final String hmac, final String encrypted) {
        final String emailAndProvider = getEmailAndProviderString(hmac, encrypted);

        return parseQueryString(emailAndProvider);
    }

    private String getEmailAndProviderString(final String hmac, final String encrypted) {
        String emailAndProvider = null;
        try {
            if (isValidHMAC(hmac, encrypted)) {
                emailAndProvider = decryptAESCBC(encrypted);
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
