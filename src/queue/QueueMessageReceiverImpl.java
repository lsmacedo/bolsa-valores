package queue;

import com.rabbitmq.client.Channel;;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import exception.QueueInitializationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class QueueMessageReceiverImpl implements QueueMessageReceiver {
	
    private QueueMessageCallback callback;
    private ConnectionFactory    factory         = new ConnectionFactory();
    private Connection           connection;
    private Channel              channel;
    private String               consumerTag;
    private String               queueName;
    private static final String  EXCHANGE_NAME   = "operacoes";

    @Override
    public void config(String host, QueueMessageCallback callback) throws QueueInitializationException {
        this.callback = callback;
        
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            System.out.println("A conexão com o host foi estabelecida com sucesso.");
        } catch (IOException | TimeoutException e) {
            throw new QueueInitializationException(e);
        }
    }

    @Override
    public void subscribe(String bindingKey) {
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void unsubscribe(String bindingKey) {
        try {
            channel.queueUnbind(queueName, EXCHANGE_NAME, bindingKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listen() throws QueueInitializationException {
        try {
            queueName = channel.queueDeclare().getQueue();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] message    = delivery.getBody();
                String routingKey = delivery.getEnvelope().getRoutingKey();
                callback.onMessage(routingKey, message);
            };
            consumerTag = channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            System.out.println("Aguardando mensagens em " + queueName + "...");
        } catch (IOException e) {
            throw new QueueInitializationException(e);
        }
    }
    
    @Override
    public void closeConnection() {
        try {
            channel.basicCancel(consumerTag);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            
        }
    }

}
