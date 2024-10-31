import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Component{
    // สร้างปุ่มกลมที่มีสีพื้นหลังแบบกำหนดเอง
    public static JButton createCustomRoundedButton(String text, Color color) {
        return new CustomRoundedButton(text, color);
    }
    // สร้างและกำหนด Jslide
    public static JSlider createCustomSlider(int value) {
        JSlider slider = new JSlider(0, 100,value); 
        slider.setUI(new CustomSliderUI(slider)); 
        slider.setPaintTicks(false); // ไม่มีเครื่องหมายติ๊กบนแถบเลื่อน
        return slider;
    }
    // คลาสปุ่มกลมแบบกำหนดเอง
    static class CustomRoundedButton extends JButton {
        public CustomRoundedButton(String text, Color color) {
            super(text);
            setContentAreaFilled(false); 
            setFocusPainted(false); 
            setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            setBackground(color);
            setCursor(new Cursor(JFrame.HAND_CURSOR));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // ถ้ากดจะเปลี่ยนสี
            if (getModel().isArmed()) {
                g2.setColor(new Color(255, 192, 203)); 
            } else {
                g2.setColor(getBackground()); 
            }
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            g2.dispose(); 
            super.paintComponent(g); 
        }
        @Override
        public void updateUI() {
            setUI(new BasicButtonUI()); // Use BasicButtonUI for default behavior
        }
    }
    // [[[[[[[[[[คลาส UI แบบกำหนดเองสำหรับ JSlider]]]]]]]]]]]]]]
    static class CustomSliderUI extends BasicSliderUI {
        private boolean isThumbHovered = false;
        @Override
            protected Dimension getThumbSize() {
                // ปรับขนาดปุ่มเลื่อน (thumb)
                return new Dimension(52, 52);
            }
        public CustomSliderUI(JSlider slider) {
            super(slider);
            slider.setBackground(Color.blue);
            slider.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (thumbRect.contains(e.getPoint())) {
                        isThumbHovered = true;
                    } else {
                        isThumbHovered = false;
                    }
                    slider.repaint();//เอาให้ repaintมาอัพเดท
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    mouseMoved(e);
                }
            });
        }
        @Override
        public void paintTrack(Graphics g) {
            // กำหนดสีเอง
            Graphics2D g2d = (Graphics2D) g;
            Rectangle trackBounds = trackRect;
            int thumbPos = thumbRect.x + (thumbRect.width / 2);
                g2d.setColor(Color.white);
                g2d.fillRect(thumbPos, trackBounds.y + (trackBounds.height / 2) - 2, trackBounds.width - thumbPos, 4);
                g2d.setColor(Color.blue);
                g2d.fillRect(trackBounds.x, trackBounds.y + (trackBounds.height / 2) - 2,  thumbPos, 4);
        }
        @Override
        public void paintThumb(Graphics g) {
            // สีและรูปร่างของปุ่มเลื่อนแบบกำหนดเอง
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(27,29,91)); 
            int thumbRadius = 48; // 
            int thumbX = thumbRect.x + thumbRect.width / 2 - thumbRadius / 2;
            int thumbY = thumbRect.y + thumbRect.height / 2 - thumbRadius / 2;
            g2d.fillOval(thumbX, thumbY, thumbRadius, thumbRadius); 
            if (isThumbHovered) {
                g2d.setColor(Color.GRAY); 
                g2d.setStroke(new BasicStroke(3)); 
                g2d.drawOval(thumbX, thumbY, thumbRadius, thumbRadius); 
            }
        }
    }
}
