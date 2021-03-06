package model;

import java.util.Calendar;

public class OperacaoCompra extends Operacao {
    
    public OperacaoCompra(int quant, float value, String broker) {
        super(quant, value, broker);
    }
    
    public static OperacaoCompra fromByteArray(byte[] byteArray) throws Exception {
        String s         = new String(byteArray, "UTF-8");
        String[] parts   = s.split("; ");
        
        OperacaoCompra o = new OperacaoCompra(Integer.parseInt(parts[0]), Float.parseFloat(parts[1]), parts[2]);
        o.date = Calendar.getInstance();
        
        return o;
    }
    
}
