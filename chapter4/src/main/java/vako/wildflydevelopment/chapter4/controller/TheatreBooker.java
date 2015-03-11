package vako.wildflydevelopment.chapter4.controller;

import org.jboss.logging.Logger;
import vako.wildflydevelopment.chapter4.boundary.TheatreBox;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by vako on 3/11/15.
 */
@Named // means	that it can be directly	referenced in our JSF pages
@SessionScoped // since	it stores the amount of money available	to the customer during its session
public class TheatreBooker implements Serializable {

    @Inject
    private Logger logger;

    private int money;

    @Inject
    private TheatreBox theatreBox;

    @Inject
    private FacesContext facesContext;

    @PostConstruct
    public void createCustomer() {
        this.money = 100;
    }

    public void bookSeat(int seatId) {

        logger.info("Booking seat    " + seatId);
        int seatPrice = theatreBox.getSeatPrice(seatId);

        if (seatPrice > money) {
//            we don’t raise Java exceptions when the customer is not able to afford a ticket.
//            Since the application is web based, we simply display a warning message to the client using JSF Faces Messages
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not enough Money !", "Registration unsuccessful");
            facesContext.addMessage(null, m);
            return;
        }

        theatreBox.buyTicket(seatId);
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Booked !", "Booking successful");
        facesContext.addMessage(null, m);

        logger.info("Seat booked.");
        money = money - seatPrice;
    }

    public int getMoney() {
        return money;
    }
}
