package kernel.jdon.moduleapi.domain.auth.util;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {
    private final String aesPrivateKey;
    private final String hmacPrivateKey;
    private final String macAlgorithm = "HmacSHA256";
    private final String cryptoAlgorithm = "AES";
    private final String cryptoTransformation = "AES/CBC/PKCS5Padding";

    public byte[] getAesPrivateKeyByByte() {
        return aesPrivateKey.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getAesIVByByte() {
        return aesPrivateKey.substring(0, 16).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getHmacPrivateKeyByByte() {
        return hmacPrivateKey.getBytes(StandardCharsets.UTF_8);
    }
}
