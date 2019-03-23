package queue;

public interface QueueMessageCallback {
	
	public void onMessage(byte[] message);

}
