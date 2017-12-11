package auction.service;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import java.util.Date;
import java.util.List;

public interface AuctionService {

    void createAuction(Auction auction);

    void updateAuction(Auction auction);

    void updateAuctions(List<Auction> auctions);

    boolean deleteAuction(int auctionId);

    void changeAuctionStatus(int statusId, int auctionId);

    List<Auction> getAllAuctions();

    List<Auction> getOpenedAuctions(Date date);

    List<Auction> getClosedAuctions(Date date);

    List<Auction> getAuctionsQueryFirst(Date dateFist, Date dateSecond, AuctionStatus auctionStatus);

    List<Auction> getAuctionsQuerySecond(Date date, AuctionStatus auctionStatus);
}
