package queue;

//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.DeliverCallback;

public class QueueMessageReceiverImpl implements QueueMessageReceiver {

	@Override
	public void config(String host, QueueMessageCallback callback) {
		callback.onMessage("Oi!!!".getBytes());
	}

}
