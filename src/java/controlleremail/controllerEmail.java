/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleremail;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ORLANDO
 */
@Named(value = "controllerEmail")
@SessionScoped
public class controllerEmail implements Serializable {

    public final static String HOST_EMAIL_GMAIL = "smtp.gmail.com";

    private String emailRemitente;
    private String passRemitente;
    private String emailDestinatario;
    private String asunto;
    private String contenido;

    private Session session;
    private MimeMessage mimeMessage;

    public controllerEmail() {
    }

    public controllerEmail(String emailRemitente, String passRemitente, String emailDestinatario) {
        this.emailRemitente = emailRemitente;
        this.passRemitente = passRemitente;
        this.emailDestinatario = emailDestinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    private void init() {
        try {
            Properties propiedades = new Properties();

            propiedades.setProperty("mail.smtp.host", HOST_EMAIL_GMAIL);
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "25");//587-25
            propiedades.setProperty("mail.smtp.user", this.emailRemitente);
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.ssl.trust", HOST_EMAIL_GMAIL);

            session = Session.getDefaultInstance(propiedades);
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emailRemitente));
            mimeMessage.setRecipients(Message.RecipientType.TO, (emailDestinatario));
        } catch (MessagingException ex) {
            Logger.getLogger(controllerEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean enviar(String asunto, String contenido) {
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
            Logger.getLogger(controllerEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean enviar(String asunto, String contenido, File file) {
        try {
            init();
            BodyPart texto = new MimeBodyPart();
            texto.setContent(contenido, "text/html");

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(file)));
            adjunto.setFileName(file.getName());

            Multipart contenidoMensaje = new MimeMultipart();
            contenidoMensaje.addBodyPart(texto);
            contenidoMensaje.addBodyPart(adjunto);

            mimeMessage.setSubject(asunto);

            //mimeMessage.setText(contenido);
            //mimeMessage.setContent(contenido, "text/html");
            mimeMessage.setContent(contenidoMensaje);

            Transport transport = session.getTransport("smtp");
            transport.connect(emailRemitente, passRemitente);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(controllerEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean enviar(String asunto, String contenido, List<File> files) {
        try {
            init();
            Multipart contenidoMensaje = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            texto.setContent(contenido, "text/html");
            contenidoMensaje.addBodyPart(texto);
            for (File file : files) {
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(file)));
                adjunto.setFileName(file.getName());
                contenidoMensaje.addBodyPart(adjunto);

            }

            mimeMessage.setSubject(asunto);

            //mimeMessage.setText(contenido);
            //mimeMessage.setContent(contenido, "text/html");
            mimeMessage.setContent(contenidoMensaje);

            Transport transport = session.getTransport("smtp");
            transport.connect(emailRemitente, passRemitente);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(controllerEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getEmailRemitente() {
        return emailRemitente;
    }

    public void setEmailRemitente(String emailRemitente) {
        this.emailRemitente = emailRemitente;
    }

    public String getPassRemitente() {
        return passRemitente;
    }

    public void setPassRemitente(String passRemitente) {
        this.passRemitente = passRemitente;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

}
