import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ResponseEncoderBin{

    private String encoding;   // Character encoding

    public ResponseEncoderBin() { encoding = "ISO-8859-1"; }

    public byte[] encode(Response response) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        out.writeByte(response.TML);
        out.writeInt(response.result);
        out.writeByte(response.errorCode);
        out.writeByte(response.requestID);
        out.flush();
        return buf.toByteArray();
    }
}
