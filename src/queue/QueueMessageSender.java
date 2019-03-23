package queue;

public interface QueueMessageSender {
	
	public void config(String host);
	
	public void publish(byte[] message);

}
