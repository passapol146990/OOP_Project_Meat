import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

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
    private  JDialog orderShow;
    private JPanel contentPanel;
    private JPanel createProductPanel(String imagePath, String productName, String price){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //เพิ่มรูป
        JLabel imagLabel = new JLabel();
        try {
                // Load the original image
                BufferedImage originalImage = ImageIO.read(new File(imagePath));
                
                // Resize the image (adjust the width and height as needed, e.g., 150x150)
                Image resizedImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                
                // Create ImageIcon using the resized image
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                
                // Add the resized image to the JLabel
                imagLabel.setIcon(resizedIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    System.out.println("Image clicked"+ productName);
                    app.getBaseClient().newMeat(productName);
                    meatRect = new Rectangle(402, 160, 400, 300);
                    
                }
            });
    
            // Add the JLabel containing the image to the panel
            panel.add(imagLabel, BorderLayout.CENTER);
    
            //ชื่อสินค้าและราคา
            JLabel namLabel = new JLabel(productName, SwingConstants.CENTER);
            JLabel pricLabel = new JLabel(price, SwingUtilities.CENTER);
            pricLabel.setForeground(Color.GREEN);
    
            JPanel textPanel = new JPanel(new GridLayout(2,1));
            textPanel.add(namLabel);
            textPanel.add(pricLabel);
    
            panel.add(textPanel, BorderLayout.SOUTH);
            return panel;
        }
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
        app.getBaseClient().setTime(300);
        app.getBaseClient().runStartGame();
        app.getSound().playmusic();

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
                // สร้าง popup modal สำหรับการสั่งซื้อ
                JDialog settingsDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Setting", true);
                settingsDialog.setSize(400, 250);
                settingsDialog.setLayout(new BorderLayout());
                settingsDialog.setUndecorated(true); // ป้องกันการขยับ popup

                JLabel orderLabel = new JLabel("Setting", SwingConstants.CENTER);
                settingsDialog.add(orderLabel, BorderLayout.NORTH);

                // สร้าง JPanel สำหรับการตั้งค่า
                JPanel settingsPanel = new JPanel();
                settingsPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // โหลดไอคอนและปรับขนาดให้พอดี (เช่น 50x50)
                ImageIcon AmusicIcon = new ImageIcon("./image/music.png");
                Image musicImage = AmusicIcon.getImage(); // แปลงจาก ImageIcon เป็น Image
                Image resizedMusicImage = musicImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // ปรับขนาด

                // โหลดไอคอน audio และปรับขนาด
                ImageIcon AaudioIcon = new ImageIcon("./image/volume.png");
                Image audioImage = AaudioIcon.getImage();
                Image resizedAudioImage = audioImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                
                // Icon สำหรับ Music และ Audio
                JLabel musicIcon = new JLabel(new ImageIcon(resizedMusicImage)); // ตั้งค่าเส้นทาง icon ของ music
                JLabel audioIcon = new JLabel(new ImageIcon(resizedAudioImage)); // ตั้งค่าเส้นทาง icon ของ audio

                // Slider สำหรับปรับระดับเสียง
                JSlider musicSlider = new JSlider(0, 100, 50);
                musicSlider.setForeground(Color.BLUE);
                musicSlider.addChangeListener(e1 ->{
                    app.getSound().setVolume(musicSlider.getValue());
                    System.out.println(musicSlider.getValue());
                });
                
                JSlider audioSlider = new JSlider(0, 100, 50);
                audioSlider.setForeground(Color.BLUE);
                audioSlider.addChangeListener(e1 ->{
                    System.out.println(musicSlider.getValue());
                });
                
                // Labels สำหรับ slider
                JLabel musicLabel = new JLabel("Music");
                JLabel audioLabel = new JLabel("Audio");

                // ปุ่มสำหรับย้อนกลับ
                JButton backToGameButton = new JButton("Back to the Game");
                JButton backToMenuButton = new JButton("Back to the Menu");

                // เพิ่ม Music Icon และ Slider
                gbc.gridx = 0;
                gbc.gridy = 0;
                settingsPanel.add(musicIcon, gbc);
                
                gbc.gridx = 1;
                gbc.gridy = 0;
                settingsPanel.add(musicLabel, gbc);
                
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                settingsPanel.add(musicSlider, gbc);

                // เพิ่ม Audio Icon และ Slider
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                settingsPanel.add(audioIcon, gbc);
                
                gbc.gridx = 1;
                gbc.gridy = 2;
                settingsPanel.add(audioLabel, gbc);
                
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                settingsPanel.add(audioSlider, gbc);

                // เพิ่มปุ่มกลับ
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 1;
                settingsPanel.add(backToGameButton, gbc);
                backToGameButton.addActionListener(e1 -> settingsDialog.dispose());
                
                gbc.gridx = 1;
                gbc.gridy = 4;
                settingsPanel.add(backToMenuButton, gbc);
                backToMenuButton.addActionListener(e1 -> {
                    // ปิด JDialog
                    settingsDialog.dispose(); 
                    // ปิด ordersShow
                    orderShow.dispose();
                    // เปลี่ยนไปยังหน้าต่างเมนู
                    app.showPanel("menu");
                });

                // เพิ่ม settingsPanel ลงใน JFrame
                settingsDialog.add(settingsPanel);

                // ตั้ง popup ให้อยู่ตรงกลาง
                settingsDialog.setLocationRelativeTo(PageStart.this);
                settingsDialog.setVisible(true);
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
                    // if(path == "./image/meat/01/rare1.png."){
                        app.getBaseClient().setorder_type("01");
                    // }
                    item1.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                orderShow = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "OrderShow", false);
                                orderShow.setBounds(825, 75, 495, 80);
                                orderShow.setLayout(new BorderLayout());
                                orderShow.setUndecorated(true);
                                contentPanel = new JPanel();
                                contentPanel.setBackground(new Color(255,203,151)); // เปลี่ยนสีตามที่ต้องการ
                                contentPanel.setLayout(new BorderLayout());
                                //Test git reset
                                // เพิ่ม item1 เข้าไปใน contentPanel
                                contentPanel.add(item1, BorderLayout.CENTER);
                                
                                // เพิ่ม contentPanel เข้าไปใน JDialog
                                orderShow.setContentPane(contentPanel);
                                orderShow.setVisible(true);
                            }
                    });
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
        B_shop.addActionListener(e -> {
            // popup ร้านค้า            
            JDialog shopDialog = new JDialog( (JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Shop",true);
            shopDialog.setSize(800,470);
            shopDialog.setLayout(new BorderLayout());
            shopDialog.setUndecorated(true);//กันการขยับ popup

            //พาแนลแสดงสินค้า
            JPanel productPanel = new JPanel(new GridLayout(1,3,10,10));
            productPanel.setBackground(Color.LIGHT_GRAY);
        

            //สินค้า 1
            JPanel meatPanel = createProductPanel("./image/rare.png", "เนื้อวัว", "50$");
            meatPanel.setBounds(meatRect.x, meatRect.y, 235, 150);
            
            productPanel.add(meatPanel);

            // วากิว
            JPanel wagyuPanel = createProductPanel("./image/wagyuu.png", "เนื้อวากิว", "105$");
            wagyuPanel.setBounds(meatRect.x, meatRect.y, 235, 150);
            productPanel.add(wagyuPanel);

            // สันกลาง
            JPanel ribeyePanel = createProductPanel("./image/sungarng.png", "เนื้อสันกลาง", "90$");
            ribeyePanel.setPreferredSize(new Dimension(235,150));
            ribeyePanel.setLocation(meatRect.x, meatRect.y);
            productPanel.add(ribeyePanel);


            //ปุ่ม back
            JButton backButton = new JButton("Back");
            
            backButton.addActionListener(e1 -> shopDialog.dispose());

            // ตั้ง layout และเพิ่มเนื้อหา
            shopDialog.add(productPanel, BorderLayout.CENTER);
            shopDialog.add(backButton, BorderLayout.SOUTH);

            //ตั้งpopup ให้อยู่ตรงกลาง
            shopDialog.setLocationRelativeTo(PageStart.this);
            shopDialog.setVisible(true);
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
                        int chk = app.getBaseClient().chkMeat();
                        if(chk == 1){
                            contentPanel.setBackground(new Color(182,255,162));
                            contentPanel.revalidate(); // ทำให้ layout ของ containerPanel ถูกประมวลผลใหม่
                            contentPanel.repaint(); // วาด containerPanel ใหม่ทั้งหมด
                        }
                        else
                        {
                            contentPanel.setBackground(Color.black);
                            contentPanel.revalidate(); // ทำให้ layout ของ containerPanel ถูกประมวลผลใหม่
                            contentPanel.repaint();
                        }
                        app.getBaseClient().getMeat().kill();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        orderShow.dispose();
                        //System.out.println("Finish! Meat on Dish.");
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

        // เงิน
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString(app.getBaseClient().getMoney()+"$", 10, 660);
        // เวลา
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(app.getBaseClient().getFormatTime(), 620, 25);

        List<String> lists = new ArrayList<String>();
        lists.add("Test1");
        lists.add("Test2");
        lists.add("Test3");
        lists.add("Test4");
        lists.add("Test5");
        int x = 1000; // ตำแหน่ง x
        int startY = 480; // ตำแหน่ง y เริ่มต้น
        int lineHeight = 30; // ความสูงของแต่ละบรรทัด

    // วาดข้อความสำหรับแต่ละรายการใน lists
    for (int i = 0; i < lists.size(); i++) {
        String s = lists.get(i);
        int rank = i + 1;
        g.setColor(new Color(85,85,85));
        int rectHeight = lineHeight; // ปรับขนาดกรอบให้มีความสูงพอดีกับข้อความ
        g.drawRect(x, startY + i * lineHeight - 20, s.length() * 30, rectHeight); // วาดกรอบสำหรับข้อความ
        g.setColor(new Color(0, 0, 0)); // ตั้งค่าสี
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // ตั้งฟอนต์
        // วาดบรรทัด
        g.drawString(rank + "   " + s + "   " + app.getBaseClient().getMoney() + "$", x, startY + i * lineHeight);
    }
        // วาดข้อความการจัดอันดับรวม
        String rankingText = "Your ranking: " + app.getBaseClient().getMoney() + "$";
        g.drawString(rankingText, x, startY + lists.size() * lineHeight + 30); // วางหลังรายการ

        for(int i=0;i<3;i++){
            // ผู้เล่น
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString(app.getBaseClient().getFormatTime(), 1000, 480+i*50);
        }
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

