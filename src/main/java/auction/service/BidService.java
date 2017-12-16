package auction.service;

import auction.utils.AuctionException;
import auction.utils.LotException;
import auction.domain.Bid;
import auction.domain.Lot;

import java.util.List;

public interface BidService {

    Lot makeBid(Bid bid) throws LotException, AuctionException;

    void deleteBids(List<Bid> bids);
}
