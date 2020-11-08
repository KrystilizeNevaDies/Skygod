package Skygod;

import java.util.ArrayList;

import net.minestom.server.chat.ColoredText;
import net.minestom.server.chat.JsonMessage;
import net.minestom.server.item.metadata.WrittenBookMeta;

public class Books {
	
	public static ArrayList<ColoredText> serverSelect = serverSelectBook();
	
	public static void addPages(WrittenBookMeta meta, ArrayList<ColoredText> book) {
		ArrayList<JsonMessage> pages = meta.getPages();
		book.forEach(page -> {
			pages.add(page);
		});
	}
 	
	private static ArrayList<ColoredText> serverSelectBook() {
		ArrayList<ColoredText> book = new ArrayList<ColoredText>();
		// Page 1
		book.add(
		ColorGradient.of(Gradients.BOOK					, "Welcome!")
			
			
		);
		
		
		
        return book;
	}
}
