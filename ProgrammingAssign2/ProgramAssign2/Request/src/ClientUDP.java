import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class ClientUDP {

    static Scanner scan = new Scanner(System.in);
    static boolean coolio = true;
    static short RequestID = 0;
    static byte totMessageLen = 0;


  public static void main(String args[]) throws Exception {

      if (args.length != 2 && args.length != 3)  // Test for correct # of args        
	    throw new IllegalArgumentException("Parameter(s): <Destination>" +
					     " <Port> [<encoding]");

      
      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port

      DatagramSocket sock = new DatagramSocket(); // UDP socket for sending

      do {
          //} //while(){};
          short Operand1 = getOperand1();
          short Operand2 = getOperand2();
          byte OpCode = getOperandCode();

          Request request = new Request(Operand1, Operand2, OpCode);

          // Use the encoding scheme given on the command line (args[2])
          RequestEncoder encoder = (args.length == 3 ?
                  new RequestEncoderBin(args[2]) :
                  new RequestEncoderBin());

          byte[] codedRequest = encoder.encode(request); // Encode friend

          DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length,
                  destAddr, destPort);
          sock.send(message);

          RequestID++;

          System.out.println("-----------------------------------------------------------------------------------------");
          System.out.println("This is the request that was sent to the Server.");
          System.out.println(request);

          System.out.println("Here is the request in hexadecimal: ");
          System.out.println("Operand 1: " + ServerUDP.hexaDec(request.Operand1));
          System.out.println("Operand 2: " + ServerUDP.hexaDec(request.Operand2));
          System.out.println("Operation Code: " + ServerUDP.hexaDec(request.OpCode));

          DatagramPacket reciev = new DatagramPacket(new byte[8], 8);

          sock.receive(reciev);

          ResponseDecoder decoder  = new ResponseDecoderBin();

          Response response= decoder.decode(reciev);

          System.out.println("\n-----------------------------------------------------------------------------------------");
          System.out.println("This is the response from the Server.");
          System.out.println(response);

          Scanner answer = new Scanner(System.in);

          System.out.print("Would you like to continue Y/N? ");

          if (answer.nextLine().equalsIgnoreCase("N")) {
              coolio = false;
          }

          System.out.println("-----------------------------------------------------------------------------------------\n");

      } while(coolio);

      sock.close();
  }
  private static short getOperand1() {

      System.out.print("Give that first Operand: ");
      return scan.nextShort();

  }

    private static short getOperand2() {

        System.out.print("Give the second Operand: ");
        return scan.nextShort();

    }

    private static byte getOperandCode() {

      System.out.println("\nPlease refer to this menu for the set of operations:");
      System.out.println("\tOperand Code 0 will execute addition (+)");
      System.out.println("\tOperand Code 1 will execute subtraction (-)");
      System.out.println("\tOperand Code 2 will execute a bitwise OR (|)");
      System.out.println("\tOperand Code 3 will execute a bitwise AND (&)");
      System.out.println("\tOperand Code 4 will execute division (/)");
      System.out.println("\tOperand Code 5 will execute multiplication (*)");

      System.out.print("Give the OpCode: ");
      return scan.nextByte();

    }

    private static byte getTotMessageLen() {
      return totMessageLen = 8;
    }
}
