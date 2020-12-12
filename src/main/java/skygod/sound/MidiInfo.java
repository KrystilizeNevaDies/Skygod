package skygod.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiInfo {

    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    public int lowestNote = 128;
    public int highestNote = 0;
    public int mean = 0;
    
    
    public MidiInfo(File file) {
        try {
            readFile(file);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFile(File file) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(file);

        int noteCount = 0;
        
        int notePitchs = 0;
        
        for (Track track: sequence.getTracks()) {
            for (int i = 0; i<track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        if (key < lowestNote) {
                        	lowestNote = key;
                        } else if (key > highestNote) {
                        	highestNote = key;
                        }

                        
                        notePitchs += key;
                        noteCount++;
                    } else if (sm.getCommand() == NOTE_OFF) {
                        sm.getData1();
                    }
                }
            }
        }
        
        mean = (int) ((double) notePitchs / (double) noteCount);
        
    }
}