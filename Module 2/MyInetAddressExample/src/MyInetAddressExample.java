import java.net.*; // for InetAddress
import java.util.Scanner;

public class MyInetAddressExample {

    public static  void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Host Name: ");
        String IP = in.nextLine();
        InetAddress hName; // Name
        try {
            hName = InetAddress.getByName(IP);
            String ipAddress = hName.getHostAddress();

            // Converts the IP Address to Binary
            toBinary(ipAddress);

            // Converts the binary to hexadecimal
            toHex(ipAddress);

            // Prints the decimal format
            System.out.println("Decimal dotted-quad format: " + ipAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    private static void toBinary(String hNameAddress) {
        String binaryString = "";
        String[] ipArray = hNameAddress.split("\\.");
        for (String section : ipArray) {
            int deci = Integer.parseInt(section);
            binaryString += Integer.toBinaryString(deci);
        }
        System.out.println("Binary format: " + binaryString);
    }

    private static void toHex(String ip) {
        StringBuilder hexString = new StringBuilder();
        String[] ipArray = ip.split("\\.");
        for (String section : ipArray) {
            int deci = Integer.parseInt(section);
            hexString.append(Integer.toHexString(deci));
            hexString.append(".");
        }
        hexString.delete(hexString.length() - 1, hexString.length());
        System.out.println("Hexadecimal dotted-quad format : " + hexString);

    }

}

