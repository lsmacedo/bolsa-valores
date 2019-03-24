package exception;

public class QueueInitializationException extends Exception {
    
    public QueueInitializationException(Exception e) {
        super("Houve um erro ao inicializar conexão com a fila de mensagens:\n"
            + e.getMessage());
    }
    
}
