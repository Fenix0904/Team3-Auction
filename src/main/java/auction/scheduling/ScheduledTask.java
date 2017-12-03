package auction.scheduling;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.repository.AuctionStatusRepository;
import auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionStatusRepository auctionStatusRepository;
    private List<Auction> auctions;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date currentDate, afterDate;
    private AuctionStatus planned, open, closed;

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void checkAuctionStatus() {
        currentDate = new Date();
        planned = auctionStatusRepository.getOne(1);
        open = auctionStatusRepository.getOne(2);
        closed = auctionStatusRepository.getOne(3);
        auctions = auctionService.getAllAuctions();

        for(int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            if(auction.getStartDate().after(currentDate))
            {
                Auction auctionTemp = auctions.get(i);
                auctionTemp.setAuctionStatus(planned);
                auctionService.updateAuction(auctionTemp);
            }
            else if(auction.getTerminationDate().after(currentDate) || auction.getStartDate().equals(currentDate))
            {
                Auction auctionTemp = auctions.get(i);
                auctionTemp.setAuctionStatus(open);
                auctionService.updateAuction(auctionTemp);
            }
            else if(auction.getTerminationDate().before(currentDate) || auction.getTerminationDate().equals(currentDate))
            {
                Auction auctionTemp = auctions.get(i);
                auctionTemp.setAuctionStatus(closed);
                auctionService.updateAuction(auctionTemp);
            }
        }
    }
}