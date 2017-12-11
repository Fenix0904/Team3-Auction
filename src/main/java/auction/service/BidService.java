package auction.service;

import auction.utils.AuctionException;
import auction.utils.LotException;
import auction.domain.Bid;
import auction.domain.Lot;

public interface BidService {

    Lot makeBid(Bid bid) throws LotException, AuctionException;
}
