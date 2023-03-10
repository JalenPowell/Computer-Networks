import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;


public class myFirstUDPClient {

  private static final int TIMEOUT = 3000;   // Resend timeout (milliseconds)
  private static final int MAX_TRIES = 5;     // Maximum retransmissions

  private static int i = 0;

  public static void main(String[] args) throws IOException {

    if ((args.length != 2))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

     int i = 0;
    double minTimeElapsed = Double.POSITIVE_INFINITY, avgTimeElapsed = 0, maxTimeElapsed = 0;

    InetAddress serverAddress = InetAddress.getByName(args[0]);  // Server address
    int servPort = Integer.parseInt(args[1]);
    // Convert input String to bytes using the default character encoding
    while (true) {


      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a sentence S as a valid decimal (Input Q to quit): ");

      String user = scan.nextLine();
      if (user.equalsIgnoreCase("Q")) break;

      int num = Integer.parseInt(user);

      if (num < 0 || (num > Math.pow(2, 31))) {
        System.out.println("Invalid Input!");
      }
      i++;
      byte[] bytesToSend = user.getBytes("UTF_16");

      DatagramSocket socket = new DatagramSocket();

      socket.setSoTimeout(TIMEOUT);  // Maximum receive blocking time (milliseconds)

      DatagramPacket sendPacket = new DatagramPacket(bytesToSend,  // Sending packet
              bytesToSend.length, serverAddress, servPort);

      DatagramPacket receivePacket =                              // Receiving packet
              new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);


      int tries = 0;      // Packets may be lost, so we have to keep trying
      boolean receivedResponse = false;
      do {
        socket.send(sendPacket); // Send the echo string
        long startTime = System.nanoTime();
        try {
          socket.receive(receivePacket);  // Attempt echo reply reception
          long finishTime = System.nanoTime();

          double timeElapsed = (finishTime - startTime) / 1000000.0;

          minTimeElapsed = Math.min(minTimeElapsed, timeElapsed);
          maxTimeElapsed = Math.max(maxTimeElapsed, timeElapsed);
          avgTimeElapsed += (timeElapsed - avgTimeElapsed) / (double) i;


          if (!receivePacket.getAddress().equals(serverAddress))  // Check source
            throw new IOException("Received packet from an unknown source");

          receivedResponse = true;
        } catch (InterruptedIOException e) {  // We did not get anything
          tries += 1;
          System.out.println("Timed out, " + (MAX_TRIES - tries) + " more tries...");
        }
      } while ((!receivedResponse) && (tries < MAX_TRIES));

      if (receivedResponse) {
        System.out.println("Received: " + new String(receivePacket.getData()).trim());

        System.out.println(String.format(
                "TIme Elapsed Statistics Min: %.2fms  Max: %.2fms  Avg: %.2fms\n",
                minTimeElapsed != Double.POSITIVE_INFINITY ? minTimeElapsed : 0,
                maxTimeElapsed,
                avgTimeElapsed));
      } else
        System.out.println("No response -- giving up.");

    }
    //socket.close();
  }

}
