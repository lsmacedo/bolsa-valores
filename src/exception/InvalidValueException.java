package exception;

public class InvalidValueException extends Exception {
    
    public InvalidValueException() {
        super("Por favor, informe um valor numérico válido.");
    }
    
}
