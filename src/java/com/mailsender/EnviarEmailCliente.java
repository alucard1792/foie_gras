/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsender;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.entidades.Pedido;

/**
 *
 * @author David
 */
public class EnviarEmailCliente implements Serializable {

    private String Asunto,
            contenido,
            destinatarios;

    @PostConstruct
    void init() {
        // dataProvider should not be null here.
    }
    @Inject
    private Pedido pedido;

    public EnviarEmailCliente() {
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String Asunto) {
        this.Asunto = Asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
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
