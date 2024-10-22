import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PageLobby extends JPanel {
    App app;
    ArrayList<String[]> stores = new ArrayList<>();  // เก็บข้อมูลชื่อร้านและสถานะในรูปแบบ ArrayList ของ String[]
    // stores.add(new String[]{"Store 1", "Not Ready"});
    // stores.add(new String[]{"Store 2", "Ready"});
    // stores.add(new String[]{"Store 3", "Not Ready"});
    JPanel show_player;
    // Constructor
    PageLobby(App app) {
        this.app = app;
        setLayout(null);

        JButton back = new JButton("Back");
        back.setBounds(0, 0, 100, 60);
        back.addActionListener(e->app.showPanel("menu"));
        add(back);

        JButton ready = new JButton("พร้อม");
        ready.setFont(new Font("Tahoma",Font.BOLD,14));
        ready.setBounds(600, 600, 150, 60);
        ready.addActionListener(e->{System.out.println("พร้อม");});
        add(ready);

        show_player = new JPanel(new GridLayout(0, 1)); // จัดเรียงร้านแต่ละร้านแถวละ 1 ช่อง
        show_player.setBounds(250, 200, 800, 400);
        show_player.setBackground(Color.GRAY);
        add(show_player);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon("./image/lobby.png");
        g.drawImage(bg.getImage(), 0, 0, this);
    }
}
