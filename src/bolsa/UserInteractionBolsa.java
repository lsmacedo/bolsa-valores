package bolsa;

import broker.UserInteractionBroker;
import exception.InvalidShareNameException;
import exception.InvalidValueException;
import exception.QueueMessageSendingException;
import queue.QueueMessageReceiver;
import queue.QueueMessageSender;
import util.InputController;

public class UserInteractionBolsa {
    
    private        QueueMessageSender    queueSender;
    private        QueueMessageReceiver  queueReceiver;
    private static UserInteractionBolsa  instance = new UserInteractionBolsa();
    
    /**
     * Construtor privado. Padrão Singleton
     */
    private UserInteractionBolsa() { }
        
    public void setQueueMessageReceiver(QueueMessageReceiver queueReceiver) {
        this.queueReceiver = queueReceiver;
    }
    
    /**
     * @param queueSender 
     */
    public void setQueueMessageSender(QueueMessageSender queueSender) {
        this.queueSender = queueSender;
    }
    
    /**
     * Devolve a instância deste menu
     * @return 
     */
    public static UserInteractionBolsa getInstance() {
        return UserInteractionBolsa.instance;
    }
    
    /**
     * Exibe um menu para que o usuário possa escolher ações para realizar
     */
    public synchronized void showUserMenu() {
        System.out.println("O aplicativo da bolsa ainda não foi desenvolvido.");
        System.out.println("Favor executar o AppBroker.");
    }
    
}
