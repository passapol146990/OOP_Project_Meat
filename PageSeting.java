import java.awt.*;
import javax.swing.*;

public class PageSeting extends JPanel{

    public PageSeting() {
        setSize(1200, 800);
        setLayout(new BorderLayout());

        // สร้างอินสแตนซ์ของ settingInTheGame และเพิ่มในเฟรม
        settingInTheGame settingPanel = new settingInTheGame();
        add(settingPanel, BorderLayout.CENTER);

        setVisible(true);  // แสดงหน้าต่าง
    }
}


class settingInTheGame extends JPanel{
    private JSlider musicSlider;
    private JSlider audioSlider;
    private JLabel music;
    private JLabel audio;

    public settingInTheGame() {
        setLayout(null);

        // สร้างแผงหลัก
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(170, 180, 911, 420);
        panel.setBackground(new Color(85,85,85));

        // สร้างปุ่ม "Back to the menu"
        JButton backButton = Component.createCustomRoundedButton("Back to the menu", Color.pink);
        backButton.setBounds(480, 530, 300, 50);
        add(backButton);

        // สร้าง JSlider สำหรับ music และ audio
        musicSlider = Component.createCustomSlider();
        musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider

        music = new JLabel("Music");
        music.setBounds(150, 100, 1000, 50);
        music.setFont(new Font("Courier New", Font.BOLD, 50));

        audioSlider = Component.createCustomSlider();
        audioSlider.setBounds(300, 250, 500, 50);
        
        audio = new JLabel("Audio");
        audio.setBounds(150, 250, 1000, 50);
        audio.setFont(new Font("Courier New", Font.BOLD, 50));

        // เพิ่ม slider และ label ในแผง
        panel.add(musicSlider);
        panel.add(music);
        panel.add(audioSlider);
        panel.add(audio);
        add(panel);

        // กำหนดขนาด panel
        setPreferredSize(new Dimension(1200, 800));
    }

    // ฟังก์ชันสร้างปุ่มที่มีขอบโค้งมน

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // วาดพื้นหลังและไอคอน
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        g.drawImage(icon.getImage(), 0, 0, this);

        ImageIcon bannerIcon = new ImageIcon("./image/BannerSeting.png");
        g.drawImage(bannerIcon.getImage(), 300, 0, this);

        ImageIcon musicIcon = new ImageIcon("./image/music.png");
        g.drawImage(musicIcon.getImage(), 225, 250, 50, 50, this);

        ImageIcon volumeIcon = new ImageIcon("./image/volume.png");
        g.drawImage(volumeIcon.getImage(), 225, 450, 50, 50, this);
    }
}

// class settingOutTheGame extends JPanel{
    // private JSlider musicSlider;
    // private JSlider audioSlider;
    // private JLabel music;
    // private JLabel audio;

    // public settingInTheGame() {
    //     setLayout(null);

    //     // สร้างแผงหลัก
    //     JPanel panel = new JPanel();
    //     panel.setLayout(null);
    //     panel.setBounds(170, 180, 911, 420);
    //     panel.setBackground(new Color(85,85,85));

    //      //สร้างปุ่ม "Back to the game"
    //      JButton bactGameButton = Component.createCustomRoundedButton("Back to the game", Color.pink);
    //      backGameButton.setBounds(150,530,300,50);
    //      add(bactgamebutton);

    //     // สร้างปุ่ม "Back to the menu"
    //     JButton backButton = Component.createCustomRoundedButton("Back to the menu", Color.pink);
    //     backButton.setBounds(350, 530, 300, 50);
    //     add(backButton);


    //     // สร้าง JSlider สำหรับ music และ audio
    //     musicSlider = Component.createCustomSlider();
    //     musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider

    //     music = new JLabel("Music");
    //     music.setBounds(150, 100, 1000, 50);
    //     music.setFont(new Font("Courier New", Font.BOLD, 50));

    //     audioSlider = Component.createCustomSlider();
    //     audioSlider.setBounds(300, 250, 500, 50);
        
    //     audio = new JLabel("Audio");
    //     audio.setBounds(150, 250, 1000, 50);
    //     audio.setFont(new Font("Courier New", Font.BOLD, 50));

    //     // เพิ่ม slider และ label ในแผง
    //     panel.add(musicSlider);
    //     panel.add(music);
    //     panel.add(audioSlider);
    //     panel.add(audio);
    //     add(panel);

    //     // กำหนดขนาด panel
    //     setPreferredSize(new Dimension(1200, 800));
    // }

    // // ฟังก์ชันสร้างปุ่มที่มีขอบโค้งมน

    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     // วาดพื้นหลังและไอคอน
    //     ImageIcon icon = new ImageIcon("./image/bg-start.png");
    //     g.drawImage(icon.getImage(), 0, 0, this);

    //     ImageIcon bannerIcon = new ImageIcon("./image/BannerSeting.png");
    //     g.drawImage(bannerIcon.getImage(), 300, 0, this);

    //     ImageIcon musicIcon = new ImageIcon("./image/music.png");
    //     g.drawImage(musicIcon.getImage(), 225, 250, 50, 50, this);

    //     ImageIcon volumeIcon = new ImageIcon("./image/volume.png");
    //     g.drawImage(volumeIcon.getImage(), 225, 450, 50, 50, this);
    // }
// }