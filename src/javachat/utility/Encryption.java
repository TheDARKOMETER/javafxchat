/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.utility;

import at.favre.lib.crypto.bcrypt.BCrypt;
/**
 *
 * @author Administrator
 */
public class Encryption {
    private final int cost = 12;
    
    public String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(cost, password.toCharArray());
    }
    
    public boolean verifyPassword(String password, String bcryptHashData) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashData);
        return result.verified;
    }
    
}
