/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Lucas
 */
public class QueueMessageSendingException extends Exception {
    
    public QueueMessageSendingException(Exception e) {
        super("Houve um erro ao enviar a mensagem:\n" + e.getMessage());
    }
    
}
