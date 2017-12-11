package auction.scheduling;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.repository.AuctionStatusRepository;
import auction.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionStatusRepository auctionStatusRepository;

    private List<Auction> auctionsOpened, auctionsClosed, auctionsToOpened, auctionsToClosed;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date currentDate, date;
    private AuctionStatus planned, open, closed;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 * * * * *")
    public void checkAuctionStatus() {
        log.info("scheduled task starting execution");
        currentDate = new Date();
        try {
            date = sdf.parse(sdf.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        auctionsOpened = auctionService.getOpenedAuctions(date);
        auctionsClosed = auctionService.getClosedAuctions(date);
        if (!auctionsOpened.isEmpty()) {
            for (int i = 0; i < auctionsOpened.size(); i++) {
                auctionsOpened.get(i).setAuctionStatus(open);
            }
            auctionService.updateAuctions(auctionsOpened);
        }
        if (!auctionsClosed.isEmpty()) {
            for (int i = 0; i < auctionsClosed.size(); i++) {
                auctionsClosed.get(i).setAuctionStatus(closed);
            }
            auctionService.updateAuctions(auctionsClosed);
        }
        log.info("scheduled task executed");
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("scheduled sturtup task starting execution");
        planned = auctionStatusRepository.getOne(1);
        open = auctionStatusRepository.getOne(2);
        closed = auctionStatusRepository.getOne(3);
        currentDate = new Date();
        try {
            date = sdf.parse(sdf.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        auctionsToOpened = auctionService.getAuctionsQueryFirst(date, date, planned);
        auctionsToClosed = auctionService.getAuctionsQuerySecond(date, open);

        if (!auctionsToOpened.isEmpty()) {
            for (int i = 0; i < auctionsToOpened.size(); i++) {
                auctionsToOpened.get(i).setAuctionStatus(open);
            }
            auctionService.updateAuctions(auctionsToOpened);
        }
        if (!auctionsToClosed.isEmpty()) {
            for (int i = 0; i < auctionsToClosed.size(); i++) {
                auctionsToClosed.get(i).setAuctionStatus(closed);
            }
            auctionService.updateAuctions(auctionsToClosed);
        }
        log.info("scheduled sturtup task executed");
    }
}
