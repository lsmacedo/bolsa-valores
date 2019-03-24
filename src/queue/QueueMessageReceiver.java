package queue;

import exception.QueueInitializationException;

public interface QueueMessageReceiver {
	
    /**
     * Inicializa o receiver
     * Deve ser chamado antes de realizar qualquer operação com esta classe.
     * @param host
     * @param callback
     * @throws QueueInitializationException 
     */
    public void config(String host, QueueMessageCallback callback) throws QueueInitializationException;
	
    /**
     * Faz com que este receiver comece a ouvir na fila informada
     * @param queueName 
     */
    public void listen(String queueName) throws QueueInitializationException;
    
    /**
     * Encerra a conexão
     */
    public void closeConnection();

}
