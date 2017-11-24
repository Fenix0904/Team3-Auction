package auction.service;
import auction.domain.Lot;
import java.util.List;

public interface LotService {

    void createLot(Lot lot);

    void updateLot(Lot lot);

    void deleteLot(int lotId);

    List<Lot> getAllLots();
}
