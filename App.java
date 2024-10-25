// class แม่ของโปรแกรมรับหน้าที่เปลี่ยนหน้าต่าง
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

import java.awt.Toolkit;
public class App extends JFrame{
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BaseClient baseClient;
    private Sound sound = new Sound();
    private HashMap<String,JPanel> panels = new HashMap<String,JPanel>();
    App(String title,BaseClient baseClient){
        this.baseClient = baseClient;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        setTitle(title);
        setBounds(50,50,1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("./image/medium rare-shadow.png"));
        add(this.mainPanel);
        setVisible(true);
    }
    BaseClient getBaseClient(){return this.baseClient;}
    void addPanel(JPanel panel,String path){
        this.panels.put(path, panel);
        this.mainPanel.add(panel, path);
    }
    void showPanel(String path){
        this.cardLayout.show(this.mainPanel,path);
    }
    JPanel getPanel(String path){
        return this.panels.get(path);
    }
    Sound getSound(){
        return this.sound;
    }
}