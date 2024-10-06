import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PageStart extends JPanel{
    private App app;
    PageStart(App app){
        this.app = app;
        
    }
    public void paint(Graphics g){
        ImageIcon icon = new ImageIcon("./image/bg-start.png");
        ImageIcon icon_setting = new ImageIcon("./image/settings-white.png");
        ImageIcon icon_order = new ImageIcon("./image/checklist-white.png");
        ImageIcon icon_shop = new ImageIcon("./image/shop -white.png");
        ImageIcon icon_dish = new ImageIcon("./image/dish.png");
        ImageIcon icon_thermometer = new ImageIcon("./image/thermometer.png");
        super.paint(g);
        g.drawImage(icon.getImage(), 0,0, this);
        g.drawImage(icon_setting.getImage(), 0,0,50,50, this);
        g.drawImage(icon_order.getImage(), 0,70,50,50, this);
        g.drawImage(icon_shop.getImage(), 0,140,50,50, this);
        g.drawImage(icon_dish.getImage(), 1000,-50,500,500, this);
        g.drawImage(icon_thermometer.getImage(), 0,310,359,91, this);
    }

}
