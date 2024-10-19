import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PageLobby extends JPanel {
    App app;
    // Constructor
    PageLobby(App app) {
        this.app = app;
        setLayout(null);

        JButton back = new JButton("Back");
        back.setBounds(0, 0, 100, 60);
        back.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back Click");
            }
            
        });
        add(back);
       
        JButton ready = new JButton("Ready");
        ready.setBounds(600, 600, 100, 60);
        ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I'm Ready");
            }
            
        });
        add(ready);
        JPanel show_player = new JPanel();
        show_player.setBounds(250,200, 800, 400);
        show_player.setBackground(Color.white);
        add(show_player);

    }

   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        ImageIcon icon = new ImageIcon("./image/lobby.png");
        g.drawImage(icon.getImage(), 0, 0, this);
    }
}
