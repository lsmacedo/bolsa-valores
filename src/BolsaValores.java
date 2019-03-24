
import java.util.Scanner;
import queue.QueueMessageReceiver;
import queue.QueueMessageSender;

public class BolsaValores {
    
    private final QueueMessageReceiver queueReceiver;
    private final QueueMessageSender   queueSender;
    private final String[]             queueNames;
    
    public BolsaValores(QueueMessageReceiver queueReceiver, QueueMessageSender queueSender, String[] queueNames) {
        this.queueReceiver = queueReceiver;
        this.queueSender   = queueSender;
        this.queueNames    = queueNames;
    }
    
    public void getInputFromUser() {
        String  message;
        Scanner scanner = new Scanner(System.in);
        do {
            message = scanner.nextLine();
            queueSender.publish(queueNames[0], message.getBytes());
        }
        while (!"Tchau".toLowerCase().equals(message.toLowerCase()));
    }
    
}
