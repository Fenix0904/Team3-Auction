package auction.service;

import auction.domain.Auction;
import auction.domain.Category;
import auction.domain.Lot;

import java.util.List;

public interface UserService {

    void createAuction(Auction auction);

    void updateAuction(Auction auction);

    void deleteAuction(int auctionId);

    void changeAuctionStatus(int statusId, int auctionId);

    List<Auction> getAllAuctions();

    //void createLot(Lot lot);

    void makeBid(Auction auction, Lot lot);
}
