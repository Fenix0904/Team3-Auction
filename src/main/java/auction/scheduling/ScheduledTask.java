package auction.scheduling;

import auction.domain.Auction;
import auction.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private AuctionService auctionService;

    private ZonedDateTime currentDate;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 * * * * *")
    public void checkAuctionStatus() {
        log.info("scheduled task starting execution");
        currentDate = ZonedDateTime.now();

        List<Auction> auctionsOpened = auctionService.getOpenedAuctions(currentDate);
        List<Auction> auctionsClosed = auctionService.getClosedAuctions(currentDate);
        if (!auctionsOpened.isEmpty()) {
            for (Auction anAuctionsOpened : auctionsOpened) {
                anAuctionsOpened.setAuctionStatus(Auction.Status.RUNNING);
            }
            auctionService.updateAuctions(auctionsOpened);
        }
        if (!auctionsClosed.isEmpty()) {
            for (Auction anAuctionsClosed : auctionsClosed) {
                anAuctionsClosed.setAuctionStatus(Auction.Status.CLOSED);
            }
            auctionService.updateAuctions(auctionsClosed);
        }
        log.info("scheduled task executed");
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("scheduled sturtup task starting execution");
        currentDate = ZonedDateTime.now();
        List<Auction> auctionsToOpen = auctionService.getAuctionsByCustomParameters(currentDate, currentDate, Auction.Status.PLANNED);
        List<Auction> auctionsToClose = auctionService.getAuctionsByCustomParameters(currentDate, Auction.Status.PLANNED, Auction.Status.RUNNING);

        if (!auctionsToOpen.isEmpty()) {
            for (int i = 0; i < auctionsToOpen.size(); i++) {
                auctionsToOpen.get(i).setAuctionStatus(Auction.Status.RUNNING);
            }
            auctionService.updateAuctions(auctionsToOpen);
        }
        if (!auctionsToClose.isEmpty()) {
            for (int i = 0; i < auctionsToClose.size(); i++) {
                auctionsToClose.get(i).setAuctionStatus(Auction.Status.CLOSED);
            }
            auctionService.updateAuctions(auctionsToClose);
        }
        log.info("scheduled startup task executed");
    }
}
