package vako.wildflydevelopment.chapter4.controller;

import vako.wildflydevelopment.chapter4.boundary.TheatreBox;
import vako.wildflydevelopment.chapter4.entity.Seat;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by vako on 3/17/15.
 */
@Model
public class Poller {
    @Inject
    private TheatreBox theatreBox;

    public boolean isPollingActive() {
        return areFreeSeatsAvailable();
    }

    private boolean areFreeSeatsAvailable() {
        final Optional<Seat> firstSeat = theatreBox.getSeats().stream().filter(seat -> !seat.isBooked()).findFirst();
        return firstSeat.isPresent();
    }
}
