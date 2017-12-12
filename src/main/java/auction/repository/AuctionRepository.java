package auction.repository;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    List<Auction> getAuctionsByTraderUsername(String username);

    List<Auction> getAuctionsByAuctionStatusIsNot(AuctionStatus status);

    List<Auction> getAuctionsByAuctionStatus(AuctionStatus status);

    List<Auction> getAuctionsByStartDateIs(Date date);

    List<Auction> getAuctionsByTerminationDateIs(Date date);

    List<Auction> getAuctionsByStartDateIsBeforeAndTerminationDateIsAfterAndAuctionStatusIs(Date dateFist, Date dateSecond, AuctionStatus auctionStatus);

    List<Auction> getAuctionsByTerminationDateIsBeforeAndAuctionStatusIsOrAuctionStatusIs(Date date, AuctionStatus val1, AuctionStatus val2);
}
