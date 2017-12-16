package auction.service;

import auction.domain.Auction;
import auction.repository.BidRepository;
import auction.utils.AuctionException;
import auction.utils.LotException;
import auction.domain.Bid;
import auction.domain.Lot;
import auction.repository.LotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static auction.utils.AuctionException.ErrorCode.AUCTION_IS_CLOSED;
import static auction.utils.LotException.ErrorCode.*;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private BidRepository bidRepository;
    private Lock lock = new ReentrantLock();
    private static final Logger log = LoggerFactory.getLogger(BidServiceImpl.class);

    @Override
    public Lot makeBid(Bid bid) throws LotException, AuctionException {
        lock.lock();
        Lot currentLot = lotRepository.findOne(bid.getLot().getId());
        try {
            if (currentLot.getAuction().getAuctionStatus() == Auction.Status.CLOSED)
                throw new AuctionException(AUCTION_IS_CLOSED, currentLot.getAuction());
            if (currentLot.getCurrentPrice() != bid.getLot().getCurrentPrice())
                throw new LotException(ANOTHER_CURRENT_PRICE, currentLot);
            currentLot.setCurrentPrice(currentLot.getCurrentPrice() + bid.getBidValue());
            currentLot.getBids().add(bid);
        } finally {
            lock.unlock();
        }
        lotRepository.save(currentLot);
        bidRepository.save(bid);
        log.info("makeBid method executed");
        return currentLot;
    }

    @Override
    public void deleteBids(List<Bid> bids) {
        bidRepository.deleteInBatch(bids);
    }


}
