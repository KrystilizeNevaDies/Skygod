package skygod;

import java.util.ArrayList;

import net.minestom.server.chat.ChatClickEvent;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.chat.ChatHoverEvent;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.chat.JsonMessage;
import net.minestom.server.chat.RichMessage;
import net.minestom.server.item.metadata.WrittenBookMeta;

public class Books {
	
	public static ArrayList<RichMessage> serverSelect = serverSelectBook();
	
	public static void addPages(WrittenBookMeta meta, ArrayList<RichMessage> book) {
		ArrayList<JsonMessage> pages = (ArrayList<JsonMessage>) meta.getPages();
		book.forEach(page -> {
			pages.add(page);
		});
	}
 	
	private static ArrayList<RichMessage> serverSelectBook() {
		ArrayList<RichMessage> book = new ArrayList<RichMessage>();
		// Page 1
		book.add(
			RichMessage.of(ColoredText.of(""))
				// Line 1
				.append(ColoredText.of(ChatColor.BLACK, "\u2692\n\u2588\u2588\n\u2588\u2588"))
					.setHoverEvent(
						ChatHoverEvent.showText(
							ColoredText.of(ChatColor.BRIGHT_GREEN, 
								"\u250F\n  Your World\n                  \u251B"
							)
						)
					)
					.setClickEvent(
						ChatClickEvent.runCommand("home")
					)
					.append(ColoredText.of("\n\n"))
				// Line 2
				.append(ColoredText.of(ChatColor.BLACK, "\u2699\n\u2588\u2588\n\u2588\u2588"))
					.setHoverEvent(
						ChatHoverEvent.showText(
							ColoredText.of(ChatColor.BRIGHT_GREEN, 
								"\u250F\n  Settings\n                  \u251B"
							)
						)
					)
					.setClickEvent(
						ChatClickEvent.runCommand("settings")
					)
					.append(ColoredText.of("\n\n"))
				// Line 3
				.append(ColoredText.of(ChatColor.BLACK, "\u2708\n\u2588\u2588\n\u2588\u2588"))
					.setClickEvent(
						ChatClickEvent.runCommand("exit")
					)
					.append(ColoredText.of("\n\n"))
			);
        return book;
	}
}
