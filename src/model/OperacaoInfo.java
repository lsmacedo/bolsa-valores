package model;

public class OperacaoInfo extends Operacao {
    
    public OperacaoInfo(String dataHora)  {
        super(dataHora);
    }
    
    @Override
    public String toString() {
        return dataHora;
    }
    
}
