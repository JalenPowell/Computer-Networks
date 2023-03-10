import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class myFirstUDPServer {

  private static final int ECHOMAX = 32;  // Maximum size of echo datagram

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct argument list
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    byte[] byteBuffer = new byte[ECHOMAX];


    for (;;) {  // Run forever, receiving and echoing datagrams
      socket.receive(packet);     // Receive packet from client
      System.out.println("Handling client at " +
        packet.getAddress().getHostAddress() + " on port " + packet.getPort());
      char[] Chars = new String(packet.getData(), StandardCharsets.UTF_16).toCharArray();
      String hit = String.valueOf(Chars).trim();
      byteBuffer = toHex(hit);

      byte[] response = Arrays.copyOf(byteBuffer, 32);
      packet.setData(response);

      System.out.println("This is the response to the client...." + Arrays.toString(response));

      socket.send(packet);       // Send the same packet back to client
      packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
    }
    /* NOT REACHED */

  }
  private static byte[] toHex(String decimalNum) {

      int decNumber = Integer.parseInt(decimalNum);
      String hexString = Integer.toHexString(decNumber).toUpperCase();
      return hexString.getBytes();

      }
  }

