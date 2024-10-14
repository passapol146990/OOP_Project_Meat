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
    private boolean isMeatOnPlate = false; // Track if the meat is on the plate
    private Point lastMousePosition;

    PageStart(App app) {
        this.app = app;
        setLayout(null);

        // Meat and plate areas
        meatRect = new Rectangle(402, 160, 400, 300); // Meat size and position
        plateRect = new Rectangle(1000, -50, 100, 100); // Plate size and position

        // Setting button
        B_setting = new JButton();
        B_setting.setBounds(0, 0, 50, 50);
        B_setting.setOpaque(false);
        B_setting.setBorderPainted(false);
        B_setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Setting Clicked");
            }
        });
        add(B_setting);

        // Order button
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

        // Shop button
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

        
        meatThread = new MeatThread(this, meatRect);
        meatThread.start();

        // Mouse listener to drag meat
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (meatRect.contains(e.getPoint()) && !isMeatOnPlate) {
                    isHoldingMeat = true;
                    lastMousePosition = e.getPoint();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (meatRect.contains(e.getPoint()) && !isMeatOnPlate) {
                    isHoldingMeat = true;
                    lastMousePosition = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isHoldingMeat) {
                    isHoldingMeat = false;
                    // Check if meat intersects with plate
                    if (meatRect.intersects(plateRect)) {
                        isMeatOnPlate = true;
                        meatThread.stopRunning(); 
                        System.out.println("Finish! Meat on Dish.");
                    }
                    else{
                        meatRect.x = 402;
                        meatRect.y = 160;
                    }
                }
            }
        });

        // Mouse motion listener to drag meat
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isHoldingMeat && !isMeatOnPlate) {
                    int dx = e.getX() - lastMousePosition.x;
                    int dy = e.getY() - lastMousePosition.y;
                    meatRect.x += dx;
                    meatRect.y += dy;
                    lastMousePosition = e.getPoint();
                    repaint(); // Refresh the screen when dragging
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        
        ImageIcon icon_setting = new ImageIcon("./image/settings-white.png");
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        ImageIcon icon_order = new ImageIcon("./image/checklist-white.png");
        ImageIcon icon_shop = new ImageIcon("./image/shop -white.png");
        ImageIcon icon_dish = new ImageIcon("./image/dish.png");
        ImageIcon icon_meat = new ImageIcon("./image/rare.png");
        ImageIcon icon_Rank = new ImageIcon("./image/rank.png");
        g.drawImage(icon.getImage(), 0, 0, this);
        g.drawImage(icon_setting.getImage(), 0, 0, 50, 50, this);
        g.drawImage(icon_order.getImage(), 0, 70, 50, 50, this);
        g.drawImage(icon_shop.getImage(), 0, 140, 50, 50, this);
        g.drawImage(icon_dish.getImage(), plateRect.x, plateRect.y, 500, 500, this);
        g.drawImage(icon_Rank.getImage(), 980, 400, 287, 304, this);
        
        if (!isMeatOnPlate) {
            g.drawImage(icon_meat.getImage(), meatRect.x, meatRect.y, 500, 382, this); 
        }
    }
}
class meat_icon{
    String[] meat_pic = {
        "image/rare.png",
        "image/medium rare.png",
        // เดี๋ยวมาเพิ่มเอาเดอ
    };
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
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint(); // Refresh the screen
        }
    }

    public void stopRunning() {
        isRunning = false; 
    }
}
