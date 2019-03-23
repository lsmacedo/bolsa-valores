import queue.QueueMessageCallback;
import queue.QueueMessageReceiver;
import queue.QueueMessageReceiverImpl;
import queue.QueueMessageSender;
import queue.QueueMessageSenderImpl;

/**
 * @author Lucas Macedo
 *
 */
public class MainApplication {
	
	public static final String HOST = "localhost"; 
	private static String[] queues  = new String[] { "Fila 1", "Fila 2", "Fila 3" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Receiver */
		QueueMessageReceiver queueReceiver = new QueueMessageReceiverImpl();
		queueReceiver.config(HOST, messageHandler());
		queueReceiver.listen(queues[0]);
		
		/* Sender */
		QueueMessageSender queueSender = new QueueMessageSenderImpl();
		queueSender.config(HOST);
		queueSender.publish(queues[0], "Oi!!!".getBytes());
	}
	
	public static QueueMessageCallback messageHandler() {
		return new QueueMessageCallback() {

			@Override
			public void onMessage(byte[] message) {
				String text = new String(message);
				System.out.println(text);
			}
			
		};
	}

}
