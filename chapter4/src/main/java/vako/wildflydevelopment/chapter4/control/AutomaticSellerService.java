package vako.wildflydevelopment.chapter4.control;

import org.jboss.logging.Logger;
import vako.wildflydevelopment.chapter4.boundary.TheatreBox;
import vako.wildflydevelopment.chapter4.entity.Seat;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by vako on 3/17/15.
 */

@Stateless
public class AutomaticSellerService {
    @Inject
    private Logger logger;
    @Inject
    private TheatreBox theatreBox;
    @Resource
    private TimerService timerService;

    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void automaticCustomer() {
        final Optional<Seat> seatOptional = findFreeSeat();
        if (!seatOptional.isPresent()) {
            cancelTimers();
            logger.info("Scheduler	gone!");
            return;    //	No	more	seats
        }
        final Seat seat = seatOptional.get();
        theatreBox.buyTicket(seat.getId());
        logger.info("Somebody	just	booked	seat	number	" + seat.getId());
    }

    private Optional<Seat> findFreeSeat() {
        final Collection<Seat> list = theatreBox.getSeats();
        return list.stream().filter(seat -> !seat.isBooked()).findFirst();
    }

    private void cancelTimers() {
        for (Timer timer : timerService.getTimers()) {
            timer.cancel();
        }
    }
}