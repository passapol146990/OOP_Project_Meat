import javax.swing.*;
import java.awt.*;

public class ChangeCursorExample extends JFrame {
    public ChangeCursorExample() {
        setTitle("เปลี่ยน Cursor ตัวอย่าง");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        // ตั้งค่า cursor เป็น crosshair
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChangeCursorExample();
    }
}
