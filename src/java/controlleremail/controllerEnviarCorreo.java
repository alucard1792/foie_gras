/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleremail;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 *
 * @author ORLANDO
 */
@Named(value = "controllerEnviarCorreo")
@SessionScoped
public class controllerEnviarCorreo implements Serializable {

    /**
     * Creates a new instance of controllerEnviarCorreo
     */
    
    
    
    
    public controllerEnviarCorreo() {
    }
    
    
    public void enviarEmail(){
       controllerEmail cemail = new controllerEmail("correofixedup@gmail.com", "fixedupsena", "orubio04@misena.edu.co");
        cemail.enviar("juan", "123"); 
    }
    
    

}
