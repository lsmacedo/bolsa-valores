package queue;

import exception.QueueInitializationException;
import exception.QueueMessageSendingException;

public interface QueueMessageSender {

    /**
     * Inicializa o sender
     * Deve ser chamado antes de realizar qualquer operação com esta classe.
     * @param host 
     */
    public void config(String host) throws QueueInitializationException;
	
    /**
     * Envia uma mensagem na fila informada
     * @param queueName
     * @param message 
     */
    public void publish(String queueName, byte[] message) throws QueueMessageSendingException;
    
    /**
     * Encerra a conexão
     */
    public void closeConnection();

}
