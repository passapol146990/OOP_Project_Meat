import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class ImageSorterByName extends JFrame implements Runnable {
    private ArrayList<String> imagePaths;
    private ArrayList<BufferedImage> images;

    public ImageSorterByName(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
        this.images = new ArrayList<>();
        setTitle("Sorted Images");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // ใช้ BorderLayout เพื่อจัดการพื้นที่
    }

    @Override
    public void run() {
        // โหลดรูปภาพตามลำดับชื่อไฟล์
        for (String path : imagePaths) {
            try {
                images.add(ImageIO.read(new File(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // เรียงชื่อไฟล์
        Collections.sort(imagePaths);

        // แสดงรูปภาพทีละรูป
        for (BufferedImage img : images) {
            JLabel label = new JLabel(new ImageIcon(img));
            add(label, BorderLayout.CENTER);
            revalidate(); // อัพเดตหน้าต่าง
            repaint(); // รีเฟรชหน้าต่าง
            
            // รอ 0.5 วินาทีก่อนแสดงรูปถัดไป
            try {
                Thread.sleep(50); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // ลบรูปก่อนหน้านี้ออก
            remove(label);
        }
        
        
    }

    public static void main(String[] args) {
        ArrayList<String> imagePaths = new ArrayList<>();

        for(int i=0;i<imagePaths.size();i++){
            
        }
        // imagePaths.add("./image/gif.gif");
        imagePaths.add("./image/meat1.png");
        imagePaths.add("./image/meat2.png");
        imagePaths.add("./image/meat3.png");
        imagePaths.add("./image/meat4.png");
        imagePaths.add("./image/meat5.png");
        imagePaths.add("./image/meat6.png");
        imagePaths.add("./image/meat7.png");
        // เพิ่มเส้นทางรูปภาพอื่น ๆ ที่ต้องการ

        // สร้างและเริ่ม Thread
        ImageSorterByName sorter = new ImageSorterByName(imagePaths);
        Thread sorterThread = new Thread(sorter);
        sorterThread.start();

        sorter.setVisible(true); // แสดงหน้าต่าง
    }
}
