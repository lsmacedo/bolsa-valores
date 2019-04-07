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
     * Inscreve receiver em um tópico
     * @param bindingKey
     */
    public void subscribe(String bindingKey);
    
    /**
     * Cancela interesse de receiver em um tópico
     * @param bindingKey 
     */
    public void unsubscribe(String bindingKey);
	
    /**
     * Faz com que este receiver comece a ouvir
     */
    public void listen() throws QueueInitializationException;
    
    /**
     * Encerra a conexão
     */
    public void closeConnection();

}
