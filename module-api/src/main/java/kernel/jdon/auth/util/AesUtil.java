package kernel.jdon.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AesUtil {
	private static final String iv = SecretKeyConstants.AES_PRIVATE_KEY;

	public static String encryptAESCBC(String message) throws Exception {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
		byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decryptAESCBC(String message) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(message));

		return new String(decrypted);
	}

	private static Cipher getCipher(int mode) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(iv.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec IV = new IvParameterSpec(iv.substring(0, 16).getBytes());

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, secretKey, IV);

		return cipher;
	}
}
