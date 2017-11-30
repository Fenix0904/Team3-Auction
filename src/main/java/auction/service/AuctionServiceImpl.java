package auction.service;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.domain.User;
import auction.repository.AuctionRepository;
import auction.repository.AuctionStatusRepository;
import auction.repository.LotRepository;
import auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuctionServiceImpl implements AuctionService {

    private Map<Auction, Integer> cache = new ConcurrentHashMap<>();

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionStatusRepository auctionStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotRepository lotRepository;

    @Override
    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    /**
     * Only Admin have rights to delete any auction.
     * Trader (User) can delete only auction he created.
     *
     * @param auctionId - identifier of auction.
     * @return true if user have rights to delete auction, else - false.
     */
    @Override
    public boolean deleteAuction(int auctionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Auction auction = auctionRepository.findOne(auctionId);
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                lotRepository.delete(auction.getLots());
                auctionRepository.delete(auctionId);
                return true;
            }
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        for (Auction tempAuction : user.getAuctions()) {
            if (auctionId == tempAuction.getId()) {
                lotRepository.delete(auction.getLots());
                auctionRepository.delete(auctionId);
                return true;
            }
        }
        return false;
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
        List<Auction> list = auctionRepository.findAll();
        return list;
    }
}
