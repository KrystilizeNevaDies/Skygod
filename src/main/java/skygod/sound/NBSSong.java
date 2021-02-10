package skygod.sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.minestom.server.entity.Player;
import net.minestom.server.utils.binary.BinaryReader;

public class NBSSong {
	@SuppressWarnings("unused")
	public static void playNBSFile(File file, Player player) {
    	try {
			BinaryReader reader = new BinaryReader(Files.readAllBytes(file.toPath()));
			
			// reader.readSizedString(Integer.MAX_VALUE);	
			assert(reader.readShort() == 0); // The first 2 bytes are always zero. In the old NBS format, this used to be song length, which can never be zero.
			Byte NBSversion = reader.readByte(); // The version of the new NBS format.
			Byte Vanillainstrumentcount = reader.readByte(); // Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
			reader.readShort(); // The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds. Note Block Studio doesn't really care about this value, the song size is calculated in the second part.
			reader.readShort(); // The last layer with at least one note block in it, or the last layer that has had its name, volume or stereo changed.
			reader.readSizedString(Integer.MAX_VALUE); // The name of the song.
			reader.readSizedString(Integer.MAX_VALUE); // The author of the song.
			reader.readSizedString(Integer.MAX_VALUE); // The original author of the song.
			reader.readSizedString(Integer.MAX_VALUE); // The description of the song.
			Short Songtempo = reader.readShort(); // The tempo of the song multiplied by 100 (for example, 1225 instead of 12.25). Measured in ticks per second.
			reader.readByte(); // Whether auto-saving has been enabled (0 or 1). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
			reader.readByte(); // The amount of minutes between each auto-save (if it has been enabled) (1-60). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
			Byte Timesignature = reader.readByte(); // The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8.
			reader.readInteger(); // Amount of minutes spent on the project.
			reader.readInteger(); // Amount of times the user has left-clicked.
			reader.readInteger(); // Amount of times the user has right-clicked.
			reader.readInteger(); // Amount of times the user has added a note block.
			reader.readInteger(); // The amount of times the user have removed a note block.
			reader.readSizedString(Integer.MAX_VALUE); // If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
			reader.readByte(); // Whether looping is on or off. (0 = off, 1 = on)
			Byte Maxloopcount = reader.readByte(); // 0 = infinite. Other values mean the amount of times the song loops.
			Short Loopstarttick = reader.readShort(); // Determines which part of the song (in ticks) it loops back to.
			
			// Note Loop
			while(true) {
				Short Jumpstothenexttick = reader.readShort(); // The amount of "jumps" to the next tick with at least one note block in it. We start at tick -1. If the amount of jumps is 0, the program will stop reading and proceed to the next part.
				System.out.println(Jumpstothenexttick);
				if (Jumpstothenexttick == 0) {
					break;
				} else {
					Short Jumpstothenextlayer = reader.readShort(); // Once we have found an active tick, we read the amount of vertical jumps to the next layer. We start at layer -1. If this is 0, we go back to step 1. If not, we have found a note block!
					Byte Noteblockinstrument = reader.readByte(); // The instrument of the note block. This is 0-15, or higher if the song uses custom instruments.|0 = Piano (Air)|1 = Double Bass (Wood)|2 = Bass Drum (Stone)|3 = Snare Drum (Sand)|4 = Click (Glass)|5 = Guitar (Wool)|6 = Flute (Clay)|7 = Bell (Block of Gold)|8 = Chime (Packed Ice)|9 = Xylophone (Bone Block)|10 = Iron Xylophone (Iron Block)|11 = Cow Bell (Soul Sand)|12 = Didgeridoo (Pumpkin)|13 = Bit (Block of Emerald)|14 = Banjo (Hay)|15 = Pling (Glowstone)
					Byte Noteblockkey = reader.readByte(); // The key of the note block, from 0-87, where 0 is A0 and 87 is C8. 33-57 is within the 2-octave limit.
					Byte Noteblockvelocity = reader.readByte(); // The velocity/volume of the note block, from 0% to 100%.
					Byte Noteblockpanning = reader.readByte(); // The stereo position of the note block, from 0-200. 100 is center panning.
					Short Noteblockpitch = reader.readShort(); // The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference. After reading this, we go back to step 2.
				}
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
