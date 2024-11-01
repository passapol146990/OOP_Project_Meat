import java.awt.*;
import javax.swing.*;
public class PageMenu extends JPanel {
    FileHandler file;
    App app;
    private JTextField ipField;
    private JTextField storeNameField;
    PageMenu(App app) {
        this.app = app;
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        /////////////////////////////////Show IP Address & Name ///////////////////////////////////////////////////
        JTextField ipField = new JTextField(app.getFile().getIp());
        ipField.setHorizontalAlignment(JTextField.CENTER);
        ipField.setBackground(Color.LIGHT_GRAY);
        ipField.setForeground(Color.BLACK);
        ipField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(ipField, gbc);
        JTextField nameField = new JTextField(app.getFile().getName());
        nameField.setFont(new Font("Tahoma", Font.BOLD, 14));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(Color.LIGHT_GRAY);
        nameField.setForeground(Color.BLACK);
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(nameField, gbc);
        //////////////////////////////////////Button//////////////////////////////////////////
        JButton startButton = Component.createCustomRoundedButton("Start", Color.white);
        JButton settingButton = Component.createCustomRoundedButton("Setting",Color.white);
        JButton aboutButton = Component.createCustomRoundedButton("About",Color.white);
        JButton exitButton = Component.createCustomRoundedButton("Exit",Color.white);
        // ปรับขนาดปุ่ม
        Dimension buttonSize = new Dimension(200, 40);
        startButton.setPreferredSize(buttonSize);
        settingButton.setPreferredSize(buttonSize);
        aboutButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        startButton.addActionListener(e->{
            if(!app.getBaseClient().statusConnectServer){
                app.getFile().saveIp(ipField.getText());
                app.getBaseClient().setNameShop(nameField.getText());
                app.getFile().saveName(nameField.getText());
                app.getBaseClient().statusConnectServer = true;
                ConnectServer conn = new ConnectServer(app, ipField.getText(), 3333);
                conn.start();
            }
        });
        settingButton.addActionListener(e->{
            ((PageSeting)app.getPanel("seting")).create();
            app.getBaseClient().nowPage = "seting";
        });
        aboutButton.addActionListener(e->{
            app.getBaseClient().nowPage = "about";
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



