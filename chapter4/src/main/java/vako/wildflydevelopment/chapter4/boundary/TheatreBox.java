package vako.wildflydevelopment.chapter4.boundary;

import org.jboss.logging.Logger;
import vako.wildflydevelopment.chapter4.entity.Seat;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by vako on 3/11/15.
 */
@Singleton
@Startup
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class TheatreBox implements Serializable {

    @Inject
    private Logger logger;

    @Inject
    private Event<Seat> seatEvent;

    @Lock(LockType.WRITE)
    public void buyTicket(int seatId) {
        final Seat seat = getSeat(seatId);
        final Seat bookedSeat = seat.getBookedSeat();
        addSeat(bookedSeat);
        seatEvent.fire(bookedSeat);
    }

    private Map<Integer, Seat> seats;

    @PostConstruct
    public void setupTheatre() {
        seats = new HashMap<>();
        int id = 0;
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Stalls", 40);
            addSeat(seat);
        }
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Circle", 20);
            addSeat(seat);
        }
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Balcony", 10);
            addSeat(seat);
        }
        logger.info("Seat Map constructed.");
    }

    private void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }

    @Lock(LockType.READ)
    public Collection<Seat> getSeats() {
        return Collections.unmodifiableCollection(seats.values());
    }

    @Lock(LockType.READ)
    public int getSeatPrice(int seatId) {
        return getSeat(seatId).getPrice();
    }

    @Lock(LockType.READ)
    private Seat getSeat(int seatId) {
        final Seat seat = seats.get(seatId);
        return seat;
    }
}
