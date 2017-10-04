/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsender;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Authenticator;
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
import org.entidades.Pedido;

/**
 *
 * @author Ismael
 */
public class ControlladorEmailPedido {

    public final static String HOST_EMAIL_GMAIL = "smtp.gmail.com";

    private String emailRemitente;
    private String passRemitente;
    private String emailDestinatario;
    private Session session;
    private MimeMessage mimeMessage;
    @Inject
    private Pedido pedido;
    
    public ControlladorEmailPedido() {
    }

    public ControlladorEmailPedido(String emailRemitente, String passRemitente, String emailDestinatario) {
        this.emailRemitente = emailRemitente;
        this.passRemitente = passRemitente;
        this.emailDestinatario = emailDestinatario;
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
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
        } catch (MessagingException ex) {
            Logger.getLogger(ControlladorEmailPedido.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean enviarSimple(String asunto, String contenido) {
        try {
            init();

            Multipart contenidoMensaje = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            texto.setText(contenido);
            contenidoMensaje.addBodyPart(texto);

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
            Logger.getLogger(ControlladorEmailPedido.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean enviar(String asunto, String contenido) {
        try {
            init();

            BodyPart adjunto = new MimeBodyPart();
            File file = new File("E:\\Ultima 14-05-2017\\Imagenes\\logoSena.png");
            adjunto.setDataHandler(new DataHandler(new FileDataSource(file)));
            adjunto.setFileName(file.getName());
            //Trick is to add the content-id header here
            adjunto.setHeader("Content-ID", "image_id");

            Multipart contenidoMensaje = new MimeMultipart();
            contenidoMensaje.addBodyPart(adjunto);
            BodyPart texto = new MimeBodyPart();
            texto.setContent(contenido + "<h1>Attached Image</h1>"
                    + "<img src=\"cid:image_id\"/>", "text/html");
            contenidoMensaje.addBodyPart(texto);

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
            Logger.getLogger(ControlladorEmailPedido.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControlladorEmailPedido.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControlladorEmailPedido.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
     public String enviarEmails() {
        try {
            System.out.println(pedido.getCorreoCliente());
            ControlladorEmailPedido controlladorEmailPedido = new ControlladorEmailPedido("correofixedup@gmail.com", "fixedupsena", pedido.getCorreoCliente());
            controlladorEmailPedido.enviarSimple("Notificacion para el pedido: " + pedido.getNombreProyecto(), "Señor " + pedido.getNombreCliente() + (pedido.getRealizoPago() == 1 ? ": Su pedido ha sido ingresado con exito al sistema, En breve su pedido sera asignado a un operario y el mismo le notificara cuando termine el proyecto. gracias" : ": No olvide realizar el pago del pedido para que pueda ser asignado a un operario. gracias."));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "usuario no existe. ", "solo se permite el envio a correos registrados en el sistema");
            fc.addMessage(null, facesMessage);
        }
        return "";
    }

}