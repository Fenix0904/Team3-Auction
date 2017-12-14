package auction.service;

import auction.domain.Auction;

import java.time.ZonedDateTime;
import java.util.List;

public interface AuctionService {

    void createAuction(Auction auction);

    void updateAuction(Auction auction);

    void updateAuctions(List<Auction> auctions);

    boolean deleteAuction(int auctionId);

    void changeAuctionStatus(int statusId, int auctionId);

    List<Auction> getUsersAuctions(String username);

    List<Auction> getOpenedAuctions(ZonedDateTime date);

    List<Auction> getClosedAuctions(ZonedDateTime date);

    List<Auction> getPlannedAndRunningAuctions();

    List<Auction> getStoppedAuctions();

    /**
     * Method return all "planned" auctions was not opened because of something.
     *
     * @param dateFist - start date of auction
     * @param dateSecond - termination date
     * @param auctionStatus - auction status. Must be "PLANNED", otherwise it doesn't make sense.
     * @return list of auctions which status wasn't changed because, for example,
     * server was down.
     */
    List<Auction> getAuctionsByCustomParameters(ZonedDateTime dateFist, ZonedDateTime dateSecond, Auction.Status auctionStatus);

    /**
     * Method return all "planned" and "opened" auctions that wasn't closed
     * because server was down.
     *
     * @param date - current time of server
     * @return list of auctions that wasn't opened or wasn't closed
     */
    List<Auction> getAuctionsByCustomParameters(ZonedDateTime date, Auction.Status status);
}
