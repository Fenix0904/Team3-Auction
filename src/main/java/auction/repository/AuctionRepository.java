package auction.repository;

import auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    List<Auction> getAuctionsByTraderUsername(String username);

    List<Auction> getAuctionsByAuctionStatusIsNot(Auction.Status status);

    List<Auction> getAuctionsByAuctionStatus(Auction.Status status);

    List<Auction> getAuctionsByStartDateIs(ZonedDateTime date);

    List<Auction> getAuctionsByTerminationDateIs(ZonedDateTime date);

    List<Auction> getAuctionsByStartDateIsBeforeAndTerminationDateIsAfterAndAuctionStatusIs(ZonedDateTime dateFist, ZonedDateTime dateSecond, Auction.Status auctionStatus);

    List<Auction> getAuctionsByTerminationDateIsBeforeAndAuctionStatusIsNot(ZonedDateTime date, Auction.Status status);
}
