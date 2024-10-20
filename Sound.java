import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class Sound {
    private Clip clip;

    // โหลดเสียง
    public void playmusic(){
        Sound sound = new Sound();
        try {
            sound.loadSound("./Sound/tvari-tokyo-cafe-159065.wav");
            sound.play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
    }

    public void loadSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File soundFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
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
}
    public void setVolume(int value) {
    if (clip != null) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float volume = (float) (value - 100) * 0.5f; // ค่าระดับเสียงจาก -80 dB ถึง 0 dB
        volumeControl.setValue(volume); // ปรับระดับเสียง
    }
}
}
