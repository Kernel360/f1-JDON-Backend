package kernel.jdon.moduleapi.domain.auth.util;

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

    public String encryptAESCBC(final String message) throws Exception {
        final Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        final byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decryptAESCBC(final String message) throws Exception {
        final Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        final byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(message));

        return new String(decrypted);
    }

    private Cipher getCipher(final int mode) throws Exception {
        final SecretKeySpec secretKey = new SecretKeySpec(
            cryptoProperties.getAesPrivateKeyByByte(), cryptoProperties.getCryptoAlgorithm());
        final IvParameterSpec IV = new IvParameterSpec(cryptoProperties.getAesIVByByte());
        final Cipher cipher = Cipher.getInstance(cryptoProperties.getCryptoTransformation());
        cipher.init(mode, secretKey, IV);

        return cipher;
    }

    public String generateHMAC(final String data) throws Exception {
        final Mac hmac = Mac.getInstance(cryptoProperties.getMacAlgorithm());
        final SecretKeySpec secretKeySpec = new SecretKeySpec(
            cryptoProperties.getHmacPrivateKeyByByte(), cryptoProperties.getMacAlgorithm());
        hmac.init(secretKeySpec);
        final byte[] hmacBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public boolean isValidHMAC(final String receivedHMAC, final String data) throws Exception {
        final String calculatedHMAC = generateHMAC(data);

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
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            params.put(key, value);
        }
        return params;
    }
}
