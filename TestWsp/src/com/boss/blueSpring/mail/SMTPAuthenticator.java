package com.boss.blueSpring.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
 
/**
 * @author viper9
 *
 */
public class SMTPAuthenticator extends Authenticator {
    public SMTPAuthenticator() {
        super();
    }
 
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "BlueSpringBoss@gmail.com";
        String password = "boss1125";
        return new PasswordAuthentication(username, password);
    }
}

