package queue;

import com.rabbitmq.client.Channel;;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import exception.QueueInitializationException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueMessageReceiverImpl implements QueueMessageReceiver {
	
    private QueueMessageCallback callback;
    private ConnectionFactory    factory = new ConnectionFactory();
    private Connection           connection;
    private Channel              channel;
    private String               consumerTag;

    @Override
    public void config(String host, QueueMessageCallback callback) throws QueueInitializationException {
        this.callback = callback;
        
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            System.out.println("A conexão com a fila foi estabelecida com sucesso. Aguardando mensagens...");
        } catch (IOException | TimeoutException e) {
            throw new QueueInitializationException(e);
        }
    }

    @Override
    public void listen(String queueName) throws QueueInitializationException {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] message = delivery.getBody();
                callback.onMessage(queueName, message);
            };
            consumerTag = channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
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
