import java.awt.event.*;

import javax.swing.*;
import java.awt.*;

public class PageStart extends JPanel {
    private App app;
    private Sound sound;
    private JButton B_setting;
    private JButton B_order;
    private JButton B_shop;
    private Rectangle meatRect;
    private Rectangle plateRect;
    private RunRepaint runRepaint;
    private boolean isHoldingMeat = false;
    private Point lastMousePosition;
    private JPanel createOrderItemPanel(String imagePath, String description, String price) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                super.paintComponent(g);
            }
        };
        
        panel.setOpaque(false); // ทำให้พาเนลโปร่งใสเพื่อแสดงขอบโค้งได้ชัดเจน
        panel.setLayout(new BorderLayout());
    
        // โหลดรูปภาพและปรับขนาดให้พอดีกับพื้นที่
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // ปรับขนาดรูปภาพ
        JLabel imageLabel = new JLabel(new ImageIcon(image));
    
        // พาเนลซ้าย: รูปภาพสินค้า
        panel.add(imageLabel, BorderLayout.WEST);
    
        // พาเนลกลาง: คำอธิบายสินค้า (ใช้ JTextArea สำหรับการตัดบรรทัด)
        JTextArea descTextArea = new JTextArea(description);
        descTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12)); // ตั้งฟอนต์ที่รองรับภาษาไทย
        descTextArea.setLineWrap(true); // เปิดใช้งานการตัดบรรทัด
        descTextArea.setWrapStyleWord(true); // ตัดบรรทัดที่เว้นวรรค
        descTextArea.setOpaque(false); // ตั้งค่าให้โปร่งใสเพื่อให้ดูเหมือน JLabel
        descTextArea.setEditable(false); // ปิดการแก้ไข
    
        panel.add(descTextArea, BorderLayout.CENTER);
    
        // พาเนลขวา: ราคา (เพิ่มการเว้นขอบด้วย EmptyBorder)
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 14)); // ตั้งฟอนต์ที่รองรับภาษาไทย
        priceLabel.setForeground(Color.GREEN);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // เว้นขอบขวา 10px
    
        panel.add(priceLabel, BorderLayout.EAST);

        // เพิ่ม MouseListener ให้กับพาเนลเพื่อตรวจจับการคลิก
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("Selected Price: " + price); // พิมพ์ราคาที่เลือกเมื่อคลิก
            }
        });
    
        // เพิ่มเส้นขอบโค้ง
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // เพิ่มระยะห่างจากขอบพาเนลเล็กน้อย
    
        return panel;
    }
    
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
                JDialog settingsDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Settings", true);
                settingsDialog.setSize(400, 300); // ขนาดของ dialog
                settingsDialog.setLayout(new BorderLayout());

                // สร้าง instance ของ settingInTheGame
                 settingInTheGame settingsPanel = new settingInTheGame(sound, app);
                 settingsDialog.add(settingsPanel, BorderLayout.CENTER); // เพิ่ม settingsPanel ลงใน dialog

                 // ตั้งตำแหน่งของ dialog ให้อยู่ตรงกลาง
                 settingsDialog.setLocationRelativeTo(PageStart.this);
                 settingsDialog.setVisible(true); // แสดง dialog
            }
        });
        add(B_setting);

        // ปุ่ม Order
        B_order = new JButton("Order");
        B_order.setBounds(0, 70, 50, 50);
        B_order.setOpaque(false);
        B_order.setBorderPainted(false);
        B_order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // สร้าง popup modal สำหรับการสั่งซื้อ
                JDialog orderDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Order", true);
                orderDialog.setSize(350, 500);
                orderDialog.setLayout(new BorderLayout());
                orderDialog.setUndecorated(true); // ป้องกันการขยับ popup

                JLabel orderLabel = new JLabel("SHOP", SwingConstants.CENTER);
                orderDialog.add(orderLabel, BorderLayout.NORTH);
                JButton order = new JButton("close");
                orderDialog.add(order, BorderLayout.CENTER);

                // พาเนลแสดงสินค้า
                JPanel outerPanel = new JPanel(new BorderLayout());
                outerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // ตั้ง padding ซ้าย 20 และขวา 20

                JPanel productPanel = new JPanel(new GridLayout(5, 1, 10, 10));
                String path = "./image/meat/01/rare1.png.";
                for(int order_count = 0;order_count<5;order_count++){
                    // สินค้า
                    JPanel item1 = createOrderItemPanel(path, "เนื้อวัวย่าง ระดับความสุขที่ medium rare อุณหภูมิ 150 องศา", "+100$");
                    productPanel.add(item1);
                }
                // ปุ่ม back
                JButton backButton = new JButton("back");
                backButton.addActionListener(e1 -> orderDialog.dispose());

                // ตั้ง layout และเพิ่มเนื้อหา
                // เพิ่ม productPanel เข้าไปใน outerPanel
                outerPanel.add(productPanel, BorderLayout.CENTER);
                // ตั้ง outerPanel เป็นเนื้อหาหลักแทน
                orderDialog.add(outerPanel, BorderLayout.CENTER);
                orderDialog.add(backButton, BorderLayout.SOUTH);

                // ตั้ง popup ให้อยู่ตรงกลาง
                orderDialog.setLocationRelativeTo(PageStart.this);
                orderDialog.setVisible(true);
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
                if(app.getBaseClient().getMoney()<=0){
                    System.out.println("คุณมีเงิน "+app.getBaseClient().getMoney()+" ไม่สามารถซื้อเนื้อมาทำอาหารได้");
                }else{
                    app.getBaseClient().newMeat();
                    app.getBaseClient().delMoney(50);
                    // Meat and plate areas
                    meatRect = new Rectangle(402, 160, 400, 300); // Meat size and position
                    plateRect = new Rectangle(1000, -50, 100, 100); // Plate size and position
                }
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

        g.setColor(new Color(255,255,255));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString(""+app.getBaseClient().getMoney()+"$", 10, 660);
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
    private boolean isRunning = true;
    private Sound grillSound;  // เรียกไปยัง class sound
    RunRepaint(JPanel panel){
        this.panel = panel;
    }
    public void run(){
        while (this.status) {
            this.panel.repaint();
            try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }

        grillSound.stopSound();  // ปิดเสียงเมื่อ ออก loop
    }

    public void stopRunning() {
        isRunning = false;  //หยุดการทำงาน thread
    }
    void kill(){
        this.status = false;
    }
}

