package queue;

//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.DeliverCallback;

public class QueueMessageReceiverImpl implements QueueMessageReceiver {
	
	private QueueMessageCallback callback;

	@Override
	public void config(String host, QueueMessageCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public synchronized void listen(String queueName) {
		while (true) {
			try {
				callback.onMessage("Oi!!!".getBytes());
				wait(2000);
			} catch (Exception e) { System.err.println(e.getMessage()) ;}
		}
	}

}
