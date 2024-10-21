import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PageLobby extends JPanel {
    App app;
    ArrayList<String[]> stores; // เก็บข้อมูลชื่อร้านและสถานะในรูปแบบ ArrayList ของ String[]
    JPanel show_player;

    // Constructor
    PageLobby(App app) {
        this.app = app;
        setLayout(null);

        // Initialize stores list with name and status
        stores = new ArrayList<>();
        stores.add(new String[]{"Store 1", "Not Ready"});
        stores.add(new String[]{"Store 2", "Ready"});
        stores.add(new String[]{"Store 3", "Not Ready"});

        JButton back = new JButton("Back");
        back.setBounds(0, 0, 100, 60);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back Click");
            }
        });
        add(back);

        JButton ready = new JButton("Toggle Ready");
        ready.setBounds(600, 600, 150, 60);
        ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle the status of the first store (for demo purposes)
                toggleStatus(0); // สลับสถานะของร้านที่ 1 (Store 1)
                updateStoreLabels(); // อัปเดต label แสดงผลใหม่
            }
        });
        add(ready);

        show_player = new JPanel(new GridLayout(0, 1)); // จัดเรียงร้านแต่ละร้านแถวละ 1 ช่อง
        show_player.setBounds(250, 200, 800, 400);
        show_player.setBackground(Color.GRAY);
        add(show_player);

        // Add store labels initially
        updateStoreLabels();
    }

    
    private void toggleStatus(int storeIndex) {
        String[] store = stores.get(storeIndex);
        // สลับสถานะระหว่าง "Ready" และ "Not Ready"
        if (store[1].equals("Ready")) {
            store[1] = "Not Ready";
        } else {
            store[1] = "Ready";
        }
    }

    // Method to update labels based on store data
    private void updateStoreLabels() {
        show_player.removeAll(); // Clear existing labels

        for (String[] store : stores) {
            JLabel label = new JLabel(store[0] + ": " + store[1]);
            label.setHorizontalAlignment(SwingConstants.CENTER);  // จัดแนวนอนให้อยู่ตรงกลาง
            label.setVerticalAlignment(SwingConstants.CENTER);    // จัดแนวตั้งให้อยู่ตรงกลาง
            show_player.add(label); // Add label for each store
        }

        show_player.revalidate();
        show_player.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon icon = new ImageIcon("./image/lobby.png");
        g.drawImage(icon.getImage(), 0, 0, this);
    }
}
