import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PageStart extends JPanel {
    private App app;
    private JButton B_setting;
    private JButton B_order;
    private JButton B_shop;
    private Rectangle meatRect;
    private Rectangle plateRect;
    private MeatThread meatThread;
    private boolean isHoldingMeat = false;
    private Point lastMousePosition;
    
    PageStart(App app) {
        this.app = app;
        setLayout(null);
        
        // สร้างพื้นที่ชิ้นเนื้อและจาน
        meatRect = new Rectangle(402, 160, 500, 382); // ขนาดและตำแหน่งชิ้นเนื้อ
        plateRect = new Rectangle(1000, -50, 500, 500); // ขนาดและตำแหน่งจาน

        // สร้างปุ่ม setting
        B_setting = new JButton();
        B_setting.setBounds(0, 0, 50, 50);
        B_setting.setOpaque(false); // ทำให้ปุ่มโปร่งแสง
        B_setting.setBorderPainted(false); // ไม่วาดขอบปุ่ม
        B_setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("setting Clicked");
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
            }
        });
        add(B_shop);
        
        // เริ่มการทำงานของ MeatThread
        meatThread = new MeatThread(this, meatRect);
        meatThread.start();
        
        // Mouse listener เพื่อตรวจจับการลากชิ้นเนื้อ
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (meatRect.contains(e.getPoint())) {
                    isHoldingMeat = true;
                    lastMousePosition = e.getPoint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isHoldingMeat) {
                    isHoldingMeat = false;
                    // ตรวจสอบว่าชิ้นเนื้อชนกับจานหรือไม่
                    if (meatRect.intersects(plateRect)) {
                        System.out.println("Finish! Meat on plate.");
                    }
                }
            }
        });

        // Mouse motion listener เพื่อลากชิ้นเนื้อ
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isHoldingMeat) {
                    int dx = e.getX() - lastMousePosition.x;
                    int dy = e.getY() - lastMousePosition.y;
                    meatRect.x += dx;
                    meatRect.y += dy;
                    lastMousePosition = e.getPoint();
                    repaint(); // รีเฟรชจอภาพเมื่อมีการลากเนื้อ
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // วาด background และปุ่มต่างๆ
        ImageIcon icon_setting = new ImageIcon("./image/settings-white.png");
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        ImageIcon icon_order = new ImageIcon("./image/checklist-white.png");
        ImageIcon icon_shop = new ImageIcon("./image/shop -white.png");
        ImageIcon icon_dish = new ImageIcon("./image/dish.png");
        ImageIcon icon_meat = new ImageIcon("./image/rare.png");
        
        g.drawImage(icon.getImage(), 0, 0, this);
        g.drawImage(icon_setting.getImage(), 0, 0, 50, 50, this);
        g.drawImage(icon_order.getImage(), 0, 70, 50, 50, this);
        g.drawImage(icon_shop.getImage(), 0, 140, 50, 50, this);
        g.drawImage(icon_dish.getImage(), plateRect.x, plateRect.y, plateRect.width, plateRect.height, this); // วาดจาน
        g.drawImage(icon_meat.getImage(), meatRect.x, meatRect.y, meatRect.width, meatRect.height, this); // วาดชิ้นเนื้อ
    }
}

// คลาสสำหรับจัดการการทำงานของเทรด
class MeatThread extends Thread {
    private Rectangle meatRect;
    private JPanel panel;
    private boolean isRunning = true;

    public MeatThread(JPanel panel, Rectangle meatRect) {
        this.panel = panel;
        this.meatRect = meatRect;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(16); // อัพเดตทุก 16 ms (60 FPS)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint(); // รีเฟรชหน้าจอ
        }
    }

    public void stopRunning() {
        isRunning = false; // หยุดการทำงานของเทรด
    }
}