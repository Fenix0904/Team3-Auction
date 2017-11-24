package auction.service;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.domain.Lot;
import auction.repository.AuctionRepository;
import auction.repository.AuctionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuctionServiceImpl implements AuctionService {

    private Map<Auction, Integer> cache = new ConcurrentHashMap<>();

    private final AuctionRepository auctionRepository;
    private final AuctionStatusRepository auctionStatusRepository;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository,
                              AuctionStatusRepository auctionStatusRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionStatusRepository = auctionStatusRepository;
    }

    @Override
    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Override
    public void deleteAuction(int auctionId) {
        auctionRepository.delete(auctionId);
    }

    @Override
    public void changeAuctionStatus(int statusId, int auctionId) {
        Auction temp = auctionRepository.findOne(auctionId);
        AuctionStatus status = auctionStatusRepository.findOne(statusId);
        temp.setAuctionStatus(status);
        auctionRepository.save(temp);
    }
    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }
}
