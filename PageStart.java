import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.util.Random;
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
    boolean showTemp = false;

    private JPanel contentPanel;
    private JPanel item1[] = new JPanel[5];
    private int price[] = new int[5];
    private Random random = new Random();
    private int indexs;
    private JPanel createProductPanel(String imagePath, String productName, int price, JDialog Jdialog){
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
                    app.getBaseClient().newMeat(productName,price);
                    meatRect = new Rectangle(402, 160, 400, 300);
                    Jdialog.dispose();
                }
            });
    
            // Add the JLabel containing the image to the panel
            panel.add(imagLabel, BorderLayout.CENTER);
    
            //ชื่อสินค้าและราคา
            JLabel namLabel = new JLabel(productName, SwingConstants.CENTER);
            JLabel pricLabel = new JLabel(String.format("%d$", price), SwingUtilities.CENTER);
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
        // app.getSound().playmusic();

        // Meat and plate areas
        meatRect = new Rectangle(402, 160, 400, 300); 
        plateRect = new Rectangle(1000, -50, 100, 100);

        // Setting button
        B_setting = new JButton();
        B_setting.setBounds(0, 0, 50, 50);
        B_setting.setOpaque(false);
        B_setting.setBorderPainted(false);
        B_setting.addActionListener(e-> {
            // สร้าง popup modal สำหรับการสั่งซื้อ
            JDialog settingsDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Setting", true);
            settingsDialog.setSize(400, 250);
            settingsDialog.setLayout(new BorderLayout());
            settingsDialog.setUndecorated(true);

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
            Image resizedMusicImage = musicImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

            // โหลดไอคอน audio และปรับขนาด
            ImageIcon AaudioIcon = new ImageIcon("./image/volume.png");
            Image audioImage = AaudioIcon.getImage();
            Image resizedAudioImage = audioImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            
            // Icon สำหรับ Music และ Audio
            JLabel musicIcon = new JLabel(new ImageIcon(resizedMusicImage));
            JLabel audioIcon = new JLabel(new ImageIcon(resizedAudioImage));

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
                app.getBaseClient().statusConnectServer = false;
                app.getBaseClient().statusReady = false;
                settingsDialog.dispose();
            });

            // เพิ่ม settingsPanel ลงใน JFrame
            settingsDialog.add(settingsPanel);

            // ตั้ง popup ให้อยู่ตรงกลาง
            settingsDialog.setLocationRelativeTo(PageStart.this);
            settingsDialog.setVisible(true);
        });
        add(B_setting);

        // ปุ่ม รับเดอร์/Order
        B_order = new JButton("Order");
        B_order.setBounds(0, 70, 50, 50);
        B_order.setOpaque(false);
        B_order.setBorderPainted(false);
        B_order.addActionListener(e-> {
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

            ArrayList<HashMap<String,String>> dataOrder =  this.app.getBaseClient().getOrder();
            for(int i=0;i<dataOrder.size();i++){
                productPanel.add(createOrderItemPanel(dataOrder.get(i).get("image"),dataOrder.get(i).get("title"),String.format("+%s$", dataOrder.get(i).get("price"))));
            }
            // String path = "./image/meat/01/medium_rare1.png";
            // for(int order_count = 0;order_count<5;order_count++){
            //     // สินค้า
            //     price[order_count] = random.nextInt(350 - 100);
            //     item1[order_count] = new JPanel();
            //     item1[order_count].setLayout(new BorderLayout());
            //     item1[order_count] = createOrderItemPanel(path, "เนื้อวัวย่าง ระดับความสุขที่ medium rare อุณหภูมิ 150 องศา",String.format("+%d$", price[order_count]));
            //     int index = order_count;
            //     item1[order_count].addMouseListener(new MouseAdapter() {
            //         @Override
            //         public void mouseClicked(MouseEvent e) {
            //             orderShow = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "OrderShow", false);
            //             orderShow.setBounds(825, 75, 495, 80);
            //             orderShow.setLayout(new BorderLayout());
            //             orderShow.setUndecorated(true);
            //             contentPanel = new JPanel();
            //             contentPanel.setBackground(new Color(255,203,151)); // เปลี่ยนสีตามที่ต้องการ
            //             contentPanel.setLayout(new BorderLayout());
            //             contentPanel.add(item1[index], BorderLayout.CENTER);
            //             indexs = index;
            //             orderShow.setContentPane(contentPanel);
            //             orderShow.setVisible(true);
            //         }
            //     });
            //     productPanel.add(item1[order_count]);
            // }
            // ปุ่ม back
            JButton backButton = new JButton("back");
            backButton.addActionListener(e1 -> orderDialog.dispose());

            // เพิ่ม productPanel เข้าไปใน outerPanel
            outerPanel.add(productPanel, BorderLayout.CENTER);
            // ตั้ง outerPanel เป็นเนื้อหาหลักแทน
            orderDialog.add(outerPanel, BorderLayout.CENTER);
            orderDialog.add(backButton, BorderLayout.SOUTH);

            // ตั้ง popup ให้อยู่ตรงกลาง
            orderDialog.setLocationRelativeTo(PageStart.this);
            orderDialog.setVisible(true);
        });
        add(B_order);
        // ปุ่มอุณหภูมิ
        JButton B_thermometer = new JButton("thermometer");
        B_thermometer.setBounds(0, 250, 80, 80);
        B_thermometer.setOpaque(false);
        B_thermometer.setBorderPainted(false);
        B_thermometer.addActionListener(e-> {
            if(!this.showTemp){
                this.showTemp = true;
                CountDownShowTemp countDownShowTemp = new CountDownShowTemp(this);
                countDownShowTemp.start();
            }
        });
        add(B_thermometer);
        // Shop ร้านค้า
        B_shop = new JButton("Shop");
        B_shop.setBounds(0, 140, 50, 50);
        B_shop.setOpaque(false);
        B_shop.setBorderPainted(false);
        B_shop.addActionListener(e -> {
            // popup ร้านค้า            
            JDialog shopDialog = new JDialog( (JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Shop",true);
            shopDialog.setSize(800,470);
            shopDialog.setLayout(new BorderLayout());
            shopDialog.setUndecorated(true);
            //พาแนลแสดงสินค้า
            JPanel productPanel = new JPanel(new GridLayout(1,3,10,10));
            productPanel.setBackground(Color.LIGHT_GRAY);
        

            //สินค้า 1
            JPanel meatPanel = createProductPanel("./image/rare.png", "เนื้อวัว", 5,shopDialog);
            meatPanel.setBounds(meatRect.x, meatRect.y, 235, 150);
            
            productPanel.add(meatPanel);

            // วากิว
            JPanel wagyuPanel = createProductPanel("./image/wagyuu.png", "เนื้อวากิว", 15,shopDialog);
            wagyuPanel.setBounds(meatRect.x, meatRect.y, 235, 150);
            productPanel.add(wagyuPanel);

            // สันกลาง
            JPanel ribeyePanel = createProductPanel("./image/sungarng.png", "เนื้อสันกลาง", 10,shopDialog);
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

        int displayDuration = 2000;
        Timer timer = new Timer(displayDuration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderShow.setVisible(false); // ซ่อน orderShow
                contentPanel.setBackground(null); // คืนค่าพื้นหลังให้กลับเป็นค่าเริ่มต้นหรือตามที่ต้องการ
            }
        });
        addMouseListener(new MouseAdapter() {
            //กดพลิกเนื้อ ยังไม่ได้ทำเพิ่มนะ
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(meatRect.contains(e.getPoint())){
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
                        orderShow.dispose();
                        int chk = app.getBaseClient().chkMeat();
                        if(chk == 1){
                            System.out.println("In chk");
                            //System.out.println("ContentPanel Parent: " + contentPanel.getParent());
                            //orderShow.remove(contentPanel);
                            contentPanel.setBackground(new Color(182, 255, 162)); // เปลี่ยนสีพื้นหลัง
                            // หากต้องการให้ orderShow มองเห็น ให้ตั้งค่าเป็น true
                            orderShow.setVisible(true);
                            app.getBaseClient().addMoney(price[indexs]);
                        }
                        else{contentPanel.setBackground(new Color(255, 89, 68));}
                        contentPanel.revalidate();
                        contentPanel.repaint();
                        app.getBaseClient().getMeat().kill();
                        timer.setRepeats(false);
                        timer.start();
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
        ImageIcon icon_thermometer = new ImageIcon("./image/thermometer_icon.png");
        g.drawImage(icon.getImage(), 0, 0, this);
        g.drawImage(icon_setting.getImage(), 0, 0, 50, 50, this);
        g.drawImage(icon_order.getImage(), 0, 70, 50, 50, this);
        g.drawImage(icon_shop.getImage(), 0, 140, 50, 50, this);
        g.drawImage(icon_dish.getImage(), plateRect.x, plateRect.y, 500, 500, this);
        g.drawImage(icon_Rank.getImage(), 980, 400, 287, 304, this);
        g.drawImage(icon_thermometer.getImage(), 0, 250, 80, 80, this);
        //วาดอุณหภูมิ
        if (showTemp) {
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            String str_temp = Integer.toString(app.getBaseClient().getMeat().getTemperature()/100);
            g.drawString(str_temp, 200, 250); 
        }
        // เงิน
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString(app.getBaseClient().getMoney()+"$", 10, 660);
        // เวลา
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(app.getBaseClient().getFormatTime(), 620, 25);

        
        
        int x = 1000; // ตำแหน่ง x
        int startY = 480; // ตำแหน่ง y เริ่มต้น
        int lineHeight = 30; // ความสูงของแต่ละบรรทัด

       // ดึงข้อมูลผู้เล่นเรียงตามอันดับจาก getPlayerRankings()
        ArrayList<BaseClient> rankedPlayers = app.baseServer.getPlayerRankings();

        // วาดข้อความสำหรับแต่ละรายการใน rankedPlayers
        for (int i = 0; i < rankedPlayers.size(); i++) {
            BaseClient player = rankedPlayers.get(i);
            String playerName = player.getNameShop();
            double playerMoney = player.getMoney();
            int rank = i + 1;
            
            // ตั้งค่าและวาดกรอบรอบข้อความ
            g.setColor(new Color(85, 85, 85));
            int rectHeight = lineHeight;
            int rectWidth = playerName.length() * 15 + 100; // กำหนดขนาดตามความยาวชื่อผู้เล่น
            int rectX = x;
            int rectY = startY + i * lineHeight - 20;
            g.drawRect(rectX, rectY, rectWidth, rectHeight);

            // วาดข้อมูลผู้เล่นในกรอบ
            g.setColor(new Color(0, 0, 0)); // ตั้งสีสำหรับข้อความ
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // ตั้งฟอนต์

            // ข้อความที่จะแสดง (อันดับ ชื่อ และจำนวนเงิน)
            String displayText = rank + ". " + playerName + " = " + playerMoney + "$";
            int textX = x + 10; // กำหนดตำแหน่ง X ของข้อความให้มีระยะจากกรอบ
            int textY = startY + i * lineHeight;
            g.drawString(displayText, textX, textY);
        }

        // แสดงอันดับของผู้เล่นปัจจุบัน
        String rankingText = "Your ranking: " + app.getBaseClient().getMoney() + "$";
        g.drawString(rankingText, x, startY + rankedPlayers.size() * lineHeight + 30); // วางหลังรายการ

        // วาดรูปภาพเนื้อ ถ้ามี
        if (this.app.getBaseClient().getMeat() != null && this.app.getBaseClient().getMeat().getImage() != null) {
            ImageIcon icon_meat = new ImageIcon(this.app.getBaseClient().getMeat().getImage());
            g.drawImage(icon_meat.getImage(), meatRect.x, meatRect.y, 500, 382, this);
        }

    }
}
class RunRepaint extends Thread{
    private boolean status = true;
    private JPanel panel;
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
    void kill(){
        this.status = false;
    }
    
}

class CountDownShowTemp extends Thread{
    private PageStart pageStart;
    CountDownShowTemp(PageStart pageStart){
        this.pageStart = pageStart;
    }
    public void run(){
        try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
        this.pageStart.showTemp = false;
    }
}

