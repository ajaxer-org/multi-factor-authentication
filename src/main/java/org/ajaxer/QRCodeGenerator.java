package org.ajaxer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shakir Ansari
 * @since 2025-04-28
 */
public class QRCodeGenerator
{
	private static final int QR_CODE_SIZE = 300; // Size of the QR code

	/**
	 * Generate the QR code image from the Google Authenticator barcode URL
	 *
	 * @param googleAuthenticatorBarcodeURL The URL to encode in the QR code
	 * @return BufferedImage representing the QR code
	 * @throws Exception If the QR code generation fails
	 */
	public static BufferedImage generateQRCode(String googleAuthenticatorBarcodeURL) throws Exception
	{
		// Set the encoding hints
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.MARGIN, 1); // Optional, adjust margin

		// Encode the URL as a QR code
		BitMatrix bitMatrix = new MultiFormatWriter().encode(
				googleAuthenticatorBarcodeURL,
				BarcodeFormat.QR_CODE,
				QR_CODE_SIZE, QR_CODE_SIZE, hints
		);

		// Convert the BitMatrix to a BufferedImage
		BufferedImage qrImage = new BufferedImage(QR_CODE_SIZE, QR_CODE_SIZE, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < QR_CODE_SIZE; x++)
		{
			for (int y = 0; y < QR_CODE_SIZE; y++)
			{
				qrImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}

		return qrImage;
	}
}
