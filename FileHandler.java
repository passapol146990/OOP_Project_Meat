import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private String ip;
    private String name;
    private int volumeMusic;
    private int volumeEffect;

    // Method สำหรับบันทึก IP
    public void saveIp(String ip) {
        updateSetting("ip", ip);
    }

    // Method สำหรับบันทึก Name
    public void saveName(String name) {
        updateSetting("name", name);
    }

    // Method สำหรับบันทึก VolumeMusic
    public void saveVolumeMusic(int volumeMusic) {
        updateSetting("volumeemusic", String.valueOf(volumeMusic));
    }

    // Method สำหรับบันทึก VolumeEffect
    public void saveVolumeEffect(int volumeEffect) {
        updateSetting("volumeeffect", String.valueOf(volumeEffect));
    }

    // Method สำหรับบันทึกค่าทั้งหมด
    public void saveSettings() {
        try (FileWriter writer = new FileWriter("data.txt", false)) {
            writer.write("ip = " + (ip != null ? ip : "") + "\n");
            writer.write("name = " + (name != null ? name : "") + "\n");
            writer.write("volumeemusic = " + volumeMusic + "\n");
            writer.write("volumeeffect = " + volumeEffect + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method สำหรับอัปเดตค่าเฉพาะที่ต้องการ
    public void updateSetting(String key, String value) {
        switch (key) {
            case "ip" -> ip = value;
            case "name" -> name = value;
            case "volumeemusic" -> volumeMusic = Integer.parseInt(value);
            case "volumeeffect" -> volumeEffect = Integer.parseInt(value);
        }
        
        // บันทึกค่าทั้งหมดกลับไปยังไฟล์
        saveSettings();
    }

    // Method สำหรับโหลดข้อมูลโดยไม่แสดงผล
    public void loaddata() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    updateSetting(key, value);  // อัปเดตค่าตาม key
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter methods
    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public int getVolumeMusic() {
        return volumeMusic;
    }

    public int getVolumeEffect() {
        return volumeEffect;
    }
}
