package org.ajaxer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Shakir Ansari
 * @since 2025-04-28
 */
class TotpUtilsTest
{
	@Test
	void generateSecret_ShouldReturnValidBase32String()
	{
		String secret = TOTPUtil.generateSecret();

		assertThat(secret)
				.isNotNull()
				.isNotEmpty()
				.matches("^[A-Z2-7]+$"); // Only Base32 valid chars
	}

	@Test
	void generateCurrentTOTP_ShouldGenerate6DigitCode() throws Exception
	{
		String secret = TOTPUtil.generateSecret();
		String totp = TOTPUtil.generateCurrentTOTP(secret);

		assertThat(totp)
				.isNotNull()
				.hasSize(6)
				.matches("\\d{6}"); // Exactly 6 digits
	}

	@Test
	void verifyCode_ShouldReturnTrueForCorrectCode() throws Exception
	{
		String secret = TOTPUtil.generateSecret();
		String totp = TOTPUtil.generateCurrentTOTP(secret);

		boolean isValid = TOTPUtil.verifyCode(secret, totp);

		assertThat(isValid).isTrue();
	}

	@Test
	void verifyCode_ShouldReturnFalseForWrongCode() throws Exception
	{
		String secret = TOTPUtil.generateSecret();
		String wrongCode = "000000"; // Impossible random code

		boolean isValid = TOTPUtil.verifyCode(secret, wrongCode);

		assertThat(isValid).isFalse();
	}
}
