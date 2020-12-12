package skygod.sound;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minestom.server.entity.Player;

public class MidiSong {
    private File file;
    private float tempo;
    private List<Player> players = new ArrayList<Player> ();
    private NoteSender receiver;

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

    public List<Player> getPlayers() {
        return this.players;
    }

    public File getMidiFile() {
        return this.file;
    }
}