package org.ajaxer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Shakir Ansari
 * @since 2025-04-28
 */
public class Main
{
	public static void main(String[] args) throws Exception
	{
		// Step 1: Generate secret for a new user
		//		String secret = TOTPUtil.generateSecret();
		String secret = "E6J6GUKBM2O7LV6W";
		System.out.println("Secret (save in database for user): " + secret);

		// Step 2: Give QR code URL to user to scan in Google Authenticator
		String username = "shakir@example.com";
		String issuer = "MyAwesomeApp";
		String qrCodeUrl = getGoogleAuthenticatorBarCode(secret, username, issuer);
		System.out.println("Scan this QR code URL: " + qrCodeUrl);
		generateQRCode(qrCodeUrl);

		// Step 3: At login time, verify the code entered by user
		// Simulating: we generate the same code here
		String generatedCode = TOTPUtil.generateCurrentTOTP(secret);
		System.out.println("Current TOTP: " + generatedCode);

		boolean verified = TOTPUtil.verifyCode(secret, generatedCode);
		System.out.println("Verification: " + verified);
	}

	private static void generateQRCode(String barcodeURL)
	{
		try
		{
			// Sample Google Authenticator barcode URL
			//String barcodeURL = "otpauth://totp/YourAppName:your_email@example.com?secret=SECRET&issuer=YourAppName";

			// Generate the QR code
			BufferedImage qrCodeImage = QRCodeGenerator.generateQRCode(barcodeURL);

			// Here you could save or display the image as needed
			// For example, save it to a file:
			File file = new File("D:\\ajaxer-org\\mfa\\qrcode.png");
			ImageIO.write(qrCodeImage, "PNG", file);
			System.out.println("QR code saved as: " + file.getAbsolutePath());

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String getGoogleAuthenticatorBarCode(String secret, String account, String issuer)
	{
		return "otpauth://totp/" + issuer + ":" + account + "?secret=" + secret + "&issuer=" + issuer;
	}
}
