package model;

public class OperacaoVenda extends Operacao {
    
    public OperacaoVenda(int quant, float value, String broker) {
        super(quant, value, broker);
    }
    
    public static OperacaoVenda fromByteArray(byte[] byteArray) throws Exception {
        String s       = new String(byteArray, "UTF-8");
        String[] parts = s.split("; ");
        
        return new OperacaoVenda(Integer.parseInt(parts[0]), Float.parseFloat(parts[1]), parts[2]);
    }
    
}
