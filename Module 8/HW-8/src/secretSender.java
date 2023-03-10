//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class secretSender {
    private static final int PACKETSIZE = 100;

    public secretSender() {
    }

    public static void main(String[] var0) {
        if (var0.length != 0) {
            System.out.println("usage: java secretSender");
        } else {
            DatagramSocket var1 = null;
            while(true) {
                try {
                    InetAddress var2 = InetAddress.getByName("131.204.14.247");
                    short var3 = 10076;
                    var1 = new DatagramSocket();
                    byte[] var4 = "Peace Eagle".getBytes();
                    DatagramPacket var5 = new DatagramPacket(var4, var4.length, var2, var3);
                    var1.send(var5);
                } catch (Exception var9) {
                    System.out.println(var9);
                } finally {
                    if (var1 != null) {
                        var1.close();
                    }

                }
            }

        }
    }
}
