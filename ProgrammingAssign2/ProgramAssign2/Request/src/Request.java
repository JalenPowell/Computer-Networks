public class Request {

    public byte TML;            // Item Total Message Length

    public short Operand1;      // First Operand in Operations

    public short Operand2;      // Second Operand in Operations

    public byte OpCode;         // Op code

    public short Request;       // Requests

    public Request(short Operand1, short Operand2, byte OpCode) {

        this.Operand1 = Operand1;
        this.Operand2 = Operand2;
        this.OpCode = OpCode;

    }

    public String toString() {
        final String EOLN = System.getProperty("line.separator");
        String value = "Operand 1 = " + Operand1 + EOLN +
                "Operand 2 = " + Operand2 + EOLN +
                "OpCode = " + OpCode + EOLN;

    return value;
    }

}