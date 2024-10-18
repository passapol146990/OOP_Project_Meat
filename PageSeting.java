import java.awt.*;
import javax.swing.*;

public class PageSeting extends JPanel {

    private JSlider musicSlider;
    private JSlider audioSlider;
    private JLabel  music;
    private JLabel  audio;
    public PageSeting() {
        setLayout(null);

        // สร้างแผงหลัก
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(170, 180, 911, 420);
        panel.setBackground(Color.blue);
        
        // สร้างปุ่ม "Back to the menu"
        Component backButton = new Component("Back to the menu",new Color(254,169,169));
        backButton.setBounds(480, 530, 300, 50);
        add(backButton);

        // สร้าง JSlider สำหรับ music และ audio
        musicSlider = new JSlider(0, 100, 50);
        musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider
        music = new JLabel("Music");
        music.setBounds(150, 50, 1000, 50);
        music.setFont(new Font("Courier New",Font.BOLD,50));
        audioSlider = new JSlider(0, 100, 50);
        audioSlider.setBounds(300, 250, 500, 50);

        // เพิ่ม slider และ panel ในแผง
        panel.add(musicSlider);
        panel.add(music);
        panel.add(audioSlider);
        add(panel);
        
        // กำหนดขนาด panel
        setPreferredSize(new Dimension(1200, 800));
    }

    @Override
    public void paint(Graphics g) {
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        super.paint(g);
        g.drawImage(icon.getImage(), 0,0, this);
        super.paint(g); // เรียกการวาดพื้นฐาน

        // ตรวจสอบว่าภาพสามารถโหลดได้หรือไม่
        ImageIcon bannerIcon = new ImageIcon("./image/BannerSeting.png");
         g.drawImage(bannerIcon.getImage(), 300, 0, this);
        // วาดไอคอนสำหรับ music และ audio
        ImageIcon musicIcon = new ImageIcon("./image/music.png");
        g.drawImage(musicIcon.getImage(), 225, 250, 50, 50, this);
        ImageIcon volumeIcon = new ImageIcon("./image/volume.png");
        g.drawImage(volumeIcon.getImage(), 225, 450, 50, 50, this);
           
    }
}


// class settingInTheGame extends JPanel{

// }
// class settingOutTheGame extends JPanel{

// }