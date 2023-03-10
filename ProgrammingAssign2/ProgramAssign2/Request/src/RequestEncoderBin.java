import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestEncoderBin(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(Request request) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);

    out.writeShort(request.Operand1);
    out.writeShort(request.Operand2);
    out.writeByte(request.OpCode);
    out.flush();
    return buf.toByteArray();
  }
}
