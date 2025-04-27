package org.ajaxer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Shakir Ansari
 * @since 2025-04-28
 */
class BackupCodeUtilsTest
{

	@Test
	void generateBackupCodes_ShouldReturnCorrectNumberOfCodes()
	{
		// Generate backup codes
		List<String> backupCodes = BackupCodeUtils.generateBackupCodes();

		// Assert that 10 backup codes are generated
		assertThat(backupCodes).hasSize(10);
	}

	@Test
	void generateBackupCodes_ShouldReturnCodesWithCorrectLength()
	{
		// Generate backup codes
		List<String> backupCodes = BackupCodeUtils.generateBackupCodes();

		// Assert that each code is 8 characters long
		for (String code : backupCodes)
			assertThat(code).hasSize(8);
	}

	@Test
	void generateBackupCodes_ShouldReturnUniqueCodes()
	{
		// Generate backup codes
		List<String> backupCodes = BackupCodeUtils.generateBackupCodes();

		// Assert that the codes are unique (no duplicates)
		assertThat(backupCodes).doesNotHaveDuplicates();
	}

	@Test
	void generateBackupCodes_ShouldContainValidCharacters()
	{
		// Generate backup codes
		List<String> backupCodes = BackupCodeUtils.generateBackupCodes();

		// Assert that each code contains only valid characters (A-Z, 0-9)
		for (String code : backupCodes)
			assertThat(code).matches("[A-Z0-9]+");
	}
}
