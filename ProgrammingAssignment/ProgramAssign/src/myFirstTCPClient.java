import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Scanner;

public class myFirstTCPClient {
  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

    int i = 0;

    String server = args[0];       // Server name or IP address
    // Convert input String to bytes using the default character encoding
    //byte[] byteBuffer = args[1].getBytes();

    int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);

    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();

    for (;;) {
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a sentence S as a valid decimal (Input Q to quit): ");
      i++;

      String user = scan.nextLine();
      if (user.equalsIgnoreCase("Q")) break;

      int num = Integer.parseInt(user);

      if (num < 0 || (num > Math.pow(2, 31))) {
        System.out.println("Invalid Input!");
        //break;
      } //else if (user. == 0) {
        //System.out.println("Please try again. ");


     // double minTimeTimeElapsed = Double.POSITIVE_INFINITY, avgTimeElapsed = 0, maxTimeElapsed = 0;
      byte[] byteBuffer = user.getBytes("UTF_16");

      out.write(byteBuffer);  // Send the encoded string to the server
      long startTime = System.nanoTime();


      double minTimeElapsed = Double.POSITIVE_INFINITY, avgTimeElapsed = 0, maxTimeElapsed = 0;


      // Receive the same string back from the server
      int totalBytesRcvd = 0;  // Total bytes received so far
      int bytesRcvd;           // Bytes received in last read
      while (totalBytesRcvd < byteBuffer.length) {
        if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,
                byteBuffer.length - totalBytesRcvd)) == -1)
          throw new SocketException("Connection close prematurely");
        totalBytesRcvd += bytesRcvd;
      }

      long finishTime = System.nanoTime();

      long totalTimeElapsed = finishTime - startTime;

      double timeElapsed = (System.nanoTime() - startTime) / 1000000;
      minTimeElapsed = Math.min(minTimeElapsed, timeElapsed);
      maxTimeElapsed = Math.max(maxTimeElapsed, timeElapsed);
      avgTimeElapsed += (timeElapsed - avgTimeElapsed) / (double) i;


      System.out.println("Received: " + new String(byteBuffer).trim());
      System.out.println("Round Trip Time: " + totalTimeElapsed / 1000000.0 + " ms");

      System.out.println(String.format(
              "TIme Elapsed Statistics Min: %.2fms  Max: %.2fms  Avg: %.2fms\n",
                          minTimeElapsed != Double.POSITIVE_INFINITY ? minTimeElapsed : 0,
                          maxTimeElapsed,
                          avgTimeElapsed));


      //System.out.println(totalTimeElapsed);
    }

    socket.close();  // Close the socket and its streams
  }
}
