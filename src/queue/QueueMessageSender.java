package queue;

public interface QueueMessageSender {
	
    public void config(String host);
	
    public void publish(String queueName, byte[] message);
    
    public void closeConnection();

}
