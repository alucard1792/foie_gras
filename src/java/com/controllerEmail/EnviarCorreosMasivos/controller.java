/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllerEmail.EnviarCorreosMasivos;

import com.enviarEmail.EnviarCorreosMasivos.EnviarEmail;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Orlando.R.R
 */
@Named(value = "controller")
@ViewScoped
public class controller implements Serializable {

    
    public controller() {
    }
    
    private String fromMail;
    private String username;
    private String password;
    private String toMail;
    private String subject;
    private String message;

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String EmailEnviar() {
        try {
            EnviarEmail mailSender=new EnviarEmail();
            mailSender.sendMail("correofixedup@gmail.com", "correofixedup@gmail.com", "fixedupsena", toMail, subject, message);
           
        } catch (Exception e) {
        }
        return "";
    }
    
}
