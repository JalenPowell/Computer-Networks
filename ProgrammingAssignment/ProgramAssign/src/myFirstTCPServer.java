import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class myFirstTCPServer {

  private static final int BUFF_SIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] byteBuffer = new byte[BUFF_SIZE];  // Receive buffer

    Socket clientSock = servSock.accept();     // Get client connection

    System.out.println("Handling client at " +
            clientSock.getInetAddress().getHostAddress() + " on port " +
            clientSock.getPort());

    for (;;) { // Run forever, accepting and servicing connections

      InputStream in = clientSock.getInputStream();
      OutputStream out = clientSock.getOutputStream();
      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(byteBuffer)) != -1) {

        System.out.println("The client is sent... " + Arrays.toString(byteBuffer));
        char[] Chars = new String(byteBuffer, StandardCharsets.UTF_16).toCharArray();
        String hit = String.valueOf(Chars).trim();
        byteBuffer = toHex(hit);
        byte[] response = Arrays.copyOf(byteBuffer, 32);
        out.write(response);

        System.out.println("This is the response to the client...." + Arrays.toString(response) + "\n");
        byteBuffer = new byte[BUFF_SIZE];

      }

      clientSock.close();  // Close the socket.  We are done with this client!
    }


   }

    /* NOT REACHED */
    private static byte[] toHex(String decimalNum) {
        int decNumber = Integer.parseInt(decimalNum);
        String hexString = Integer.toHexString(decNumber).toUpperCase();
        return hexString.getBytes();
      }
        //String hexString = Integer.toHexString(decNumber).toUpperCase();
        //return hexString.getBytes();

    }


