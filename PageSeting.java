import java.awt.*;
import javax.swing.*;

public class PageSeting extends JPanel {

    private JSlider musicSlider;
    private JSlider audioSlider;

    public PageSeting() {
        setLayout(null);

        // สร้างแผงหลัก
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(170, 180, 911, 420);
        panel.setBackground(Color.blue);
        
        // สร้างปุ่ม "Back to the menu"
        JButton backButton = new JButton("Back to the menu");
        backButton.setBounds(480, 530, 300, 50);
        add(backButton);

        // สร้าง JSlider สำหรับ music และ audio
        musicSlider = new JSlider(0, 100, 50);
        musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider
        audioSlider = new JSlider(0, 100, 50);
        audioSlider.setBounds(300, 250, 500, 50);

        // เพิ่ม slider และ panel ในแผง
        panel.add(musicSlider);
        panel.add(audioSlider);
        add(panel);
        
        // กำหนดขนาด panel
        setPreferredSize(new Dimension(1200, 800));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // เรียกการวาดพื้นฐาน

        // ตรวจสอบว่าภาพสามารถโหลดได้หรือไม่
        ImageIcon bannerIcon = new ImageIcon("./image/BannerSeting.png");
        if (bannerIcon.getImage() != null) {
            g.drawImage(bannerIcon.getImage(), 300, 0, this);
        } else {
            System.out.println("Failed to load BannerSeting.png");
        }

        // วาดไอคอนสำหรับ music และ audio
        ImageIcon musicIcon = new ImageIcon("./image/music.png");
        if (musicIcon.getImage() != null) {
            g.drawImage(musicIcon.getImage(), 225, 250, 50, 50, this);
        } else {
            System.out.println("Failed to load music.png");
        }

        ImageIcon volumeIcon = new ImageIcon("./image/volume.png");
        if (volumeIcon.getImage() != null) {
            g.drawImage(volumeIcon.getImage(), 225, 450, 50, 50, this);
        } else {
            System.out.println("Failed to load volume.png");
        }
    }
}
