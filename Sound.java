import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private int volumeMusic = 100;
    private Clip music;
    private Clip effect;
    private Clip clip2;

    void playGrillSound() {
        try {
            File audioFile = new File(System.getProperty("user.dir") + File.separator + "./sound/meat_audio.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip2 = (Clip) AudioSystem.getLine(info);
            clip2.open(stream);
            FloatControl volumeControl = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-20.0f);
            clip2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void playMusic(){
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("./Sound/tvari-tokyo-cafe-159065.wav"));
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
    int getVolumeEffect(){return this.volumeMusic;}
    ///////////////// Effect Sound //////////////////////////////////////
    public void closeEffect() {
        if (clip2 != null) {
            clip2.stop();
            clip2.close();
        }
    }
    public void setVolumeEffect(int value) {
        if (clip2 != null) {
            float volume;
            FloatControl volumeControl = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
            if(value == 0)
            {
                clip2.stop(); // หยุดเสียง
                volumeControl.setValue(-80.0f);
            }
            else{
            volume = (float) (value - 100) * 0.5f;
            volumeControl.setValue(volume); // ปรับระดับเสียง
            if (!clip2.isRunning()) {
                clip2.start(); // เริ่มเสียงถ้าหยุด
            }
            }
        }
    }
}

