import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PageStart extends JPanel {
    private App app;
    private JButton B_setting;
    private JButton B_order;
    private JButton B_shop;
    
    PageStart(App app) {
        this.app = app;
        setLayout(null);
    
        // สร้างปุ่ม setting
        B_setting = new JButton();
        B_setting.setBounds(0, 0, 50, 50);
        B_setting.setOpaque(false); // ทำให้ปุ่มโปร่งแสง
        B_setting.setBorderPainted(false); // ไม่วาดขอบปุ่ม
        B_setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("setting Clicked");
                // app.showPanel("setting");
            }
        });
        add(B_setting);
    
        // สร้างปุ่ม order
        B_order = new JButton("Order");
        B_order.setBounds(0, 70, 50, 50);
        B_order.setOpaque(false);
        B_order.setBorderPainted(false);
        B_order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Order Clicked");
                // app.showPanel("order");
            }
        });
        add(B_order);
    
        // สร้างปุ่ม shop
        B_shop = new JButton("Shop");
        B_shop.setBounds(0, 140, 50, 50);
        B_shop.setOpaque(false);
        B_shop.setBorderPainted(false);
        B_shop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Shop Clicked");
                // app.showPanel("shop");
            }
        });
        add(B_shop);
    }
    
    
    
    @Override
    public void paint(Graphics g) {
        ImageIcon icon_setting = new ImageIcon("./image/settings-white.png");
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        ImageIcon icon_order = new ImageIcon("./image/checklist-white.png");
        ImageIcon icon_shop = new ImageIcon("./image/shop -white.png");
        ImageIcon icon_dish = new ImageIcon("./image/dish.png");
        ImageIcon icon_thermometer = new ImageIcon("./image/thermometer.png");
        ImageIcon icon_Rank = new ImageIcon("./image/rank.png");
        
        super.paint(g);
        g.drawImage(icon.getImage(), 0, 0, this);
        g.drawImage(icon_setting.getImage(), 0, 0, 50, 50, this);
        g.drawImage(icon_order.getImage(), 0, 70, 50, 50, this);
        g.drawImage(icon_shop.getImage(), 0, 140, 50, 50, this);
        g.drawImage(icon_dish.getImage(), 1000, -50, 500, 500, this);
        g.drawImage(icon_thermometer.getImage(), 0, 310, 359, 91, this);
        g.drawImage(icon_Rank.getImage(), 980, 400, 287, 304, this);
    }
}
