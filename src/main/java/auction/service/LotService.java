package auction.service;

import auction.domain.Lot;

import java.util.List;

public interface LotService {

    void createLot();

    void updateLot();

    void deleteLot(int lotId);

    List<Lot> getAllLots();
}
