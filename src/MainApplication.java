import queue.QueueMessageCallback;
import queue.QueueMessageReceiver;
import queue.QueueMessageReceiverImpl;

/**
 * @author Lucas Macedo
 *
 */
public class MainApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QueueMessageCallback callback = new QueueMessageCallback() {

			@Override
			public void onMessage(byte[] message) {
				String text = new String(message);
				System.out.println(text);
			}
			
		};
		
		QueueMessageReceiver queueReceiver = new QueueMessageReceiverImpl();
		queueReceiver.config("localhost", callback);
	}

}
