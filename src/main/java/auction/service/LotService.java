package auction.service;

import auction.domain.Lot;

import java.util.List;

public interface LotService {

    void createLot(Lot lot);

    boolean deleteLot(Lot lot);

    void deleteLot(List<Lot> lots);

    List<Lot> getAllLots();

    void updateLot(List<Lot> lots);
}
