import javax.swing.*;
import java.awt.*;

public class OrderPopup extends JPanel {
    OrderPopup() {
        setLayout(null);

        // ปุ่ม Order
        JButton B_order = new JButton("Order");
        B_order.setBounds(0, 140, 50, 50);
        B_order.setOpaque(false);
        B_order.setBorderPainted(false);
        B_order.addActionListener(e -> {
            // สร้าง popup modal สำหรับการสั่งซื้อ
            JDialog orderDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(OrderPopup.this), "Order", true);
            orderDialog.setSize(350, 500);
            orderDialog.setLayout(new BorderLayout());
            orderDialog.setUndecorated(true); // ป้องกันการขยับ popup

            // พาเนลแสดงสินค้า
            JPanel productPanel = new JPanel(new GridLayout(5, 1, 10, 10));
            productPanel.setBackground(Color.LIGHT_GRAY);
            String path = "./image/meat/01/rare1.png.";
            // สินค้า 1
            JPanel item1 = createOrderItemPanel(path, "เนื้อวัวย่าง ระดับความสุขที่ medium rare อุณหภูมิ 150 องศา", "+100$");
            productPanel.add(item1);

            // สินค้า 2
            JPanel item2 = createOrderItemPanel(path, "เนื้อวัวย่าง ระดับความสุขที่ medium well อุณหภูมิ 200 องศา", "+150$");
            productPanel.add(item2);

            // สินค้า 3
            JPanel item3 = createOrderItemPanel(path, "เนื้อวัวดิบ ระดับความสุขที่ rare อุณหภูมิ 23 องศา", "+50$");
            productPanel.add(item3);

            // สินค้า 4
            JPanel item4 = createOrderItemPanel(path, "เนื้อวัวย่าง ระดับความสุขที่ medium rare อุณหภูมิ 152 องศา", "+109$");
            productPanel.add(item4);

            // สินค้า 5
            JPanel item5 = createOrderItemPanel(path, "เนื้อวัวย่าง ระดับความสุขที่ medium rare อุณหภูมิ 170 องศา", "+112$");
            productPanel.add(item5);

            // ปุ่ม back
            JButton backButton = new JButton("back");
            backButton.addActionListener(e1 -> orderDialog.dispose());

            // ตั้ง layout และเพิ่มเนื้อหา
            orderDialog.add(productPanel, BorderLayout.CENTER);
            orderDialog.add(backButton, BorderLayout.SOUTH);

            // ตั้ง popup ให้อยู่ตรงกลาง
            orderDialog.setLocationRelativeTo(OrderPopup.this);
            orderDialog.setVisible(true);
        });

        add(B_order);
    }

    private JPanel createOrderItemPanel(String imagePath, String description, String price) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // โหลดรูปภาพและปรับขนาดให้พอดีกับพื้นที่
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // ปรับขนาดรูปภาพ
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        // พาเนลซ้าย: รูปภาพสินค้า
        panel.add(imageLabel, BorderLayout.WEST);

        // พาเนลกลาง: คำอธิบายสินค้า (ใช้ JTextArea สำหรับการตัดบรรทัด)
        JTextArea descTextArea = new JTextArea(description);
        descTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12)); // ตั้งฟอนต์ที่รองรับภาษาไทย
        descTextArea.setLineWrap(true); // เปิดใช้งานการตัดบรรทัด
        descTextArea.setWrapStyleWord(true); // ตัดบรรทัดที่เว้นวรรค
        descTextArea.setOpaque(false); // ตั้งค่าให้โปร่งใสเพื่อให้ดูเหมือน JLabel
        descTextArea.setEditable(false); // ปิดการแก้ไข

        panel.add(descTextArea, BorderLayout.CENTER);

        // พาเนลขวา: ราคา (เพิ่มการเว้นขอบด้วย EmptyBorder)
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 14)); // ตั้งฟอนต์ที่รองรับภาษาไทย
        priceLabel.setForeground(Color.GREEN);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // เว้นขอบขวา 10px

        panel.add(priceLabel, BorderLayout.EAST);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // เพิ่มเส้นขอบ
        return panel;
    }

    // public static void main(String[] args) {
    //     JFrame frame = new JFrame("Order Popup Example");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(800, 600);

    //     OrderPopup page = new OrderPopup();
    //     frame.add(page);
    //     frame.setVisible(true);
    // }
}
