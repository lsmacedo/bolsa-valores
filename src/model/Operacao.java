package model;

public abstract class Operacao {
    
    protected int    quant;
    protected float  value;
    protected String broker;
    protected String dataHora;
    protected String shareName;
    
    public Operacao(int quant, float value, String broker) {
        this.quant    = quant;
        this.value    = value;
        this.broker   = broker;
    }
    
    public Operacao(String dataHora) {
        this.dataHora = dataHora;
    }

    public int getQuant() {
        return quant;
    }

    public float getValue() {
        return value;
    }

    public String getBroker() {
        return broker;
    }

    public String getDataHora() {
        return dataHora;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }
    
    public void subtractQuant(int quant) {
        this.quant -= quant;
    }
    
    @Override
    public String toString() {
        return this.quant + "; " + this.value + "; " + this.broker;
    }
    
    public byte[] toByteArray() {
        return this.toString().getBytes();
    }
    
}
