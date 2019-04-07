package model;

import java.util.Calendar;

public class OperacaoVenda extends Operacao {
    
    public OperacaoVenda(int quant, float value, String broker) {
        super(quant, value, broker);
    }
    
    public static OperacaoVenda fromByteArray(byte[] byteArray) throws Exception {
        String s        = new String(byteArray, "UTF-8");
        String[] parts  = s.split("; ");
        
        OperacaoVenda o = new OperacaoVenda(Integer.parseInt(parts[0]), Float.parseFloat(parts[1]), parts[2]);
        o.date = Calendar.getInstance();
        
        return o;
    }
    
}
