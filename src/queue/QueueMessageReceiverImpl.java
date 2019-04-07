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
    private List<String>         connectedQueues = new ArrayList<String>();

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
    public void subscribe(String topicName, String bindingKey) {
        try {
            channel.exchangeDeclare(topicName, "topic");
            for (String queueName : connectedQueues) 
                channel.queueBind(queueName, topicName, bindingKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void unsubscribe(String topicName, String bindingKey) {
        try {
            for (String queueName : connectedQueues) 
                channel.queueUnbind(queueName, topicName, bindingKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listen(String queueName) throws QueueInitializationException {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] message    = delivery.getBody();
                String routingKey = delivery.getEnvelope().getRoutingKey();
                callback.onMessage(routingKey, message);
            };
            consumerTag = channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            connectedQueues.add(queueName);
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
