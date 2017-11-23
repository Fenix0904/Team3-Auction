package auction.repository;

import auction.domain.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot, Integer> {
    Lot findById(int id);

    Lot findByAuctionId(int auctionId);
}
