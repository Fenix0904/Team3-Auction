package auction.service;
import auction.domain.Lot;
import java.util.List;

public interface LotService {

    void createLot(Lot lot);

    void updateLot(Lot lot);

    boolean deleteLot(int lotId);

    void deleteLot(List<Lot> lots);

    List<Lot> getAllLots();
}
