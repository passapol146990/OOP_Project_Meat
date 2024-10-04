// class แม่ของโปรแกรมรับหน้าที่เปลี่ยนหน้าต่าง
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
public class App extends JFrame{
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PageMenu  pageMenu;
    private PageStart pageStart;
    private PageSeting pageSeting;
    private PageAbout pageAbout;
    App(String title){
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        setTitle(title);
        setBounds(50,50,1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("./image/medium rare-shadow.png"));
        add(this.mainPanel);
        setVisible(true);
    }
    void addPanel(JPanel panel,String path){
        this.mainPanel.add(panel, path);
    }
    void showPanel(String path){
        this.cardLayout.show(this.mainPanel,path);
    }
    void setPageMenuClass(PageMenu page){
        this.pageMenu = page;
    }
    void setPageStartClass(PageStart page){
        this.pageStart = page;
    }
    void setPageSetingClass(PageSeting page){
        this.pageSeting = page;
    }
    void setPageAboutClass(PageAbout page){
        this.pageAbout = page;
    }
    // PageAbout getPageAbout(){
    //     return this.pageAbout;
    // }
    // PageStart getPageStart(){
    //     return this.pageStart;
    // }
}