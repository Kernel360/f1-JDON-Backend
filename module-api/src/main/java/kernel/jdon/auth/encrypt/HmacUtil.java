package kernel.jdon.auth.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HmacUtil {
	private static final String secretKey = SecretKeyConstants.HMAC_PRIVATE_KEY;

	public static String generateHMAC(String data) throws Exception {
		Mac hmac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
			"HmacSHA256");
		hmac.init(secretKeySpec);
		byte[] hmacBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

		return Base64.getEncoder().encodeToString(hmacBytes);
	}

	public static boolean isValidHMAC(String receivedHMAC, String data) throws Exception {
		String calculatedHMAC = generateHMAC(data);
		
		return receivedHMAC.equals(calculatedHMAC);
	}
}
