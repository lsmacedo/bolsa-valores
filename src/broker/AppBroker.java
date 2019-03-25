package broker;

import exception.InvalidShareNameException;
import exception.QueueInitializationException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import model.Operacao;
import queue.*;

public class AppBroker {
	
    public static final String       HOST   = "localhost"; 
    public static final String       BROKER = "BROKER1";
    public static final List<String> TOPICS = Arrays.asList(new String[]{ 
        "abev3", "petr4", "vale5", "itub4", "bbdc4", "bbas3", "ciel3",
        "petr3", "hype3", "vale3", "bbse3", "ctip3", "ggbr4", "fibr3", "radl3"
    });

    /**
     * @param args
     */
    public static void main(String[] args) throws QueueInitializationException {
        /* Inicializando Receiver */
        QueueMessageReceiver queueReceiver = new QueueMessageReceiverImpl();
        queueReceiver.config(HOST, messageHandler());
        queueReceiver.listen(BROKER);

        /* Inicializando Sender */
        QueueMessageSender queueSender = new QueueMessageSenderImpl();
        queueSender.config(HOST);
        
        /* Solicitando que usuário digite mensagens */
        UserInteractionBroker bolsaValores = UserInteractionBroker.getInstance();
        bolsaValores.setQueueMessageReceiver(queueReceiver);
        bolsaValores.setQueueMessageSender(queueSender);
        bolsaValores.showUserMenu();
        
        /* Finalizando execução */
        queueReceiver.closeConnection();
        System.exit(0);
    }
	
    /**
     * Método que é chamado após uma nova mensagem ser recebida na fila
     * @return 
     */
    private static QueueMessageCallback messageHandler() {
        return (String routingKey, byte[] message) -> {
            Operacao operation = Operacao.fromByteArray(message);
            if (operation != null) {
                System.out.println(
                    "\n---------------\n"
                    + "Operação recebida em " + routingKey + ": " + operation.toString()
                    + "\n---------------\n"
                );
                UserInteractionBroker bolsaValores = UserInteractionBroker.getInstance();
            }
        };
    }

}
