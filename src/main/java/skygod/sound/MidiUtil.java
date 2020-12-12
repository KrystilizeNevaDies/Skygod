package skygod.sound;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import net.minestom.server.sound.Sound;

public class MidiUtil {
    private static List<NoteSender> sequencerList = new ArrayList<NoteSender> ();

    public static void stop() {
        for (NoteSender noteReceiver: sequencerList) {
            if (noteReceiver.getSequencer().isRunning()) {
                noteReceiver.getSequencer().stop();
                noteReceiver.getNoteSound().stop();
            }
        }
    }

    private static NoteSender play(final MidiSong sound, Sequence seq, float tempo, File file) {
        try {
            Sequencer sequencer = MidiSystem.getSequencer(false);
            sequencer.setSequence(seq);
            sequencer.open();

            sequencer.setTempoFactor(tempo);

            final NoteSender noteReceiver = new NoteSender(sound, file);
            sequencer.getTransmitter().setReceiver(noteReceiver);
            sequencer.start();

            sound.stop();

            sequencer.addMetaEventListener(new MetaEventListener() {
                @Override
                public void meta(MetaMessage meta) {
                    if (meta.getType() == 47) {
                    	sound.stop();
                        MidiUtil.sequencerList.remove(noteReceiver);
                    }
                }
            });

            noteReceiver.setSequencer(sequencer);
            noteReceiver.setNoteSound(sound);

            sequencerList.add(noteReceiver);

            return noteReceiver;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NoteSender playFile(MidiSong sound, File file, float tempo) {
        try {
            return play(sound, MidiSystem.getSequence(file), tempo, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Sound getInstrument(int note) {
        // TODO: Get Instrument
        return null;
    }
}