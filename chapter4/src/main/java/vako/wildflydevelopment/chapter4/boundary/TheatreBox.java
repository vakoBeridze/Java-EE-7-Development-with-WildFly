package vako.wildflydevelopment.chapter4.boundary;

import vako.wildflydevelopment.chapter4.entity.Seat;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
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
public class TheatreBox {

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
            addSeat(new Seat(++id, "Stalls", 40));
            addSeat(new Seat(++id, "Circle", 20));
            addSeat(new Seat(++id, "Balcony", 10));
        }
//        logger.info("Seat Map constructed.");
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
        if (seat == null) {
//            throw new NoSuchSeatException("Seat " + seatId + " does not exist!");
        }
        return seat;
    }
}
