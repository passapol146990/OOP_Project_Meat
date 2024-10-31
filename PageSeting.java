import java.awt.*;
import java.io.IOException;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

class PageSeting extends JPanel{
    private JSlider musicSlider;
    private JSlider audioSlider;
    private JLabel music;
    private JLabel audio;
    private App app;
    public PageSeting(App app) {
        //กำหนดค่าเริ่มต้น และเรียก methods สำหรับสร้างหน้าตั้งค่า
            this.app = app;
            create();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // วาดพื้นหลังและไอคอน
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        g.drawImage(icon.getImage(), 0, 0, this);

        ImageIcon bannerIcon = new ImageIcon("./image/Component/BannerSeting.png");
        g.drawImage(bannerIcon.getImage(), 300, 0, this);
    }
    void create(){
        // System.out.println(app.getSound().getVolumeMusic());
        setLayout(null);
        // สร้าง JPanel สำหรับการตั้งค่า
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBounds(400, 200, 500, 420);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // โหลดไอคอนและปรับขนาดให้พอดี (เช่น 50x50)
        ImageIcon AmusicIcon = new ImageIcon("./image/music.png");
        Image musicImage = AmusicIcon.getImage(); // แปลงจาก ImageIcon เป็น Image
        Image resizedMusicImage = musicImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        // โหลดไอคอน audio และปรับขนาด
        ImageIcon AaudioIcon = new ImageIcon("./image/volume.png");
        Image audioImage = AaudioIcon.getImage();
        Image resizedAudioImage = audioImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        
        // Icon สำหรับ Music และ Audio
        JLabel musicIcon = new JLabel(new ImageIcon(resizedMusicImage));
        JLabel audioIcon = new JLabel(new ImageIcon(resizedAudioImage));

        // Slider สำหรับปรับระดับเสียงเพลง
        JSlider musicSlider = new JSlider(0, 100, app.getSound().getVolumeMusic());
        musicSlider.setForeground(Color.BLUE);
        musicSlider.addChangeListener(e1 ->{
            app.getFile().saveVolumeMusic(musicSlider.getValue());
            app.getSound().setVolumeMusic(musicSlider.getValue());
        });
        // Slider สำหรับปรับระดับเสียงย่างเนื้อ
        JSlider audioSlider = new JSlider(0, 100, app.getSound().getVolumeEffect());
        audioSlider.setForeground(Color.BLUE);
        audioSlider.addChangeListener(e1 ->{
            app.getFile().saveVolumeEffect(audioSlider.getValue());
            app.getSound().setVolumeEffect(audioSlider.getValue());
        });
        
        // Labels สำหรับ slider
        JLabel musicLabel = new JLabel("Music");
        JLabel audioLabel = new JLabel("Audio");

        // เพิ่ม Music Icon และ Slider
        gbc.gridx = 0;
        gbc.gridy = 0;
        settingsPanel.add(musicIcon, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        settingsPanel.add(musicLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        settingsPanel.add(musicSlider, gbc);

        // เพิ่ม Audio Icon และ Slider
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        settingsPanel.add(audioIcon, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        settingsPanel.add(audioLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        settingsPanel.add(audioSlider, gbc);
        settingsPanel.setVisible(true);
        // ปุ่มสำหรับย้อนกลับ
        JButton backToMenuButton = Component.createCustomRoundedButton("Back to the Menu",Color.white);
        backToMenuButton.setBounds(0, 0, 200, 50);
        backToMenuButton.addActionListener(e1 -> {
            // System.out.println(app.getSound().getVolumeMusic());
            app.getBaseClient().nowPage = "menu";
        });
        // เพิ่ม settingsPanel ลงใน JFrame
        add(settingsPanel);
        add(backToMenuButton);
        setPreferredSize(new Dimension(1200, 800));
    }
}