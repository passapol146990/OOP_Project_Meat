import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PageSeting extends JPanel{
    PageSeting(){
        setBackground(Color.black);
        setLayout(new BorderLayout());
        JLabel  label = new JLabel("Seting");
        JButton b = new JButton("Back to the menu");
        JButton b1 = new JButton("Test1");
        add(label);
        add(b);
        add(b1);
    }
}
