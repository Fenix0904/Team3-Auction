package auction.scheduling;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.repository.AuctionStatusRepository;
import auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private List<Auction> auctionsOpened, auctionsClosed;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date currentDate, date;
    private AuctionStatus open, closed;

    @Scheduled(cron = "0 * * * * *")
    public void checkAuctionStatus() {
        currentDate = new Date();
        open = auctionStatusRepository.getOne(2);
        closed = auctionStatusRepository.getOne(3);
        currentDate = new Date();
        try {
            date = sdf.parse(sdf.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        auctionsOpened = auctionService.getOpenedAuctions(date);
        auctionsClosed = auctionService.getClosedAuctions(date);
        if(!auctionsOpened.isEmpty()) {
            for(int i = 0; i < auctionsOpened.size(); i++) {
                auctionsOpened.get(i).setAuctionStatus(open);
            }
            auctionService.updateAuctions(auctionsOpened);
        }
        if(!auctionsClosed.isEmpty()) {
            for(int i = 0; i < auctionsClosed.size(); i++) {
                auctionsClosed.get(i).setAuctionStatus(closed);
            }
            auctionService.updateAuctions(auctionsClosed);
        }
    }
}