import java.io.*;
import java.net.DatagramPacket;

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public Request decode(InputStream wire) throws IOException {

    DataInputStream src = new DataInputStream(wire);

    short Operand1 = src.readShort();
    short Operand2 = src.readShort();
    byte OpCode = src.readByte();

    //Deal with the first Operand
    return new Request(Operand1, Operand2, OpCode);
  }


  public Request decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
