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
        ImageIcon icon_dep = new ImageIcon("./image/dep.png");
        g.drawImage(icon.getImage(), 0, 0, 1280, 720, this);
        g.drawImage(icon_dep.getImage(), 0, 0, 1280, 720, this);
    }
}
