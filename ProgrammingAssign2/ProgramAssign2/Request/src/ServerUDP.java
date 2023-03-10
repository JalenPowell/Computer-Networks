import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ServerUDP {

    static byte requestID = 1;

  public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

      int port = Integer.parseInt(args[0]);   // Receiving Port

      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

      while (true) {

          sock.receive(packet);

          RequestDecoder decoder = (args.length == 2 ?   // Which encoding
                  new RequestDecoderBin(args[1]) :
                  new RequestDecoderBin());

          Request receivedRequest = decoder.decode(packet);

          int result = 0;
          byte errorCode = 0;

          switch (receivedRequest.OpCode) {
              case 0:
                  result = receivedRequest.Operand1 + receivedRequest.Operand2;
                  break;
              case 1:
                  result = receivedRequest.Operand1 - receivedRequest.Operand2;
                  break;
              case 2:
                  result = receivedRequest.Operand1 | receivedRequest.Operand2;
                  break;
              case 3:
                  result = receivedRequest.Operand1 & receivedRequest.Operand2;
                  break;
              case 4:
                  result = receivedRequest.Operand1 / receivedRequest.Operand2;
                  break;
              case 5:
                  result = receivedRequest.Operand1 * receivedRequest.Operand2;
                  break;

              default :
                  errorCode = 127;
                  result = -1;
          }

          System.out.println(receivedRequest);
          System.out.println(" Operand 1 in Hexadecimal: " + hexaDec(receivedRequest.Operand1));
          System.out.println(" Operand 2 in Hexadecimal: " + hexaDec(receivedRequest.Operand2));
          System.out.println(" Operation Code in Hexadecimal: " + hexaDec(receivedRequest.OpCode));

          System.out.println("--------------------------------------------------------");
          System.out.println("Here is the request sent from the Client:");
          System.out.println("RequestID\t\tOperand 1\t\tOperation\t\tOperand 2\t\t");
          System.out.println(requestID +"\t\t\t\t" + receivedRequest.Operand1
                  + "\t\t\t\t" + opCodeConv(receivedRequest.OpCode) + "\t\t\t\t" + receivedRequest.Operand2);

          System.out.println("\nResult of the above request: " + result);

          System.out.println("--------------------------------------------------------");

          ResponseEncoderBin eBin = new ResponseEncoderBin();

          Response Respo = new Response((byte) 7, result, errorCode, requestID);

          byte[] ecodeArr = eBin.encode(Respo);

          packet.setData(ecodeArr);
          packet.setLength(ecodeArr.length);

          sock.send(packet);

          packet = new DatagramPacket(new byte[1024], 1024);


      }

  }

  private static String opCodeConv(int operation) {

  String rightWay = "";

  switch (operation) {
      case 0:
          rightWay = "+";
          break;

      case 1:
          rightWay = "-";
          break;

      case 2:
          rightWay = "|";
          break;

      case 3:
          rightWay = "&";
          break;

      case 4:
          rightWay = "/";
          break;

      case 5:
          rightWay = "*";
          break;

      default:
          rightWay = "Invalid Operation";
          break;
  }

    return rightWay;

  }

  public static String hexaDec(int wideRange) {

      return Integer.toHexString(wideRange).toUpperCase();

  }

}
