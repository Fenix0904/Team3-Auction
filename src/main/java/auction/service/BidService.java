package auction.service;

import auction.domain.Bid;
import auction.domain.Lot;

public interface BidService {

    void makeBid(Bid bid, Lot lot);
}
