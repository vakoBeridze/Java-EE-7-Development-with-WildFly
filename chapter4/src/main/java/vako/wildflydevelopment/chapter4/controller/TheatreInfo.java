package vako.wildflydevelopment.chapter4.controller;

import vako.wildflydevelopment.chapter4.boundary.TheatreBox;
import vako.wildflydevelopment.chapter4.entity.Seat;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by vako on 3/11/15.
 */
@Model
// @Model is an alias (we call this kind of annotations stereotypes)
// for two commonly used annotations: @Named and @RequestScoped
public class TheatreInfo {

    @Inject
    private TheatreBox box;

    protected Collection<Seat> seats;

    @PostConstruct
    public void retrieveAllSeatsOrderedByName() {
        seats = box.getSeats();
    }

    /**
     * <br>
     * <b>This method returns a list of seats, exposing it as a producer method</b>
     * <p/>
     * The producer method allows you to have control over the production of the dependency
     * objects.As a Java factory pattern, they can be used as a source of objects whose
     * implementation may vary at runtime or if the object requires some custom initialization
     * that is not to be performed in the constructor.
     * <p/>
     * It can be used to provide any kind of concrete class implementation.
     * however, it is especially useful to inject Java EE resources into your application.
     * <p/>
     * One advantage of using a @Producer annotation for the getSeats method is that its
     * objects can be exposed directly via JSF’s Expression Language(EL)
     */
    @Produces
    @Named
    public Collection<Seat> getSeats() {
//        return Lists.newArrayList(seats);
        return new ArrayList<>(seats);
    }

    //    An observer, as the name suggests, can be used to observe events.
    //    An observer method is notified whenever an object is created, removed, or updated.
    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Seat member) {
        retrieveAllSeatsOrderedByName();
    }
    /*
     Note

        To be precise, in our example, we are using a conditional observer that is denoted by the
        expression notifyObserver = Reception.IF_EXISTS.This means that in practice, the
        observer method is only called if an instance of the component already exists.If not
        specified, the default option(ALWAYS) will be that the observer method is always called. (If
        an instance doesn’t exist, it will be created.)

        In the newest CDI version, it is possible to get additional information about the fired event
        in the observer by adding an EventMetadata parameter to the observer’s method.

        Whenever a change in our list of seats occurs, we will use the
        javax.enterprise.event.Event object to notify the observer about the changes.This
        will be done in our singleton bean, which gets injected with the seat’s event,and
        notifies the observer by firing the event when a seat is booked
     */
}
