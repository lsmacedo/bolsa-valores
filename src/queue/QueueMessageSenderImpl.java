package queue;

import com.rabbitmq.client.ConnectionFactory;;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import exception.QueueInitializationException;
import exception.QueueMessageSendingException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueMessageSenderImpl implements QueueMessageSender {
    
    private ConnectionFactory factory = new ConnectionFactory();
    private Channel           channel;
    private Connection        connection;

    @Override
    public void config(String host) throws QueueInitializationException {
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            throw new QueueInitializationException(e);
        }
    }

    @Override
    public void publish(String topicName, String routingKey, byte[] message) throws QueueMessageSendingException {
        try {
            channel.exchangeDeclare(topicName, "topic");
            channel.basicPublish(topicName, routingKey, null, message);
        } catch (IOException e) {
            throw new QueueMessageSendingException(e);
        }
    }
    
    @Override
    public void closeConnection() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) { }
    }

}
