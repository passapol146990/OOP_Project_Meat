import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class Sound{
    private int volumeMusic = 100;
    private int volumeEffect = 100;
    private Clip music;
    private Clip effect;
    void playMusic(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./Sound/tvari-tokyo-cafe-159065.wav"));
            this.music = AudioSystem.getClip();
            this.music.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}
        if (this.music != null) {
            this.music.start();
            this.music.loop(-1);
        }
    }
    void stopMusic() {
        if (this.music != null && this.music.isRunning()) {
            this.music.stop();
        }
    }
    void setVolumeMusic(int value) {
        this.volumeMusic = value;
        if (this.music != null) {
            float volume;
            FloatControl volumeControl = (FloatControl) this.music.getControl(FloatControl.Type.MASTER_GAIN);
            if(this.volumeMusic == 0){
                this.music.stop(); // หยุดเสียง
                volumeControl.setValue(-80.0f);
            }else{
                volume = (float) (this.volumeMusic - 100) * 0.5f;
                volumeControl.setValue(volume); // ปรับระดับเสียง
                if (!this.music.isRunning()) {
                    this.music.start(); // เริ่มเสียงถ้าหยุด
                }
            }
        }
    }
    int getVolumeMusic(){return this.volumeMusic;}
    ///////////////// Effect Sound //////////////////////////////////////
    void playEffect() {
        try {
            File audioFile = new File(System.getProperty("user.dir") + File.separator + "./sound/meat_audio.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            this.effect = (Clip) AudioSystem.getLine(info);
            this.effect.open(stream);
            FloatControl volumeControl = (FloatControl) this.effect.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f);
            this.effect.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void closeEffect() {
        if (this.effect != null) {
            this.effect.stop();
            this.effect.close();
        }
    }
    void setVolumeEffect(int value) {
        this.volumeEffect = value;
        if (this.effect != null) {
            float volume;
            FloatControl volumeControl = (FloatControl) this.effect.getControl(FloatControl.Type.MASTER_GAIN);
            if(this.volumeEffect == 0){
                this.effect.stop(); // หยุดเสียง
                volumeControl.setValue(-80.0f);
            }else{
                volume = (float) (this.volumeEffect - 100) * 0.5f;
                volumeControl.setValue(volume); // ปรับระดับเสียง
                if (!this.effect.isRunning()) {
                    this.effect.start(); // เริ่มเสียงถ้าหยุด
                }
            }
        }
    }
    int getVolumeEffect(){return this.volumeEffect;}
    ///////////////// Give Money Sound //////////////////////////////////////
    void playGiveMoney() {
        try {
            File audioFile = new File(System.getProperty("user.dir") + File.separator + "./Sound/givemoney.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip sound = (Clip) AudioSystem.getLine(info);
            sound.open(stream);
            FloatControl volumeControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-1.0f);
            sound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void stopGiveMoney() {
        if (this.effect != null) {
            this.effect.stop();
            this.effect.close();
        }
    }
}

