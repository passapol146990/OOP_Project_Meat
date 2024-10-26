import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PageMenu extends JPanel {
    App app;
    private JTextField ipField;
    private JTextField storeNameField;
    
    PageMenu(App app) {
        this.app = app;
        // สร้าง JPanel และตั้ง Layout แบบ GridBagLayout
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // แสดง IP Address
        JTextField ipField = new JTextField("127.0.0.1");
        ipField.setHorizontalAlignment(JTextField.CENTER);
        ipField.setBackground(Color.LIGHT_GRAY);
        ipField.setForeground(Color.BLACK);
        ipField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(ipField, gbc);

        // สร้างช่องกรอกชื่อ
        JTextField nameField = new JTextField("ตั้งชื่อร้านของคุณ");
        nameField.setFont(new Font("Tahoma", Font.BOLD, 14));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(Color.LIGHT_GRAY);
        nameField.setForeground(Color.BLACK);
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(nameField, gbc);

        // สร้างปุ่มและตั้งค่า GridBagConstraints ให้ปุ่มอยู่ตรงกลาง
        JButton startButton = new JButton("Start");
        JButton settingButton = new JButton("Setting");
        JButton aboutButton = new JButton("About");
        JButton exitButton = new JButton("Exit");

        // ปรับขนาดปุ่ม
        Dimension buttonSize = new Dimension(200, 40);
        startButton.setPreferredSize(buttonSize);
        settingButton.setPreferredSize(buttonSize);
        aboutButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        startButton.addActionListener(e->{
            if(!app.getBaseClient().statusConnectServer){
                app.getBaseClient().setNameShop(nameField.getText());
                app.getBaseClient().statusConnectServer = true;
                PageLobby lobby = new PageLobby(app);
                app.addPanel(lobby, "lobby");
                ConnectServer conn = new ConnectServer(app, ipField.getText(), 3333);
                conn.start();
            }
        });
        settingButton.addActionListener(e->{
            PageSeting seting = new PageSeting(app);
            app.addPanel(seting, "seting");
            app.showPanel("seting");
        });
        aboutButton.addActionListener(e->{
            PageAbout about = new PageAbout(app);
            app.addPanel(about, "about");
            app.showPanel("about");
        });
        exitButton.addActionListener(e->System.exit(0));

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(startButton, gbc);

        gbc.gridy = 4;
        add(settingButton, gbc);

        gbc.gridy = 5;
        add(aboutButton, gbc);

        gbc.gridy = 6;
        add(exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon("./image/gif.gif");
        ImageIcon plate = new ImageIcon("./image/plate.png");
        g.drawImage(bg.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(plate.getImage(), 0, -200, this.getWidth(), this.getHeight(), this);
    }
}



