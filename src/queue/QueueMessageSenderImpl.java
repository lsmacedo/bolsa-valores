package queue;

import com.rabbitmq.client.ConnectionFactory;;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueMessageSenderImpl implements QueueMessageSender {
    
    private ConnectionFactory factory = new ConnectionFactory();
    private Channel           channel;
    private Connection        connection;

    @Override
    public void config(String host) {
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            System.err.println("Houve um erro ao estabelecer conexão com a fila de mensagens:\n" + e.getMessage());
        }
    }

    @Override
    public void publish(String queueName, byte[] message) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message);
        } catch (IOException e) {
            System.err.println("Houve um erro ao enviar sua mensagem:\n" + e.getMessage());
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
