import java.awt.*;
import java.io.IOException;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class PageSeting extends JPanel{
    private App app;
    public PageSeting(App app) {
        setSize(1200, 800);
        setLayout(new BorderLayout());
        settingOutTheGame settingPanel = new settingOutTheGame(app);
        add(settingPanel, BorderLayout.CENTER);
        setVisible(true);   
    }
}

class settingOutTheGame extends JPanel{
    private JSlider musicSlider;
    private JSlider audioSlider;
    private JLabel music;
    private JLabel audio;
    private App app;
    public settingOutTheGame(App app) {
        setLayout(null);
        // สร้างแผงหลัก
        JPanel panel = new JPanel();    
        panel.setLayout(null);
        panel.setBounds(170, 180, 911, 420);
        panel.setBackground(new Color(	217, 217, 217));

        // สร้างปุ่ม "Back to the menu"
        JButton backButton = Component.createCustomRoundedButton("Back to the menu", Color.pink);
        backButton.setBounds(480, 530, 300, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.getBaseClient().nowPage = "menu";
            }
        });
        add(backButton);

        // สร้าง JSlider สำหรับ music และ audio
        musicSlider = Component.createCustomSlider(app.getSound().getVolumeMusic());
        musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider
        musicSlider.addChangeListener(e ->{
            // sound.setVolume(musicSlider.getValue()); // เชื่อมต่อกับการเปลี่ยนแปลงค่า
        });

        music = new JLabel("Music");
        music.setBounds(145, 100, 1000, 50);
        music.setFont(new Font("Courier New", Font.BOLD, 50));

        audioSlider = Component.createCustomSlider(app.getSound().getVolumeEffect());
        audioSlider.setBounds(300, 250, 500, 50);
        
        audio = new JLabel("Audio");
        audio.setBounds(145, 250, 1000, 50);
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // วาดพื้นหลังและไอคอน
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        g.drawImage(icon.getImage(), 0, 0, this);

        ImageIcon bannerIcon = new ImageIcon("./image/BannerSeting.png");
        g.drawImage(bannerIcon.getImage(), 300, 0, this);
    }
}