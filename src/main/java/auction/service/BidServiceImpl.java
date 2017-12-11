package auction.service;

import auction.utils.LotException;
import auction.domain.Bid;
import auction.domain.Lot;
import auction.repository.LotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static auction.utils.LotException.ErrorCode.*;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private LotRepository lotRepository;
    private static final Logger log = LoggerFactory.getLogger(BidServiceImpl.class);

    @Override
    public Lot makeBid(Bid bid) throws LotException {
        Lot currentLot = lotRepository.findOne(bid.getLot().getId());
        Lock lock = new ReentrantLock(); //TODO lock or synchronized block?
        lock.lock();
        try {
            if (currentLot.getCurrentPrice() != bid.getLot().getCurrentPrice()) {
                throw new LotException(ANOTHER_CURRENT_PRICE, currentLot);
            }
            if (currentLot.getLotQuantity() == 0) {
                throw new LotException(NO_ITEM, currentLot);
            }
            currentLot.setCurrentPrice(currentLot.getCurrentPrice() + currentLot.getBidStep());
        } finally {
            lock.unlock();
        }
        lotRepository.save(currentLot);
        log.info("makeBid method executed");
        return currentLot;
    }
}
