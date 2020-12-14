package skygod.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import net.minestom.server.entity.Player;
import net.minestom.server.sound.Sound;
import net.minestom.server.sound.SoundCategory;
import net.minestom.server.utils.Position;

public class MidiSong {
    private File file;
    private float tempo;
    private List <Player> players = new ArrayList <Player> ();
    private NoteSender receiver;
    private Consumer<MidiSong> endScript;

    public void setMidiFile(File file) {
        this.file = file;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
        if (this.receiver != null) {
            this.receiver.getSequencer().setTempoFactor(tempo);
        }
    }

    public boolean addPlayer(Player player) {
        if (this.players.contains(player)) {
            return false;
        }

        this.players.add(player);

        return true;
    }

    public boolean removePlayer(Player player) {
        if (!this.players.contains(player)) {
            return false;
        }

        this.players.remove(player);

        return true;
    }

    public long getTickLength() {
        if (this.receiver == null) {
            return -1L;
        }

        return this.receiver.getSequencer().getTickLength();
    }

    public long getMicrosecondsLength() {
        if (this.receiver == null) {
            return -1L;
        }

        return this.receiver.getSequencer().getMicrosecondLength();
    }

    public boolean setLoops(int loops) {
        if (this.receiver == null) {
            return false;
        }

        this.receiver.getSequencer().setLoopCount(loops);

        return true;
    }

    public boolean togglePause() {
        if (this.receiver == null) {
            return false;
        }
        // TODO: pause
        return true;
    }

    public boolean stop() {
        if (this.receiver == null) {
            return false;
        }

        return this.receiver.stop();
    }

    public boolean play() {
        if (this.receiver == null) {
            this.receiver = MidiUtil.playFile(this, this.file, this.tempo);
            return true;
        }
        if (!this.receiver.getSequencer().isRunning()) {
            this.receiver = MidiUtil.playFile(this, this.file, this.tempo);
            return true;
        }

        return false;
    }

    protected void end() {
    	endScript.accept(this);
	}
    
    public boolean setTickPosition(long tick) {
        if (this.receiver == null) {
            return false;
        }

        this.receiver.getSequencer().setTickPosition(tick);

        return true;
    }

    public boolean setMicrosecondPosition(long tick) {
        if (this.receiver == null) {
            return false;
        }

        this.receiver.getSequencer().setMicrosecondPosition(tick);

        return true;
    }

    public List <Player> getPlayers() {
        return this.players;
    }

    public File getMidiFile() {
        return this.file;
    }

    public Consumer<MidiSong> getEndScript() {
		return endScript;
	}

	public void setEndScript(Consumer<MidiSong> endScript) {
		this.endScript = endScript;
	}

	public static class ToneUtil {

        private int BASE_NOTE;

        public ToneUtil(int lowestNote) {
            BASE_NOTE = lowestNote;
        }

        public int convertToPitch(ShortMessage message) {
            return (midiToNote(message) - BASE_NOTE);
        }

        private int midiToNote(ShortMessage smsg) {
            assert smsg.getCommand() == 144;
            int semitones = smsg.getData1() - 6;
            return semitones;
        }

        public Sound getInstrument(int pitch) {
            switch (pitch) {
                case 0:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 1:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 2:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 3:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 4:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 5:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 6:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 7:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 8:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 9:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 10:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 11:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
                case 12:
                    return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;

                case 13:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 14:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 15:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 16:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 17:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 18:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 19:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 20:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 21:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 22:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 23:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;
                case 24:
                    return Sound.BLOCK_NOTE_BLOCK_BASS;

                case 25:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 26:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 27:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 28:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 29:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 30:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 31:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 32:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 33:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 34:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 35:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 36:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;

                case 37:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 38:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 39:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 40:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 41:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 42:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 43:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 44:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 45:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 46:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 47:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;
                case 48:
                    return Sound.BLOCK_NOTE_BLOCK_HARP;

                case 49:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 50:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 51:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 52:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 53:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 54:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 55:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 56:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 57:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 58:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 59:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 60:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;

                case 61:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 62:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 63:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 64:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 65:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 66:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 67:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 68:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 69:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 70:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 71:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
                case 72:
                    return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;

                case 73:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 74:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 75:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 76:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 77:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 78:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 79:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 80:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 81:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 82:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 83:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                case 84:
                    return Sound.BLOCK_NOTE_BLOCK_FLUTE;
                default:
                    return Sound.BLOCK_NOTE_BLOCK_PLING;
            }
        }

        public static ToneUtil create(int i) {
            return new ToneUtil(i);
        }
    }

    public static class NoteSender implements Receiver {
        private MidiSong sound;
        private ToneUtil toneUtil;
        private Sequencer sequencer;

        public NoteSender(MidiSong sound, File file) throws InvalidMidiDataException, IOException {
            this.sound = sound;
            this.channelPatches = new HashMap < Integer, Integer > ();
            this.ids = new HashSet < Integer > ();
            this.toneUtil = ToneUtil.create(MidiInfo.create(file).mean - 48);
        }

        private MidiSong noteSound;
        private Map < Integer, Integer > channelPatches;
        private Set < Integer > ids;

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

            Sound instrument = toneUtil.getInstrument((int) pitch);

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

        public Set < Integer > getIds() {
            return this.ids;
        }

        public boolean stop() {
            if (this.sequencer.isRunning()) {
                this.sequencer.stop();
                return true;
            }

            return false;
        }

        public static NoteSender create(MidiSong sound, File file) {
            try {
                return new NoteSender(sound, file);
            } catch (InvalidMidiDataException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class MidiUtil {
        private static List < NoteSender > sequencerList = new ArrayList < NoteSender > ();

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

                final NoteSender noteReceiver = NoteSender.create(sound, file);
                sequencer.getTransmitter().setReceiver(noteReceiver);
                sequencer.start();

                sound.stop();

                sequencer.addMetaEventListener(new MetaEventListener() {
                    @Override
                    public void meta(MetaMessage meta) {
                        if (meta.getType() == 47) {
                            sound.stop();
                            sound.end();
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

    public static class MidiInfo {

        private static final int NOTE_ON = 0x90;
        private static final int NOTE_OFF = 0x80;
        public int lowestNote = 128;
        public int highestNote = 0;
        public int mean = 0;


        MidiInfo(File file) {
            try {
                readFile(file);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static MidiInfo create(File file) {
            return new MidiInfo(file);
        }

        private void readFile(File file) throws InvalidMidiDataException, IOException {
            Sequence sequence = MidiSystem.getSequence(file);

            int noteCount = 0;

            int notePitchs = 0;

            for (Track track: sequence.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
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

            mean = (int)((double) notePitchs / (double) noteCount);

        }
    }
}