public class PercentageCalculator {
    public static void main(String[] args) {
        int num1 = 150;
        int num2 = 200;
        
        int difference = Math.abs(num1 - num2); // หาค่าความต่าง
        int percent = 100 - (difference / 3) * 5; // ลดเปอร์เซ็นต์ตามค่าความต่าง
        
        // ถ้าเปอร์เซ็นต์น้อยกว่า 1 ให้ส่งค่า 1%
        if (percent <= 0) {
            percent = 1;
        }

        System.out.println("เปอร์เซ็นต์ที่ได้: " + percent + "%");
    }
}
