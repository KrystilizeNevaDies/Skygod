package Skygod;

import net.minestom.server.chat.ChatColor;
import net.minestom.server.chat.ColoredText;

public class ColorGradient {
	
	
	public static ColoredText of(ChatColor[] gradients, String message) {
		// assert gradients' length
		assert gradients.length < 2 : "Gradients list must be larger then 1";
		
		// Create ColoredText
		ColoredText text = ColoredText.of("");
		
		// quarantSise used for spliting the string into different sections
		double quadrantSize = message.length() / (gradients.length - 1);
		
		// for each quadrant
		for (int i = 0; i < gradients.length - 1; i++) {
			// split section out of string
			String section = message.substring((int) (quadrantSize * i), (int) (quadrantSize * (i + 1)));
			
			// add the gradients' output
			fromTwo(text, gradients[i], gradients[i + 1], section);
		}
		
		return text;
	}
	
	// Linear gradient function
	private static void fromTwo(ColoredText text, ChatColor gradientA, ChatColor gradientB, String message) {
		// For each character
		for (double i = 0; i < message.length(); i++) {
			// Get character as string
			String character = String.valueOf(message.charAt((int) i));
			
			// calculate ratio's of each gradient
			double deltaB = (i / message.length());
			double deltaA = 1 - deltaB;
			
			// Get gradients' values
			double aR = gradientA.getRed();
			double aG = gradientA.getGreen();
			double aB = gradientA.getBlue();
			double bR = gradientB.getRed();
			double bG = gradientB.getGreen();
			double bB = gradientB.getBlue();
			
			
			// Remove overflow
			if (aR < 0) {aR += 255;};
			if (aG < 0) {aG += 255;};
			if (aB < 0) {aB += 255;};
			if (bR < 0) {bR += 255;};
			if (bG < 0) {bG += 255;};
			if (bB < 0) {bB += 255;};
			
			// calculate r, g, b using ratios
			
			int r = (int) ((aR * deltaA) + (bR * deltaB));
			int g = (int) ((aG * deltaA) + (bG * deltaB));
			int b = (int) ((aB * deltaA) + (bB * deltaB));
			
			// add character to text component
			
			text.append(ChatColor.fromRGB((byte) r, (byte) g, (byte) b), character);
		}
	}
}
