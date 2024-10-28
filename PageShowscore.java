import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
public class PageShowscore extends JPanel {
    private App app;
    private JButton backButton;
    PageShowscore(App app){
        this.app = app;
        backButton = new JButton("Back");
        backButton.setBounds(540, 600, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back button clicked!");
                app.getBaseClient().statusConnectServer = false;
            }
        });
        setLayout(null);
        add(backButton);

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("./image/bg-score.png");

        g.drawImage(icon.getImage(), 0, 0, 1280, 720, this);
        Font font = new Font("Tahoma", Font.PLAIN, 30);
        g.setFont(font);
        g.drawString("สรุปอันดับร้านค้า",545,100);

        int x = 520; // ตำแหน่ง x
        int startY = 200; // ตำแหน่ง y เริ่มต้น
        int lineHeight = 30; // ความสูงของแต่ละบรรทัด

       // ดึงข้อมูลผู้เล่นเรียงตามอันดับจาก getPlayerRankings()
       ArrayList<BaseClient> rankedPlayers = app.baseServer.getPlayerRankings();
       

            //วาดข้อความสำหรับแต่ละรายการใน rankedPlayers
            for (int i = 0; i < rankedPlayers.size(); i++) {
                BaseClient player = rankedPlayers.get(i);
                String playerName = player.getNameShop();
                double playerMoney = player.getMoney();
                int rank = i + 1;
                
                // ตั้งค่าและวาดกรอบรอบข้อความ
                g.setColor(new Color(85, 85, 85));
                int rectHeight = lineHeight;
                int rectWidth = playerName.length() * 15; // กำหนดขนาดตามความยาวชื่อผู้เล่น
                int rectX = x - 10;
                int rectY = startY + i * lineHeight - 20;
                g.drawRect(rectX, rectY, rectWidth, rectHeight);
                
                // วาดข้อมูลผู้เล่นในกรอบ
                g.setColor(new Color(0, 0, 0)); // ตั้งสีสำหรับข้อความ
                g.setFont(new Font("Tahoma", Font.PLAIN, 20)); // ตั้งฟอนต์
                
                // ข้อความที่จะแสดง (อันดับ ชื่อ และจำนวนเงิน)
                String displayText = rank + ". " + playerName + " = " + playerMoney + "$";
                int textX = x; // กำหนดตำแหน่ง X ของข้อความให้มีระยะจากกรอบ
                int textY = startY + i * lineHeight;
                g.drawString(displayText, textX, textY);
            }
            
            
        }
    }
    