package skygod.sound;

import javax.sound.midi.ShortMessage;

import net.minestom.server.sound.Sound;

public class ToneUtil {
	
	private int BASE_NOTE;
	
	public ToneUtil(int lowestNote) {
		BASE_NOTE = lowestNote;
	}
	
    public int convertToPitch(ShortMessage message) {
        return (midiToNote(message) - BASE_NOTE);
    }

    private static int midiToNote(ShortMessage smsg) {
        assert smsg.getCommand() == 144;
        int semitones = smsg.getData1() - 6;
        return semitones;
    }

	public static Sound getInstrument(int pitch) {
		switch (pitch) {
		case 0: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 1: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 2: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 3: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 4: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 5: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 6: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 7: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 8: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 9: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 10: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 11: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		case 12: return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
		
		case 13: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 14: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 15: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 16: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 17: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 18: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 19: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 20: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 21: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 22: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 23: return Sound.BLOCK_NOTE_BLOCK_BASS;
		case 24: return Sound.BLOCK_NOTE_BLOCK_BASS;
		
		case 25: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 26: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 27: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 28: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 29: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 30: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 31: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 32: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 33: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 34: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 35: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 36: return Sound.BLOCK_NOTE_BLOCK_HARP;
		
		case 37: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 38: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 39: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 40: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 41: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 42: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 43: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 44: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 45: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 46: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 47: return Sound.BLOCK_NOTE_BLOCK_HARP;
		case 48: return Sound.BLOCK_NOTE_BLOCK_HARP;
		
		case 49: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 50: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 51: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 52: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 53: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 54: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 55: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 56: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 57: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 58: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 59: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 60: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		
		case 61: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 62: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 63: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 64: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 65: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 66: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 67: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 68: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 69: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 70: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 71: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		case 72: return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
		
		case 73: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 74: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 75: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 76: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 77: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 78: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 79: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 80: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 81: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 82: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 83: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		case 84: return Sound.BLOCK_NOTE_BLOCK_FLUTE;
		default: return Sound.BLOCK_NOTE_BLOCK_PLING;
		}
	}
}