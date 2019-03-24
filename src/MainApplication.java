import java.io.UnsupportedEncodingException;
import queue.QueueMessageCallback;
import queue.QueueMessageReceiver;
import queue.QueueMessageReceiverImpl;
import queue.QueueMessageSender;
import queue.QueueMessageSenderImpl;

public class MainApplication {
	
    public static final String    HOST       = "localhost"; 
    private static final String[] queueNames = new String[] { "Fila 1", "Fila 2", "Fila 3" };

    /**
     * @param args
     */
    public static void main(String[] args) {
        /* Receiver */
        QueueMessageReceiver queueReceiver = new QueueMessageReceiverImpl();
        queueReceiver.config(HOST, messageHandler());
        queueReceiver.listen(queueNames[0]);

        /* Sender */
        QueueMessageSender queueSender = new QueueMessageSenderImpl();
        queueSender.config(HOST);
        
        /* Permitindo que usuário digite mensagens */
        BolsaValores bolsaValores = new BolsaValores(queueReceiver, queueSender, queueNames);
        bolsaValores.getInputFromUser();
        
        /* Finalizando execução */
        queueReceiver.closeConnection();
        System.exit(0);
    }
	
    private static QueueMessageCallback messageHandler() {
        return (byte[] message) -> {
            try {
                String text = new String(message, "UTF-8");
                System.out.println("Mensagem recebida: " + text);
            } catch (UnsupportedEncodingException e) {}
        };
    }

}
