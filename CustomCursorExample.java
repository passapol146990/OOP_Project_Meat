import javax.swing.*;
import java.awt.*;

public class CustomCursorExample extends JFrame {
    public CustomCursorExample() {
        setTitle("Custom Cursor ตัวอย่าง");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        try {
            // โหลดรูปภาพที่ต้องการใช้เป็น cursor
            Image cursorImage = Toolkit.getDefaultToolkit().getImage("Untitled-1.png");
            // กำหนดจุด hotspot ของ cursor (จุดที่คลิกจะถูกพิจารณา)
            Point hotspot = new Point(0, 0);
            // สร้าง custom cursor
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, hotspot, "Custom Cursor");
            panel.setCursor(customCursor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CustomCursorExample();
    }
}
