import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class Sound {
    private Clip clip;

    // โหลดเสียง
    public void playmusic(){
        try {
            loadSound("./Sound/tvari-tokyo-cafe-159065.wav");
            play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
    }

    public void loadSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
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
