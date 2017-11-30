package auction.service;

import auction.domain.Bid;
import auction.domain.Lot;
import auction.domain.User;
import auction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Override
    public void makeBid(Bid bid, User user, Lot lot) {
        bidRepository.save(bid);
        user.getBids().add(bid);
        lot.getBids().add(bid);
    }
}
