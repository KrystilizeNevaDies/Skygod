package skygod.sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

import net.minestom.server.entity.Player;
import net.minestom.server.sound.Sound;
import net.minestom.server.sound.SoundCategory;
import net.minestom.server.utils.Position;

public class NoteSender
implements Receiver {
    private MidiSong sound;
    private ToneUtil toneUtil;
    private Sequencer sequencer;

    public NoteSender(MidiSong sound, File file) throws InvalidMidiDataException, IOException {
        this.sound = sound;
        this.channelPatches = new HashMap<Integer, Integer> ();
        this.ids = new HashSet<Integer> ();
        this.toneUtil = new ToneUtil(new MidiInfo(file).mean - 48);
    }

    private MidiSong noteSound;
    private Map<Integer, Integer> channelPatches;
    private Set<Integer> ids;
    
    @Override
    public void send(MidiMessage message, long time) {
        if (message instanceof ShortMessage) {
            int patch;
            ShortMessage shortMessage = (ShortMessage) message;
            int chan = shortMessage.getChannel();

            switch (shortMessage.getCommand()) {

                case 192:
                    patch = shortMessage.getData1();

                    if (patch != 0) {
                        this.ids.add(Integer.valueOf(patch));
                    }

                    this.channelPatches.put(Integer.valueOf(chan), Integer.valueOf(patch));
                    break;

                case 144:
                    playNote(shortMessage);
                    break;
            }
        }
    }

    public void playNote(ShortMessage message) {
        if (144 != message.getCommand())
            return;
        float pitch = toneUtil.convertToPitch(message);
        float volume = message.getData2() / 127.0F;

        Sound instrument = ToneUtil.getInstrument((int) pitch);
        
        pitch = toPitch((int) pitch);
        
        if (instrument != null) {
            for (Player player: this.sound.getPlayers()) {
                Position position = player.getPosition();
                player.playSound(instrument,
            		SoundCategory.AMBIENT,
            		(int) position.getX(),
            		(int) position.getY(),
            		(int) position.getZ(),
            		volume,
            		pitch
            	);
            }
        }
    }
    
    private float toPitch(int pitch) {
    	return (float) Math.pow(2.0, (((double) pitch % 24) - 12.0) / 12.0);
    }

    @Override
    public void close() {
        this.channelPatches.clear();
    }

    public void setSequencer(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    public Sequencer getSequencer() {
        return this.sequencer;
    }

    public void setNoteSound(MidiSong noteSound) {
        this.noteSound = noteSound;
    }

    public MidiSong getNoteSound() {
        return this.noteSound;
    }

    public Set<Integer> getIds() {
        return this.ids;
    }

    public boolean stop() {
        if (this.sequencer.isRunning()) {
            this.sequencer.stop();
            return true;
        }

        return false;
    }
}