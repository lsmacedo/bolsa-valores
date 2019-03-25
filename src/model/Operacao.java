package model;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Operacao implements Serializable {
    
    protected int    quant;
    protected float  value;
    protected String broker;
    protected String dataHora;
    
    public Operacao(int quant, float value, String broker) {
        this.quant    = quant;
        this.value    = value;
        this.broker   = broker;
    }
    
    public Operacao(String dataHora) {
        this.dataHora = dataHora;
    }
    
    @Override
    public String toString() {
        return this.quant + ";" + this.value + ";" + this.broker;
    }
    
    public byte[] toByteArray() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            return bos.toByteArray();
        } catch (IOException ex) {
            System.err.println("Erro ao serializar operação.");
        }
        return new byte[1];
    }
    
    public static Operacao fromByteArray(byte[] byteArray) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            ObjectInput in = new ObjectInputStream(bis);
            return (Operacao) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao desserializar operação.");
            e.printStackTrace();
        }
        return null;
    }
    
}
