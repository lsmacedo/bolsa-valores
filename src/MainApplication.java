import exception.QueueInitializationException;
import java.io.UnsupportedEncodingException;
import queue.*;

public class MainApplication {
	
    public static final  String   HOST   = "localhost"; 
    public static final  String   QUEUE  = "LUCAS-PC";
    private static final String[] TOPICS = new String[] { "TP1", "TP2" };

    /**
     * @param args
     */
    public static void main(String[] args) throws QueueInitializationException {
        /* Inicializando Receiver */
        QueueMessageReceiver queueReceiver = new QueueMessageReceiverImpl();
        queueReceiver.config(HOST, messageHandler());
        
        /* Inscrevendo em todos os tópicos */
        queueReceiver.listen(QUEUE);
        for (String topicName : TOPICS) { queueReceiver.subscribe(topicName); }

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
                System.out.println("\n----------\nMensagem recebida em " + queueName + ": " + text);
                BolsaValores bolsaValores = BolsaValores.getInstance();
//                synchronized (bolsaValores) {
//                    bolsaValores.notify();
//                }
            } catch (UnsupportedEncodingException e) {}
        };
    }

}
