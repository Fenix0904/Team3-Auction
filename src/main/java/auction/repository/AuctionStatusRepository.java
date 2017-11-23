package auction.repository;

import auction.domain.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionStatusRepository extends JpaRepository<AuctionStatus, Integer> {
}
