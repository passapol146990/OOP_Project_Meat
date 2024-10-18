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
    private RunRepaint runRepaint;
    private boolean isHoldingMeat = false;
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
                app.getBaseClient().newMeat();
                app.getBaseClient().delMoney(50);
                // Meat and plate areas
                meatRect = new Rectangle(402, 160, 400, 300); // Meat size and position
                plateRect = new Rectangle(1000, -50, 100, 100); // Plate size and position
            }
        });
        add(B_shop);

        
        runRepaint = new RunRepaint(this); 
        runRepaint.start();

        
        addMouseListener(new MouseAdapter() {
            //กดพลิกเนื้อ ยังไม่ได้ทำเพิ่มนะ
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(meatRect.contains(e.getPoint())){
                    System.out.println("click meat");
                    ClickMeat clickMeat = new ClickMeat(app.getBaseClient().getMeat());
                    clickMeat.start();
                }
            }
            @Override
            //กดยกเนื้อ
            public void mousePressed(MouseEvent e) {
                if (meatRect.contains(e.getPoint())) {
                    isHoldingMeat = true;
                    lastMousePosition = e.getPoint();
                }
            }
            //เช็คว่าเนื้ออยู่จานมั้ย
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isHoldingMeat) {
                    isHoldingMeat = false;
                    // Check if meat intersects with plate
                    if (meatRect.intersects(plateRect)) {
                        app.getBaseClient().getMeat().kill();
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
                if (isHoldingMeat) {
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
        ImageIcon icon_Rank = new ImageIcon("./image/rank.png");
        g.drawImage(icon.getImage(), 0, 0, this);
        g.drawImage(icon_setting.getImage(), 0, 0, 50, 50, this);
        g.drawImage(icon_order.getImage(), 0, 70, 50, 50, this);
        g.drawImage(icon_shop.getImage(), 0, 140, 50, 50, this);
        g.drawImage(icon_dish.getImage(), plateRect.x, plateRect.y, 500, 500, this);
        g.drawImage(icon_Rank.getImage(), 980, 400, 287, 304, this);
        // ถ้ามีเนื้อใน baseClient
        // System.out.println(app.getBaseClient().getMoney());
        if(this.app.getBaseClient().getMeat()!=null&&this.app.getBaseClient().getMeat().getImage()!=null){
            ImageIcon icon_meat = new ImageIcon(this.app.getBaseClient().getMeat().getImage());
            g.drawImage(icon_meat.getImage(), meatRect.x, meatRect.y, 500, 382, this);
        }
    }
}
class RunRepaint extends Thread{
    private boolean status = true;
    private JPanel panel;
    RunRepaint(JPanel panel){
        this.panel = panel;
    }
    public void run(){
        while (this.status) {
            this.panel.repaint();
            try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    void kill(){
        this.status = false;
    }
}