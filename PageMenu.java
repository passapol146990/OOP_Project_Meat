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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // ขยายช่องว่างระหว่างปุ่ม
        
        // ช่องสำหรับใส่ IP
        JLabel ipLabel = new JLabel("Server IP:");
        ipLabel.setForeground(Color.WHITE); // เปลี่ยนสีตัวหนังสือเป็นสีขาว
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // จัดให้ชิดขวา
        add(ipLabel, gbc);

        ipField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // จัดให้ชิดซ้าย
        add(ipField, gbc);

        // ช่องสำหรับใส่ชื่อร้าน
        JLabel storeNameLabel = new JLabel("Store Name:");
        storeNameLabel.setForeground(Color.WHITE); // เปลี่ยนสีตัวหนังสือเป็นสีขาว
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(storeNameLabel, gbc);

        storeNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(storeNameField, gbc);

        // ปุ่มเข้าสู่เกม
        JButton loginButton = new JButton("Start");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER; // จัดให้ตรงกลาง
        loginButton.setPreferredSize(new Dimension(120, 40)); // กำหนดขนาดปุ่ม
        add(loginButton, gbc);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                String storeName = storeNameField.getText();
                app.showPanel("start");
            }
        });

        // ปุ่ม Setting
        JButton settingButton = new JButton("Setting");
        gbc.gridx = 1;
        gbc.gridy = 4;
        settingButton.setPreferredSize(new Dimension(120, 40));
        add(settingButton, gbc);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.showPanel("seting");
            }
        });

        // ปุ่ม About
        JButton aboutButton = new JButton("About");
        gbc.gridx = 1;
        gbc.gridy = 5;
        aboutButton.setPreferredSize(new Dimension(120, 40));
        add(aboutButton, gbc);
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.showPanel("about");
            }
        });

        // ปุ่ม Exit
        JButton exitButton = new JButton("Exit");
        gbc.gridx = 1;
        gbc.gridy = 6;
        exitButton.setPreferredSize(new Dimension(120, 40));
        add(exitButton, gbc);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("./image/gif.gif");
        ImageIcon plate = new ImageIcon("./image/plate.png");
        g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(plate.getImage(), 50, -200, this.getWidth(), this.getHeight(), this);
    }
}
