package Skygod.Sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.minestom.server.sound.Sound;

public class Song {
	
	public ArrayList<float[]> notes;
	
	private Map<String, Sound> noteBindings; 
	
	public Song(String fileName) {
		
		// Ensure bindings
		if (notes == null)
			generateBindings();
		
		// Load file
		File songFile = new File(fileName);
		
		ArrayList<float[]> unprocessedNotes = new ArrayList<float[]>();
		notes = new ArrayList<float[]>();
		
		// Create scanner
		Scanner myReader = null;
		try {
			myReader = new Scanner(songFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Read each line and add notes to note list
        while (myReader.hasNextLine()) {
	        // Get line
        	String line = myReader.nextLine();
        	// If line Is Valid
        	if (!line.startsWith("#") && !line.isBlank()) {
		        // Split Line
	        	String[] properties = line.split("[|]");
	        	
	        	// Create note
	        	float[] newNote = {0, 0, 0, 0};
	        	newNote[0] = Integer.parseInt(properties[0]);
	        	newNote[1] = Float.parseFloat(properties[1]);
	        	newNote[2] = (float) Math.sqrt(Float.parseFloat(properties[2]));
	        	newNote[3] = noteBindings.get(properties[3]).getId();
	        	
	        	// Add note to list
	        	unprocessedNotes.add(newNote);
        	}
        }
        // Post processing
        // Get mean pitch
        float mean = 0;
        for (int i = 0; i < unprocessedNotes.size(); i++) {
        	mean = mean + unprocessedNotes.get(i)[1];
        }
        mean /= unprocessedNotes.size();
        
        mean = (float) Math.floor(mean);
        
        final float finalMean = mean;
        
        // Add notes to list with correct pitch
        
        unprocessedNotes.forEach(note -> {
        	float[] newNote = {
        			note[0],
        			(float) Math.pow(2, ((note[1] - finalMean) / 12)),
        			note[2],
        			note[3]
        	};
        	
        	notes.add(newNote);
        });
	}
	
	private void generateBindings() {
		noteBindings = new HashMap<String, Sound>();
		
		Sound[] sounds = Sound.values();
		
		for (int i = 0; i < sounds.length; i++) {
			noteBindings.put(sounds[i].name(), sounds[i]);
		}
	}
}
