import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
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
    boolean showTemp = false;
    /////////////////////////////////////createProductPanel//////////////////////////////////////////
    private JPanel createProductPanel(String imagePath, String productName, int price, JDialog Jdialog){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel imagLabel = new JLabel();
        try{
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image resizedImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            imagLabel.setIcon(resizedIcon);
        }catch (IOException e) {e.printStackTrace();}
        imagLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                app.createMeat(productName,price);
                meatRect = new Rectangle(402, 160, 400, 300);
                Jdialog.dispose();
            }
        });
        panel.add(imagLabel, BorderLayout.CENTER);
        JLabel nameLabel = new JLabel(productName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Tahoma",Font.CENTER_BASELINE,20));
        JLabel pricLabel = new JLabel(String.format("%d$", price), SwingUtilities.CENTER);
        pricLabel.setForeground(new Color(4,93,40));
        JPanel textPanel = new JPanel(new GridLayout(2,1));
        textPanel.add(nameLabel);
        textPanel.add(pricLabel);
        panel.add(textPanel, BorderLayout.SOUTH);
        return panel;
    }
     /////////////////////////////////////createOrderItemPanel//////////////////////////////////////////
    private JPanel createOrderItemPanel(JDialog orderDialog, int index,String imagePath, String description, String price) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, BorderLayout.WEST);
        JTextArea descTextArea = new JTextArea(description);
        descTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setOpaque(false);
        descTextArea.setEditable(false);
        panel.add(descTextArea, BorderLayout.CENTER);
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        priceLabel.setForeground(new Color(4,93,40));
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panel.add(priceLabel, BorderLayout.EAST);
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                app.getBaseClient().setOrdering(app.getBaseClient().getOrder().get(index),index);
                orderDialog.dispose();
            }
        });
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
   /////////// /////////////////////////////////////PageStart////////////////////////////////////////////////
    PageStart(App app) {
        this.app = app;
        setLayout(null);
        app.getBaseClient().setTime(300);
        app.getSound().playMusic();
        meatRect = new Rectangle(402, 160, 400, 300); 
        plateRect = new Rectangle(1000, -50, 100, 100);

        ///////////////////////////////////Button_setting///////////////////////////////////////////////////
        B_setting = new JButton();
        B_setting.setBounds(0, 0, 50, 50);
        B_setting.setOpaque(false);
        B_setting.setBorderPainted(false);
        B_setting.addActionListener(e-> {
            JDialog settingsDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Setting", true);
            settingsDialog.setSize(400, 250);
            settingsDialog.setLayout(new BorderLayout());
            settingsDialog.setUndecorated(true);
            JLabel orderLabel = new JLabel("Setting", SwingConstants.CENTER);
            settingsDialog.add(orderLabel, BorderLayout.NORTH);
            JPanel settingsPanel = new JPanel();
            settingsPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            ImageIcon AmusicIcon = new ImageIcon("./image/music.png");
            Image musicImage = AmusicIcon.getImage(); 
            Image resizedMusicImage = musicImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon AaudioIcon = new ImageIcon("./image/volume.png");
            Image audioImage = AaudioIcon.getImage();
            Image resizedAudioImage = audioImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel musicIcon = new JLabel(new ImageIcon(resizedMusicImage));
            JLabel audioIcon = new JLabel(new ImageIcon(resizedAudioImage));
            JSlider musicSlider = new JSlider(0, 100, app.getSound().getVolumeMusic());
            musicSlider.setForeground(Color.BLUE);
            musicSlider.addChangeListener(e1 ->{
                app.getFile().saveVolumeMusic(musicSlider.getValue());
                app.getSound().setVolumeMusic(musicSlider.getValue());
            });
            JSlider audioSlider = new JSlider(0, 100, app.getSound().getVolumeEffect());
            audioSlider.setForeground(Color.BLUE);
            audioSlider.addChangeListener(e1 ->{
                app.getFile().saveVolumeEffect(audioSlider.getValue());
                app.getSound().setVolumeEffect(audioSlider.getValue());
            });
            JLabel musicLabel = new JLabel("Music");
            JLabel audioLabel = new JLabel("Audio");
            JButton backToGameButton = Component.createCustomRoundedButton("Back to the Game",Color.white);
            JButton backToMenuButton = Component.createCustomRoundedButton("Back to the Menu",Color.white);
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
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 5.0;
            settingsPanel.add(backToGameButton, gbc);
            backToGameButton.addActionListener(e1 -> settingsDialog.dispose());
            gbc.gridx = 1;
            gbc.gridy = 4;
            settingsPanel.add(backToMenuButton, gbc);
            backToMenuButton.addActionListener(e1 -> {
                app.getBaseClient().statusConnectServer = false;
                app.getBaseClient().statusReady = false;
                settingsDialog.dispose();
                app.getBaseClient().nowPage = "menu";
            });
            settingsDialog.add(settingsPanel);
            settingsDialog.setLocationRelativeTo(PageStart.this);
            settingsDialog.setVisible(true);
        });
        add(B_setting);

        //////////////////////////////////////////Button_order///////////////////////////////////////////////////
        B_order = new JButton("Order");
        B_order.setBounds(0, 70, 50, 50);
        B_order.setOpaque(false);
        B_order.setBorderPainted(false);
        B_order.addActionListener(e-> {
            JDialog orderDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Order", true);
            orderDialog.setSize(350, 500);
            orderDialog.setLayout(new BorderLayout());
            orderDialog.setUndecorated(true);
            JLabel orderLabel = new JLabel("ORDER", SwingConstants.CENTER);
            orderDialog.add(orderLabel, BorderLayout.NORTH);
            JButton btnClose = new JButton("close");
            orderDialog.add(btnClose, BorderLayout.CENTER);
            JPanel outerPanel = new JPanel(new BorderLayout());
            outerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            JPanel productPanel = new JPanel(new GridLayout(5, 1, 10, 10));
            ArrayList<HashMap<String,String>> dataOrder = this.app.getBaseClient().getOrder();
            for(int i=0;i<dataOrder.size();i++){
                productPanel.add(this.createOrderItemPanel(orderDialog,i,dataOrder.get(i).get("image"),dataOrder.get(i).get("title"),String.format("+%s$", dataOrder.get(i).get("price"))));
            }
            JButton backButton = new JButton("Close");
            backButton.addActionListener(e1 -> orderDialog.dispose());
            outerPanel.add(productPanel, BorderLayout.CENTER);
            orderDialog.add(outerPanel, BorderLayout.CENTER);
            orderDialog.add(backButton, BorderLayout.SOUTH);
            orderDialog.setLocationRelativeTo(PageStart.this);
            orderDialog.setVisible(true);
        });
        add(B_order);
        //////////////////////////////////////////Button_thermometer///////////////////////////////////////////////////
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
        //////////////////////////////////////////Button_Shop///////////////////////////////////////////////////
        B_shop = new JButton("Shop");
        B_shop.setBounds(0, 140, 50, 50);
        B_shop.setOpaque(false);
        B_shop.setBorderPainted(false);
        B_shop.addActionListener(e -> {        
            JDialog shopDialog = new JDialog( (JFrame) SwingUtilities.getWindowAncestor(PageStart.this), "Shop",true);
            shopDialog.setSize(800,470);
            shopDialog.setLayout(new BorderLayout());
            shopDialog.setUndecorated(true);
            JPanel productPanel = new JPanel(new GridLayout(1,3,10,10));
            productPanel.setBackground(Color.LIGHT_GRAY);
            JPanel meatPanel = createProductPanel("./image/meat/01/rare1.png", "เนื้อวัว", 10,shopDialog);
            meatPanel.setBounds(meatRect.x, meatRect.y, 235, 150);
            productPanel.add(meatPanel);
            JPanel wagyuPanel = createProductPanel("./image/meat/02/rare1.png", "เนื้อวากิว", 40,shopDialog);
            wagyuPanel.setBounds(meatRect.x, meatRect.y, 235, 150);
            productPanel.add(wagyuPanel);
            JPanel ribeyePanel = createProductPanel("./image/meat/03/rare1.png", "เนื้อสันกลาง", 30,shopDialog);
            ribeyePanel.setPreferredSize(new Dimension(235,150));
            ribeyePanel.setLocation(meatRect.x, meatRect.y);
            productPanel.add(ribeyePanel);
            JButton backButton = new JButton("Close");
            backButton.addActionListener(e1 -> shopDialog.dispose());
            shopDialog.add(productPanel, BorderLayout.CENTER);
            shopDialog.add(backButton, BorderLayout.SOUTH);
            shopDialog.setLocationRelativeTo(PageStart.this);
            shopDialog.setVisible(true);
        });
        add(B_shop);
        /////////////////////////////////////////thread_RunRepaint///////////////////////////////////////////////////////////
        runRepaint = new RunRepaint(this); 
        runRepaint.start();
        ////////////////////////////////////////MouseListener&&MotionListener////////////////////////////////////////////////
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(meatRect.contains(e.getPoint())){
                    ClickMeat clickMeat = new ClickMeat(app.getBaseClient().getMeat());
                    clickMeat.start();
                }
            }
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
                    if(meatRect.x>550&&meatRect.y<200) {
                        boolean sendCheck = app.getBaseClient().sendOrder();
                        if(sendCheck){app.getSound().playGiveMoney();}
                        app.getSound().closeEffect();
                        isHoldingMeat =false; 
                        showTemp = false;
                    }else{
                        meatRect.x = 402;
                        meatRect.y = 160;
                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isHoldingMeat) {
                    int dx = e.getX() - lastMousePosition.x;
                    int dy = e.getY() - lastMousePosition.y;
                    meatRect.x += dx;
                    meatRect.y += dy;
                    lastMousePosition = e.getPoint();
                    repaint();
                }
            }
        });
    }
    static String[] getFormatTitleOrder(String text, int maxString) {
        if (text == null || text.isEmpty() || maxString <= 0) {
            return new String[]{};
        }
        int len = (int) Math.ceil(text.length()/maxString);
        if(text.length()%maxString!=0){len+=1;}
        String[] data = new String[len];
        int start = 0;
        int end = maxString;
        for(int i=0;i<len;i++){
            end = Math.min(start + maxString, text.length());
            data[i] = text.substring(start, end);
            start = end;
        }
        return data;
    }
    ////////////////////////////////////////////////////PAINT//////////////////////////////////////////////////////////////////
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
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
        if (showTemp&&this.app.getBaseClient().getMeat()!=null) {
            g.drawString("อุณหภูมิตรงกลางของเนื้อคือ : " + app.getBaseClient().getMeat().getTemperature() + " °C", 500, 650); 
        }else if(showTemp){
            g.setColor(new Color(255, 0,0));
            g.drawString("คุณยังไม่มีเนื้อบนเตา", 550, 650); 
        }
        g.setColor(new Color(255, 255, 255));
        g.drawString(app.getBaseClient().getFormatTime(), 620, 25);
        g.setFont(new Font("Tahoma", Font.PLAIN, 30));
        if(app.getBaseClient().getMoney()<0){
            g.setColor(new Color(255, 0,0));
        }
        g.drawString(app.getBaseClient().getMoney()+"$", 10, 660);
        ////////////////////////////////order///////////////////////////////////////////
        if(this.app.getBaseClient().checkOrdering()){
            HashMap<String,String> isorders = this.app.getBaseClient().getOrdering();
            g.drawImage(new ImageIcon("./image/Component/bg_order.png").getImage(), 900, 0, 400,100,this);
            g.drawImage(new ImageIcon(isorders.get("image")).getImage(), 910, 2, 100,100,this);
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("Tahoma",Font.ITALIC,15));
            String[] titleOrder = getFormatTitleOrder(isorders.get("title"),20);
            int positionTitleShowOrder = 20;
            for(int i=0;i<titleOrder.length;i++){
                g.drawString(titleOrder[i],1010,positionTitleShowOrder);
                positionTitleShowOrder+=20;
            }
            g.setFont(new Font("Tahoma",Font.BOLD,20));
            g.setColor(new Color(4,93,40));
            g.drawString(String.format("+%s$", isorders.get("price")),1180,55);
        }
        
        //////////////////////////////getPlayerRankings()///////////////////////////////////
        int x = 995;
        int startY = 480;
        int lineHeight = 30;
        ArrayList<BaseClient> rankedPlayers = app.baseServer.getPlayerRankings();
        for (int i = 0; i < rankedPlayers.size(); i++) {
            BaseClient player = rankedPlayers.get(i);
            String playerName = player.getNameShop();
            double playerMoney = player.getMoney();
            int rank = i + 1;
            g.setColor(new Color(85, 85, 85));
            int rectY = startY + i * lineHeight - 20;
            g.drawRect(x-10, rectY, 260, lineHeight);
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Tahoma", Font.PLAIN, 15));
            String displayText = rank + ". " + playerName + " = " + playerMoney + "$";
            int textX = x + 10;
            int textY = startY + i * lineHeight;
            g.drawString(displayText, textX, textY);
        }
        String rankingText = "Your money: " + app.getBaseClient().getMoney() + "$";
        g.drawString(rankingText, x, 670);
        g.drawRect(x-10,650,260,lineHeight);
        ///////////////////////////////////////////////drawMeat////////////////////////////////////////////////////
        if (this.app.getBaseClient().getMeat() != null && this.app.getBaseClient().getMeat().getImage() != null) {
            ImageIcon icon_meat = new ImageIcon(this.app.getBaseClient().getMeat().getImage());
            g.drawImage(icon_meat.getImage(), meatRect.x, meatRect.y, 500, 382, this);
        }
    }
}
class RunRepaint extends Thread{
    private boolean status = true;
    private JPanel panel;
    private Sound grillSound;
    RunRepaint(JPanel panel){
        this.panel = panel;
    }
    public void run(){
        while (this.status) {
            this.panel.repaint();
            try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
        grillSound.stopMusic();
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

