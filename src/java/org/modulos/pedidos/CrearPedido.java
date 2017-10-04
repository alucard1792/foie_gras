/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.pedidos;

import com.controllerEmail.EnviarCorreosMasivos.controller;

import com.mailsender.EnviarEmailCliente;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
import org.dao.MateriaPrimaFacadeLocal;
import org.dao.PedidoFacadeLocal;
import org.dao.StockFacadeLocal;
import org.entidades.MateriaPrima;
import org.entidades.Pedido;
import org.entidades.Stock;
import org.login.ControladorSesion;

/**
 *
 * @author David
 */
@Named(value = "crearPedido")
@ViewScoped
public class CrearPedido implements Serializable {

    @EJB
    private PedidoFacadeLocal pedidoFacadeLocal;
    @EJB
    private MateriaPrimaFacadeLocal materiaPrimaFacadeLocal;
    @EJB
    private StockFacadeLocal stockFacadeLocal;
    @Inject
    private ControladorSesion controladorSesion;
    private Pedido pedido;
    private List<MateriaPrima> listaMateriaPrima;
    private Stock stockMateriaPrima;
    private MateriaPrima materiaPrima;
    private CrearPedido controlladorEmailPedido;
    public final static String HOST_EMAIL_GMAIL = "smtp.gmail.com";
    private String emailRemitente;
    private String passRemitente;
    private String emailDestinatario;
    private Session session;
    private MimeMessage mimeMessage;

    public CrearPedido() {
    }

    @PostConstruct
    public void init() {
        emailRemitente = "correofixedup@gmail.com";
        passRemitente = "fixedupsena";
        listaMateriaPrima = materiaPrimaFacadeLocal.listarPorStockMayor0();
        pedido = new Pedido();

    }

    public void inicializarProperties() {
        try {
            Properties propiedades = new Properties();

            propiedades.setProperty("mail.smtp.host", HOST_EMAIL_GMAIL);
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
           // propiedades.setProperty("mail.smtp.port", "587");//587-25
            propiedades.setProperty("mail.smtp.user", this.emailRemitente);
            //propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.ssl.trust", HOST_EMAIL_GMAIL);
            propiedades.setProperty("mail.smtp.host", "smtp.xyz.in");
            propiedades.setProperty("mail.smtp.socketFactory.port", "25");

            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.port", "25");
            propiedades.setProperty("mail.smtp.dsn.notify",
                      "SUCCESS ORCPT=rfc822;");
            propiedades.setProperty("mail.smtp.dsn.ret", "FULL");
            session = Session.getDefaultInstance(propiedades);
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emailRemitente));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(pedido.getCorreoCliente()));
        } catch (MessagingException ex) {
            Logger.getLogger(CrearPedido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ControladorSesion getControladorSesion() {
        return controladorSesion;
    }

    public void setControladorSesion(ControladorSesion controladorSesion) {
        this.controladorSesion = controladorSesion;
    }

    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
    }

    public void setListaMateriaPrima(List<MateriaPrima> listaMateriaPrima) {
        this.listaMateriaPrima = listaMateriaPrima;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String crear() {
        inicializarProperties(); 
        emailRemitente = "correofixedup@gmail.com";
        passRemitente = "fixedupsena";
        emailDestinatario = pedido.getCorreoCliente();
        try {

            if (enviarSimple(passRemitente, passRemitente)) {
                materiaPrima = materiaPrimaFacadeLocal.find(pedido.getMateriasPrimaIdMateria().getIdMateria());
                pedido.setMateriasPrimaIdMateria(materiaPrima);
                pedido.setVendedorIdPersona(controladorSesion.getP());
                pedidoFacadeLocal.create(pedido);
                return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";
            } else {
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo enviar el correo", "Contacte con el admin");
                FacesContext.getCurrentInstance().addMessage(null, msj);
                return "";

            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al crear el pedido, por favor contacte al admin", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
            return "";
        }

    }

    public void enviarCorreoCliente() {
        controller controladorCorreos = new controller();
        controladorCorreos.setToMail(pedido.getCorreoCliente());
        String mensaje = "Señor " + pedido.getNombreCliente() + (pedido.getRealizoPago() == 1 ? ":\nSu pedido ha sido ingresado con exito al sistema, En breve su pedido sera asignado a un operario y el mismo le notificara cuando termine el proyecto. gracias" : "\nNo olvide realizar el pago del pedido para que pueda ser asignado a un operario. gracias.");
        controladorCorreos.setMessage(mensaje);
        controladorCorreos.setToMail(pedido.getCorreoCliente());
        controladorCorreos.setSubject("Notificacion para el pedido: " + pedido.getNombreProyecto());
        controladorCorreos.EmailEnviar();
    }

    public String cancelar() {
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

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
            Logger.getLogger(CrearPedido.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
