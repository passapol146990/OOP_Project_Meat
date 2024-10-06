import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    // ฟังก์ชันสำหรับ Hash String
    public static String hashString(String input, String algorithm) {
        try {
            // สร้างอินสแตนซ์ของ MessageDigest โดยระบุอัลกอริธึม เช่น MD5, SHA-256
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(input.getBytes());

            // แปลงค่า byte[] เป็น String ที่อ่านได้
            return bytesToHex(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("ไม่พบอัลกอริธึมที่ต้องการ: " + algorithm);
        }
    }

    // ฟังก์ชันช่วยในการแปลง byte[] เป็น String ในรูปแบบ Hex
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
