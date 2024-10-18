import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class Component extends JButton{
    public Component(String text,Color color) {
        super(text);
        setContentAreaFilled(false);    
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setBackground(color);
        setCursor(new Cursor(JFrame.HAND_CURSOR));
    }
    // โอเวอร์ไลด์เมธอดที่ถูกเรียกมาจาก components JButton
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // วาดปุ่ม
        // ถ้ากดปุ่มจะให้สีเปลี่ยน
        if (getModel().isArmed()) {
            g2.setColor(new Color(255,192,203));
        }else{
            g2.setColor(getBackground());
        }
        // เซ็ตพื้นหลังให้เป็นสีต่างๆ
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        //วาดขอบ
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        // g2.dispose();
        super.paintComponent(g);
    }
    public void updateUI() {
        setUI(new BasicButtonUI());
    }
}
