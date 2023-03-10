import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;

public class ResponseDecoderBin implements ResponseDecoder{

    private String encoding;

    public ResponseDecoderBin() { encoding = "ISO-8859-1"; }

    public ResponseDecoderBin(String encoding) { this.encoding = encoding; }

    public Response decode(InputStream wire) throws IOException {

        DataInputStream src = new DataInputStream(wire);

        byte TML = src.readByte();
        int result = src.readInt();
        byte errorCode = src.readByte();
        byte requestID = src.readByte();

        return new Response(TML, result, errorCode, requestID);

    }

    public Response decode(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decode(payload);
    }
}
