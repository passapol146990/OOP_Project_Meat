import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PageAbout extends JPanel {
    private App app;
    private JButton backButton;
    public PageAbout(App app) {
        this.app = app;
        backButton = new JButton("Back");
        backButton.setBounds(540, 550, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back button clicked!");
                app.showPanel("menu");
            }
        });
        setLayout(null);
        add(backButton);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("./image/bg-wood.png");
        ImageIcon dep_1 = new ImageIcon("./image/dep_1.png");
        ImageIcon dep_2 = new ImageIcon("./image/dep_2.png");
        ImageIcon dep_3 = new ImageIcon("./image/dep_3.png");
        ImageIcon dep_4 = new ImageIcon("./image/dep_4.png");
        g.drawImage(icon.getImage(), 0, 0, 1280, 720, this);
        g.drawImage(dep_1.getImage(), 100, 205, 250, 300, this); 
        g.drawImage(dep_2.getImage(), 380, 205, 250, 300, this); 
        g.drawImage(dep_3.getImage(), 660, 205, 250, 300, this); 
        g.drawImage(dep_4.getImage(), 940, 205, 250, 300, this); 
    }
}
