
import java.util.Scanner;
import queue.QueueMessageReceiver;
import queue.QueueMessageSender;

public class BolsaValores {
    
    private        QueueMessageSender queueSender;
    private static BolsaValores       instance = new BolsaValores();
    
    private BolsaValores() {
        
    }
        
    public void setQueueMessageSender(QueueMessageSender queueSender) {
        this.queueSender = queueSender;
    }
    
    public static BolsaValores getInstance() {
        return BolsaValores.instance;
    }
    
    public synchronized void getInputFromUser() {
        String  message;
        String  queue;
        Scanner scanner = new Scanner(System.in);
        
        try {
            do {
                System.out.println("----------");
                System.out.print("Informe uma fila: ");
                queue = scanner.nextLine();
                System.out.print("Informe uma mensagem: ");
                message = scanner.nextLine();
                queueSender.publish(queue, message.getBytes());
                this.wait(2000); // Aguardando 2 segundos ou até mensagem ser recebida
            }
            while (!"Tchau".toLowerCase().equals(message.toLowerCase()));
        } catch (InterruptedException e) { }
    }
    
}
