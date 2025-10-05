/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javachat.models.User;
/**
 *
 * @author thebe
 */
public class UIPublisher {
    private static UIPublisher instance;
    private List<Runnable> subscribers = new ArrayList<>();
    
    private UIPublisher() {}
    
    public static synchronized UIPublisher getUIPublisherInstance() {
        if (instance == null) {
            instance = new UIPublisher();
            return instance;
        } else {
            return instance;
        }
    }
    
    public void subscribe(Runnable callback) {
        subscribers.add(callback);
    }
    
    public void unsubscribe(Runnable callback) {
        subscribers.removeIf(cb -> cb.equals(callback));
    }
    
    public void notifySubscribers() {
        for (Runnable sub : subscribers) {
            sub.run();
        }
    }
}
