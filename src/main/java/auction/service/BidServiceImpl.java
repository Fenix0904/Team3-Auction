package auction.service;

import auction.domain.Bid;
import auction.domain.Lot;
import auction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BidServiceImpl implements BidService {

    private BidRepository bidRepository;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public void makeBid(Bid bid, Lot lot) {
        bidRepository.save(bid);
        lot.getBids().add(bid);
    }
}
