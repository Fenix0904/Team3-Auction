package auction.repository;

import auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    List<Auction> getAuctionsByStartDateIs(Date date);
    List<Auction> getAuctionsByTerminationDateIs(Date date);

}
