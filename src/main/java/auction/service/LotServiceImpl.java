package auction.service;

import auction.domain.Lot;
import auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    @Autowired
    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public void createLot(Lot lot) {
        lotRepository.save(lot);
    }

    @Override
    public void updateLot(Lot lot) {
        lotRepository.save(lot);
    }

    @Override
    public void deleteLot(int lotId) {
        lotRepository.delete(lotId);
    }

    @Override
    public List<Lot> getAllLots() {
        return lotRepository.findAll();
    }
}
