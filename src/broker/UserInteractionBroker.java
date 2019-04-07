package broker;


import exception.InvalidDateFormatException;
import exception.InvalidShareNameException;
import exception.InvalidValueException;
import exception.QueueMessageSendingException;
import model.Operacao;
import model.OperacaoCompra;
import model.OperacaoVenda;
import queue.QueueMessageReceiver;
import queue.QueueMessageSender;
import util.InputController;

public class UserInteractionBroker {
    
    private        QueueMessageSender    queueSender;
    private        QueueMessageReceiver  queueReceiver;
    private static UserInteractionBroker instance = new UserInteractionBroker();
    
    /**
     * Construtor privado. Padrão Singleton
     */
    private UserInteractionBroker() { }
        
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
    public static UserInteractionBroker getInstance() {
        return UserInteractionBroker.instance;
    }
    
    /**
     * Exibe um menu para que o usuário possa escolher ações para realizar
     */
    public synchronized void showUserMenu() {
        String option = "";
        while (!option.equals("9")) {
            System.out.println("-- Menu de opções --");
            System.out.println("1- Comprar");
            System.out.println("2- Vender");
            System.out.println("3- Informações");
            System.out.println("4- Inscrever em tópico");
            System.out.println("5- Desinscrever de tópico\n");
            option = InputController.readLine("Escolha uma das opções informadas:");
            try {
                switch (option) {
                    case "1":
                        showBuyMenu();
                        break;
                    case "2":
                        showSellMenu();
                        break;
                    case "3":;
                        showInfoMenu();
                        break;
                    case "4":
                        showSubscribeMenu();
                        break;
                    case "5":
                        showUnsubscribeMenu();
                        break;
                }
                wait(500);
            } catch (InvalidShareNameException | InvalidValueException | QueueMessageSendingException | InvalidDateFormatException e) {
                System.err.println(e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Erro ao manipular threads.");
            }
        }
        System.out.println("");
    }
    
    private void showBuyMenu() throws InvalidShareNameException, InvalidValueException, QueueMessageSendingException {
        String shareName  = InputController.readLine("Informe qual ação deseja comprar:");
        if (!AppBroker.TOPICS.contains(shareName)) throw new InvalidShareNameException();
        int quant         = InputController.readInteger("Informe a quantidade que deseja comprar:");
        if (quant <= 0) throw new InvalidValueException();
        float value       = InputController.readFloat("Informe o valor pelo qual deseja comprar:");
        if (value <= 0) throw new InvalidValueException();
        
        String routingKey = "compra." + shareName;
        Operacao operacao = new OperacaoCompra(quant, value, AppBroker.BROKER);
        this.queueSender.publish(routingKey, operacao.toByteArray());
    }
    
    private void showSellMenu() throws InvalidShareNameException, InvalidValueException, QueueMessageSendingException {
        String shareName  = InputController.readLine("Informe qual ação deseja vender:");
        if (!AppBroker.TOPICS.contains(shareName)) throw new InvalidShareNameException();
        int quant         = InputController.readInteger("Informe a quantidade que deseja vender:");
        if (quant <= 0) throw new InvalidValueException();
        float value       = InputController.readFloat("Informe o valor pelo qual deseja vender:");
        if (value <= 0) throw new InvalidValueException();
        
        String routingKey = "venda." + shareName;
        Operacao operacao = new OperacaoVenda(quant, value, AppBroker.BROKER);
        this.queueSender.publish(routingKey, operacao.toByteArray());
    }
    
    private void showSubscribeMenu() throws InvalidShareNameException {
        String shareName = InputController.readLine("Informe uma ação para se inscrever:");
        if (!AppBroker.TOPICS.contains(shareName)) throw new InvalidShareNameException();
        
        this.queueReceiver.subscribe("compra." + shareName);
        this.queueReceiver.subscribe("venda." + shareName);
        this.queueReceiver.subscribe("transacao." + shareName);
    }
    
    private void showUnsubscribeMenu() throws InvalidShareNameException {
        String shareName = InputController.readLine("Informe uma ação para se desinscrever:");
        if (!AppBroker.TOPICS.contains(shareName)) throw new InvalidShareNameException();
        
        this.queueReceiver.unsubscribe("compra." + shareName);
        this.queueReceiver.unsubscribe("venda." + shareName);
        this.queueReceiver.unsubscribe("transacao." + shareName);
    }
    
    private void showInfoMenu() throws InvalidDateFormatException, InvalidShareNameException, QueueMessageSendingException {
        String shareName = InputController.readLine("Informe a ação:");
        if (!AppBroker.TOPICS.contains(shareName)) throw new InvalidShareNameException();
        String date = InputController.readLine("Informe a data (dd/mm/aaaa hh:mm):");
        if (date.split("[\\/ :]").length != 5) throw new InvalidDateFormatException();
        
        this.queueSender.publish("info." + shareName, date.getBytes());
    }
    
}
