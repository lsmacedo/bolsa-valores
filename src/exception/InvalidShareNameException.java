package exception;

public class InvalidShareNameException extends Exception {
    
    public InvalidShareNameException() {
        super("O nome da ação informada é inválido.");
    }
    
}
