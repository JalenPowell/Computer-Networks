public class Response {

public byte TML;

public int result;

public byte errorCode;

public byte requestID;

    public Response(byte TML, int result, byte errorCode, byte requestID) {

        this.TML = TML;
        this.result = result;
        this.errorCode = errorCode;
        this.requestID = requestID;
    }

    public String toString() {
        final String EOLN = System.getProperty("line.separator");
        String value = "\nTML = " + TML + EOLN +
                "Result = " + result + EOLN +
                "Error Code = " + errorCode + EOLN +
                "Request ID = " + requestID + EOLN;

        return value;
    }
}
