import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Image;

public class PageMenu extends JPanel{
    PageMenu(){
    }
    public void paint(Graphics g){
        ImageIcon icon = new ImageIcon("./image/gif.gif");
        super.paint(g);
        g.drawImage(icon.getImage(), 0,0, this);
    }
}
