package model;

public class OperacaoInfo extends Operacao {
    
    public OperacaoInfo(String dataHora)  {
        super(dataHora);
    }
    
    @Override
    public String toString() {
        return dataHora;
    }
    
    public static OperacaoInfo fromByteArray(byte[] byteArray) throws Exception {
        String s = new String(byteArray, "UTF-8");
        
        return new OperacaoInfo(s);
    }
    
}
