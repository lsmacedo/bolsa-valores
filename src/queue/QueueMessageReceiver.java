package queue;

public interface QueueMessageReceiver {
	
	public void config(String host, QueueMessageCallback callback);
	
	public void listen(String queueName);

}
