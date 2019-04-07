package bolsa;

import exception.QueueInitializationException;
import exception.QueueMessageSendingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Operacao;
import model.OperacaoCompra;
import model.OperacaoInfo;
import model.OperacaoVenda;
import queue.*;

public class AppBolsa {
	
    public static final String       HOST   = "localhost"; 
    public static final List<String> TOPICS = Arrays.asList(new String[]{ 
        "abev3", "petr4", "vale5", "itub4", "bbdc4", "bbas3", "ciel3",
        "petr3", "hype3", "vale3", "bbse3", "ctip3", "ggbr4", "fibr3", "radl3"
    });
    private static List<Operacao> operacoes = new ArrayList<>();
    private static QueueMessageReceiver queueReceiver;
    private static QueueMessageSender queueSender;

    public static void main(String[] args) throws QueueInitializationException {
        /* Inicializando Receiver */
        queueReceiver = new QueueMessageReceiverImpl();
        queueReceiver.config(HOST, messageHandler());
        queueReceiver.listen();
        
        queueReceiver.subscribe("compra.*");
        queueReceiver.subscribe("venda.*");
        queueReceiver.subscribe("transacao.*");
        queueReceiver.subscribe("info.*");

        /* Inicializando Sender */
        queueSender = new QueueMessageSenderImpl();
        queueSender.config(HOST);
    }
	
    /**
     * Método que é chamado após uma nova mensagem ser recebida na fila
     * @return 
     */
    private static QueueMessageCallback messageHandler() {
        return (String routingKey, byte[] message) -> {
            try {
                String tipoOperacao = routingKey.split("\\.")[0];
                Operacao operacao, matchingOperation;
                switch (tipoOperacao) {
                    case "compra":
                        operacao = OperacaoCompra.fromByteArray(message);
                        operacao.setShareName(routingKey.split("\\.")[1]);
                        operacoes.add(operacao);
                        if (null != (matchingOperation = findMatchingSellOperation(operacao))) {
                            execTransaction(operacao, matchingOperation);
                        }
                        break;
                    case "venda":
                        operacao = OperacaoVenda.fromByteArray(message);
                        operacoes.add(operacao);
                        operacao.setShareName(routingKey.split("\\.")[1]);
                        if (null != (matchingOperation = findMatchingBuyOperation(operacao))) {
                            execTransaction(operacao, matchingOperation);
                        }
                        break;
                    case "info":
                        operacao = OperacaoInfo.fromByteArray(message);
                        String shareName = routingKey.split("\\.")[1];
                        for (String s : getOperationsByDateAndShareName(operacao.getDataHora(), shareName)) {
                            queueSender.publish("info." + shareName, s.getBytes());
                        }
                        break;
                }
            } catch (Exception ex) {
                Logger.getLogger(AppBolsa.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
    
    private static void execTransaction(Operacao operacao1, Operacao operacao2) {
        int quant = Integer.min(operacao1.getQuant(), operacao2.getQuant());
        float val = Float.min(operacao1.getValue(), operacao2.getQuant());
        
        operacao1.subtractQuant(quant);
        operacao2.subtractQuant(quant);
        
        if (operacao1.getQuant() == 0) operacoes.remove(operacao1);
        if (operacao2.getQuant() == 0) operacoes.remove(operacao2);
        
        String routingKey = "transacao." + operacao1.getShareName();
        String message    = quant + "; " + val;
        
        try {
            queueSender.publish(routingKey, message.getBytes());
        } catch (QueueMessageSendingException e) {
            System.err.println("Houve um erro ao enviar a mensagem: " + e.getMessage());
        }
    }
    
    private static OperacaoVenda findMatchingSellOperation(Operacao operacao) {
        OperacaoVenda operacaoVenda = null;
        for (Operacao o : operacoes) {
            if (o instanceof OperacaoVenda && operacao.getShareName().equals(o.getShareName()) &&
                operacao.getValue() >= o.getValue()) {
                return (OperacaoVenda) o;
            }
        }
        
        return operacaoVenda;
    }
    
    private static OperacaoCompra findMatchingBuyOperation(Operacao operacao) {
        OperacaoCompra operacaoCompra = null;
        for (Operacao o : operacoes) {
            if (o instanceof OperacaoCompra && 
                operacao.getShareName().equals(o.getShareName()) &&
                o.getValue() >= operacao.getValue()) {
                    return (OperacaoCompra) o;
            }
        }
        
        return operacaoCompra;
    }
    
    private static List<String> getOperationsByDateAndShareName(String dateAsString, String shareName) {
        String[] dateArray   = dateAsString.split("[\\/ :]");
        Calendar desiredDate = Calendar.getInstance();
        List<String> ops     = new ArrayList<>();
        int day              = Integer.parseInt(dateArray[0]);
        int month            = Integer.parseInt(dateArray[1]) - 1;
        int year             = Integer.parseInt(dateArray[2]);
        int hours            = Integer.parseInt(dateArray[3]);
        int minutes          = Integer.parseInt(dateArray[4]);
        int seconds          = 0;
        desiredDate.set(year, month, day, hours, minutes, seconds);
        
        for (Operacao operacao : operacoes) {
            if (operacao.getDate().compareTo(desiredDate) >= 0 && operacao.getShareName().equals(shareName)) {
                if (operacao instanceof OperacaoCompra) 
                    ops.add("Compra - " + shareName + ": " + operacao.toString());
                else ops.add("Venda - " + shareName + ": " + operacao.toString());
            }
        }
        
        return ops;
    }

}
