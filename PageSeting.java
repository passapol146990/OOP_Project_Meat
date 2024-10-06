import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class PageSeting extends JPanel{
    PageSeting(){
        setBackground(Color.white);
        setLayout(null);
        JPanel panel = new JPanel();
        JButton b = new JButton("Back to the menu");
        panel.setBounds(170, 180, 911, 420);
        panel.setBackground(Color.black);
        b.setBounds(480, 530, 300, 50);
        add(b);
        add(panel);
    }
    @Override
    public void paint(Graphics g) {
        ImageIcon icon = new ImageIcon("./image/BannerSeting.png");
        super.paint(g);
        g.drawImage(icon.getImage(), 300,0, this);
        icon = new ImageIcon("./image/settings-back.png");
        g.drawImage(icon.getImage(), 10,0, this);
        icon = new ImageIcon("./image/settings-back.png");
        g.drawImage(icon.getImage(), 10,0, this);
    }
}