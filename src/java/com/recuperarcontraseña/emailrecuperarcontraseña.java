package com.recuperarcontraseña;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.mailsender.MailSender.HOST_EMAIL_GMAIL;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.dao.PersonaFacadeLocal;
import org.entidades.Persona;

/**
 *
 * @author ORLANDO
 */
@Named(value = "emailrecuperarcontraseña")
@SessionScoped
public class emailrecuperarcontraseña implements Serializable {

    private String emailRemitente;
    private String passRemitente;
    private String emailDestinatario;
    private String Contrasena;

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }
    
    
    
    private Session session;
    private MimeMessage mimeMessage;
    
    @EJB
    private PersonaFacadeLocal pfl;
    private Persona persona;
    
    
    public emailrecuperarcontraseña() {
    }
    

    public emailrecuperarcontraseña(String emailRemitente, String passRemitente, String emailDestinatario) {
        this.emailRemitente = emailRemitente;
        this.passRemitente = passRemitente;
        this.emailDestinatario = emailDestinatario;
    }
    
    
    private void init() {
        try {
            Properties propiedades = new Properties();

            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "25");//587-25
            propiedades.setProperty("mail.smtp.user", this.emailRemitente);
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.ssl.trust", HOST_EMAIL_GMAIL);

            session = Session.getDefaultInstance(propiedades);
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emailRemitente));
            mimeMessage.setRecipients(Message.RecipientType.TO, emailDestinatario);
        } catch (MessagingException ex) {
            Logger.getLogger(emailrecuperarcontraseña.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean enviarSimple(String asunto, String contenido) {
        try {
            if (asunto != null && contenido != null) {

                init();

                Multipart contenidoMensaje = new MimeMultipart();
                BodyPart text = new MimeBodyPart();
                text.setText(contenido);
                contenidoMensaje.addBodyPart(text);

                mimeMessage.setSubject(asunto);
                mimeMessage.setContent(contenidoMensaje);

                Transport transport = session.getTransport("smtp");
                transport.connect(emailRemitente, passRemitente);
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                transport.close();
                emailDestinatario = "";
                return true;
            } else {
                System.out.println("Falso");
                return false;
            }

        } catch (MessagingException ex) {
            Logger.getLogger(emailrecuperarcontraseña.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public void asignacionCorreos() throws Exception {
        System.out.println("Ingresamos a enviar correo" + emailDestinatario);
       
            if (emailDestinatario != null && !emailDestinatario.equals("")) {
               persona = pfl.recuperacontrasena(emailDestinatario);
                if (persona != null) {
                    
                    Contrasena = persona.getPassword();
                    emailrecuperarcontraseña email = new emailrecuperarcontraseña("correofixedup@gmail.com", "fixedupsena", emailDestinatario);
                    email.enviarSimple(" contraseña Fixed up", "Tu contraseña es: " + Contrasena);
                } else {
                    System.out.println("Email no esta en base de datos");
                    throw new Exception("Correo invalido");
                }
            } else {
               
                System.out.println("Email es nulo o vacio");
            }      
    }


}
