
import exception.QueueMessageSendingException;
import java.util.Scanner;
import queue.QueueMessageReceiver;
import queue.QueueMessageSender;

public class BolsaValores {
    
    private        QueueMessageSender queueSender;
    private static BolsaValores       instance = new BolsaValores();
    
    /**
     * Construtor privado. Padrão Singleton
     */
    private BolsaValores() { }
        
    /**
     * @param queueSender 
     */
    public void setQueueMessageSender(QueueMessageSender queueSender) {
        this.queueSender = queueSender;
    }
    
    /**
     * Devolve a instância de BolsaValores
     * @return 
     */
    public static BolsaValores getInstance() {
        return BolsaValores.instance;
    }
    
    /**
     * Função de teste criada para usuário enviar mensagens para filas.
     * Obs: método wait está sendo chamado para que uma nova mensagem só seja 
     * solicitada ao usuário após a anterior ter sido recebida.
     * É o messageHandler em MainApplication que está notificando esta de que
     * a mensagem foi recebida.
     * Timeout foi adicionado para caso mensagem não seja recebida.
     */
    public synchronized void getInputFromUser() {
        String  message;
        String  topic;
        Scanner scanner = new Scanner(System.in);
        
        try {
            do {
                System.out.println("\n----------");
                System.out.print("Informe um tópico: ");
                topic = scanner.nextLine();
                System.out.print("Informe uma mensagem: ");
                message = scanner.nextLine();
                queueSender.publish(MainApplication.QUEUE, topic, message.getBytes());
//                this.wait(2000); // Aguardando 2 segundos ou até mensagem ser recebida
            }
            while (!"Tchau".toLowerCase().equals(message.toLowerCase()));
        } 
//        catch (InterruptedException e) { }/
        catch (QueueMessageSendingException e) { 
            System.err.println("Infelizmente, não foi possível enviar a sua mensagem :(");
        }
    }
    
}
