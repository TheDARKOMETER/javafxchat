/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.controller;

import java.util.ArrayList;

/**
 *
 * @author thebe
 */
public class DataController {

    private static DataController instance;
    private int onlineUsersCount;
    private ArrayList<String> onlineUsernames;

    private DataController() {
    }

    public static synchronized DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    public int getOnlineUsersCount() {
        return onlineUsersCount;
    }

    public void setOnlineUsersCount(int onlineUsersCount) {
        this.onlineUsersCount = onlineUsersCount;
    }

    public ArrayList<String> getOnlineUsernames() {
        return onlineUsernames;
    }

    public void setOnlineUsernames(ArrayList<String> onlineUsernames) {
        this.onlineUsernames = onlineUsernames;
    }
}
