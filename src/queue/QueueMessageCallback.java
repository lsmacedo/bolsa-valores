package queue;

public interface QueueMessageCallback {
	
    public void onMessage(String queueName, byte[] message);

}
