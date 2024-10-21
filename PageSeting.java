import java.awt.*;
import java.io.IOException;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class PageSeting extends JPanel{
    private Sound sound;
    private App app;
    public PageSeting(Sound sound,App app) {
        this.sound = sound;
        setSize(1200, 800);
        setLayout(new BorderLayout());
        settingOutTheGame settingPanel = new settingOutTheGame(sound,app);
        add(settingPanel, BorderLayout.CENTER);
        setVisible(true);   
    }
}


class settingOutTheGame extends JPanel{
    private JSlider musicSlider;
    private JSlider audioSlider;
    private JLabel music;
    private JLabel audio;
    private Sound sound;
    private App app;
    public settingOutTheGame(Sound sound,App app) {
        this.sound = sound;
        setLayout(null);
        // สร้างแผงหลัก
        JPanel panel = new JPanel();    
        panel.setLayout(null);
        panel.setBounds(170, 180, 911, 420);
        panel.setBackground(new Color(85,85,85));

        // สร้างปุ่ม "Back to the menu"
        JButton backButton = Component.createCustomRoundedButton("Back to the menu", Color.pink);
        backButton.setBounds(480, 530, 300, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.showPanel("menu");
            }
        });
        add(backButton);

        // สร้าง JSlider สำหรับ music และ audio
        musicSlider = Component.createCustomSlider();
        musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider
        musicSlider.addChangeListener(e ->{
                sound.setVolume(musicSlider.getValue()); // เชื่อมต่อกับการเปลี่ยนแปลงค่า
            });

        music = new JLabel("Music");
        music.setBounds(145, 100, 1000, 50);
        music.setFont(new Font("Courier New", Font.BOLD, 50));

        audioSlider = Component.createCustomSlider();
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

class settingInTheGame extends JPanel{
     private JSlider musicSlider;
     private JSlider audioSlider;
     private JLabel music;
     private JLabel audio;
     private Sound sound;

     public settingInTheGame(Sound sound,App app) {
        this.sound = sound;
        setLayout(null);

         // สร้างแผงหลัก
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(170, 180, 900, 420);
        panel.setBackground(new Color(85, 85, 85));

    //      //สร้างปุ่ม "Back to the game"
          JButton backGameButton = Component.createCustomRoundedButton("Back to the game", Color.pink);
          backGameButton.setBounds(200,530,300,50);
          backGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.showPanel("start");
            }
        });
          add(backGameButton);

         // สร้างปุ่ม "Back to the menu"
         JButton backButton = Component.createCustomRoundedButton("Back to the menu", Color.pink);
         backButton.setBounds(750, 530, 300, 50);
         backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app != null) {
                    app.showPanel("menu"); // แสดงหน้าเมนู
                } else {
                    System.out.println("App instance is null");
                }
            }
        });
         add(backButton);


        // สร้าง JSlider สำหรับ music และ audio
            musicSlider = Component.createCustomSlider();
            musicSlider.setBounds(300, 100, 500, 50);  // ตั้งตำแหน่ง slider
            musicSlider.addChangeListener(e ->{
            sound.setVolume(musicSlider.getValue()); // เชื่อมต่อกับการเปลี่ยนแปลงค่า
            System.out.println(musicSlider.getValue());
             });
         music = new JLabel("Music");
         music.setBounds(150, 100, 1000, 50);
         music.setFont(new Font("Courier New", Font.BOLD, 50));

         audioSlider = Component.createCustomSlider();
         audioSlider.setBounds(300, 250, 500, 50);
        
         audio = new JLabel("Audio");
         audio.setBounds(150, 250, 1000, 50);
         audio.setFont(new Font("Courier New", Font.BOLD, 50));
         
         ImageIcon Music = new ImageIcon("./image/music.png");
        JLabel musicIcon = new JLabel(new ImageIcon(Music.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        musicIcon.setBounds(50, 100, 50, 50);
        
        ImageIcon Volume = new ImageIcon("./image/volume.png");
        JLabel volumeIcon = new JLabel(new ImageIcon(Volume.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        volumeIcon.setBounds(50, 250, 50, 50);
        // เพิ่ม slider และ label ในแผง
        panel.add(musicSlider);
        panel.add(music);
        panel.add(musicIcon); // เพิ่มไอคอนลงใน panel
        panel.add(audioSlider);
        panel.add(audio);
        panel.add(volumeIcon); // เพิ่มไอคอนลงใน panel
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