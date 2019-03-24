package queue;

public interface QueueMessageCallback {
	
    /**
     * Método que é chamado após uma nova mensagem ser recebida na fila
     * @param queueName
     * @param message 
     */
    public void onMessage(String queueName, byte[] message);

}
