/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.exceptions;

/**
 *
 * @author thebe
 */
public class ChatMessageException extends RuntimeException {
    public ChatMessageException(String message) {
        super(message);
    }
    
    public ChatMessageException() {
        super();
    }
}
