import exception.QueueInitializationException;
import java.io.UnsupportedEncodingException;
import queue.*;

public class MainApplication {
	
    public static final String    HOST        = "localhost"; 
    private static final String[] QUEUE_NAMES = new String[] { "Fila 1", "Fila 2", "Fila 3" };

    /**
     * @param args
     */
    public static void main(String[] args) throws QueueInitializationException {
        /* Inicializando Receiver */
        QueueMessageReceiver queueReceiver = new QueueMessageReceiverImpl();
        queueReceiver.config(HOST, messageHandler());
        
        /* Ouvindo em todas as filas */
        for (String queueName : QUEUE_NAMES) { queueReceiver.listen(queueName); }

        /* Inicializando Sender */
        QueueMessageSender queueSender = new QueueMessageSenderImpl();
        queueSender.config(HOST);
        
        /* Solicitando que usuário digite mensagens */
        BolsaValores bolsaValores = BolsaValores.getInstance();
        bolsaValores.setQueueMessageSender(queueSender);
        bolsaValores.getInputFromUser();
        
        /* Finalizando execução */
        queueReceiver.closeConnection();
        System.exit(0);
    }
	
    /**
     * Método que é chamado após uma nova mensagem ser recebida na fila
     * @return 
     */
    private static QueueMessageCallback messageHandler() {
        return (String queueName, byte[] message) -> {
            try {
                String text = new String(message, "UTF-8");
                System.out.println("----------\nMensagem recebida em " + queueName + ": " + text);
                BolsaValores bolsaValores = BolsaValores.getInstance();
                synchronized (bolsaValores) {
                    bolsaValores.notify();
                }
            } catch (UnsupportedEncodingException e) {}
        };
    }

}
