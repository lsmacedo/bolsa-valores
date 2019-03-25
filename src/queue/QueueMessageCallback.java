package queue;

public interface QueueMessageCallback {
	
    /**
     * Método que é chamado após uma nova mensagem ser recebida na fila
     * @param routingKey
     * @param message 
     */
    public void onMessage(String routingKey, byte[] message);

}
