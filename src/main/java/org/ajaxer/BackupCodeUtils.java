package org.ajaxer;

/**
 * @author Shakir Ansari
 * @since 2025-04-28
 */

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class BackupCodeUtils
{
	private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Customize as needed
	private static final int CODE_LENGTH = 8; // Each backup code will be 8 characters long
	private static final int NUM_CODES = 10; // Number of backup codes to generate

	/**
	 * Generate a list of random backup codes.
	 */
	public static List<String> generateBackupCodes()
	{
		SecureRandom random = new SecureRandom();
		List<String> codes = new ArrayList<>();

		for (int i = 0; i < NUM_CODES; i++)
		{
			StringBuilder code = new StringBuilder(CODE_LENGTH);
			for (int j = 0; j < CODE_LENGTH; j++)
			{
				int index = random.nextInt(CHARSET.length());
				code.append(CHARSET.charAt(index));
			}
			codes.add(code.toString());
		}

		return codes;
	}
}
