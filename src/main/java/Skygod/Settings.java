package skygod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Settings {
	
	private static Settings settings = null;
	
	private Hashtable<String, Object> properties;
	
	private Settings() {
		properties = new Hashtable<String, Object>();
		
		// Read settings
		// Load file
		File settings = new File("Settings.txt");
		
		// Create scanner
		Scanner myReader = null;
		try {
			myReader = new Scanner(settings);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Read each line
        while (myReader.hasNextLine()) {
	        // Get line
        	String line = myReader.nextLine();
        	// If line Is Valid
        	if (!line.startsWith("#") && !line.isBlank()) {
        		
        		// Split Line
	        	String[] property = line.split(": ", 2);
	        	
	        	String key = property[0];
	        	
	        	String object = property[1];
	        	
        		// Set Property
	        	properties.put(key, object);
        	}
        }
	}
	
	/**
	 * Gets the single Settings
	 * @return InstanceList
	 */
	public static Settings get() 
    { 
        if (settings == null) 
        	settings = new Settings(); 
  
        return settings; 
    }
	
	/**
	 * Gets a setting from the settings file
	 * @param key
	 * @return
	 */
	public Object getSetting(String key) {
		return properties.get(key);
	}
}
