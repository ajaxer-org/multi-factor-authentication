package org.ajaxer;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.time.Instant;

/**
 * @author Shakir Ansari
 * @since 2025-04-28
 */
public class TOTPUtil
{
	private static final int SECRET_SIZE = 10; // 80 bits
	private static final String HMAC_ALGORITHM = "HmacSHA1";
	private static final int TIME_STEP_SECONDS = 30;
	private static final int TOTP_DIGITS = 6;
	private static final int SKEW = 5; //in seconds

	/**
	 * Generate a random secret key for a new user
	 */
	public static String generateSecret()
	{
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[SECRET_SIZE];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes).replace("=", ""); // remove padding
	}

	/**
	 * Generate the current TOTP based on secret
	 */
	public static String generateCurrentTOTP(String base32Secret) throws Exception
	{
		long currentInterval = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;
		return generateTOTP(base32Secret, currentInterval);
	}

	/**
	 * Verify the user provided TOTP code
	 */
	public static boolean verifyCode(String base32Secret, String code) throws Exception
	{
		long currentInterval = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;

		// Allow codes from [-1, 0, +1] time windows (to handle slight clock drift)
		for (long i = -SKEW; i <= SKEW; i++)
		{
			String candidate = generateTOTP(base32Secret, currentInterval + i);
			if (candidate.equals(code))
				return true;
		}
		return false;
	}

	/**
	 * Internal method to generate TOTP
	 */
	private static String generateTOTP(String base32Secret, long interval) throws Exception
	{
		Base32 base32 = new Base32();
		byte[] key = base32.decode(base32Secret);

		byte[] data = new byte[8];
		for (int i = 7; i >= 0; i--)
		{
			data[i] = (byte) (interval & 0xFF);
			interval >>= 8;
		}

		Mac mac = Mac.getInstance(HMAC_ALGORITHM);
		mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
		byte[] hash = mac.doFinal(data);

		int offset = hash[hash.length - 1] & 0x0F;
		int binary =
				((hash[offset] & 0x7f) << 24) |
				((hash[offset + 1] & 0xff) << 16) |
				((hash[offset + 2] & 0xff) << 8) |
				(hash[offset + 3] & 0xff);

		int otp = binary % (int) Math.pow(10, TOTP_DIGITS);
		return String.format("%0" + TOTP_DIGITS + "d", otp);
	}
}
