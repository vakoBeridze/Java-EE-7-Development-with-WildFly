package vako.wildflydevelopment.chapter4.controller;

import vako.wildflydevelopment.chapter4.entity.Seat;

import javax.enterprise.event.Observes;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by vako on 3/17/15.
 */
@Named

public class BookingRecord implements Serializable {
    private int bookedCount = 0;

    public int getBookedCount() {
        return bookedCount;
    }

    public void bookEvent(@Observes Seat bookedSeat) {
        bookedCount++;
    }
}