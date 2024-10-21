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
    private Clip clip;
    public void playGrillSound() {
        try {
            File audioFile = new File(System.getProperty("user.dir") + File.separator + "./sound/meat_audio.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);

            // Adjust volume using FloatControl
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-20.0f);  // ปรับเสียงง

            clip.start();
            
            // Loop the clip continuously
            // clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();  // Stop the clip if it's running
        }
    }
    // โหลดเสียง
    public void playmusic(){
            loadSound("./Sound/tvari-tokyo-cafe-159065.wav");
            play();
    }
    public void loadSound(String filePath){
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            try {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (LineUnavailableException e) {
                
                e.printStackTrace();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // เล่นเสียง
    public void play() {
        if (clip != null) {
            clip.start();
            clip.loop(-1); // เล่นเสียงซ้ำไปเรื่อย ๆ
        }
    }

    // ปิดเสียงเมื่อเสร็จสิ้น
    public void close() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    // play sound 
}
    public void setVolume(int value) {
        if (clip != null) {
            float volume;
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if(value == 0)
            {
                clip.stop(); // หยุดเสียง
                volumeControl.setValue(-80.0f);
            }
            else{
            volume = (float) (value - 100) * 0.5f;
            volumeControl.setValue(volume); // ปรับระดับเสียง
            if (!clip.isRunning()) {
                clip.start(); // เริ่มเสียงถ้าหยุด
            }
            }
        }
    }
}

