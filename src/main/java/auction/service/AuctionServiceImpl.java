package auction.service;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.domain.User;
import auction.repository.AuctionRepository;
import auction.repository.AuctionStatusRepository;
import auction.repository.LotRepository;
import auction.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class AuctionServiceImpl implements AuctionService {

    private final Map<Integer, AuctionStatus> auctionStatusCache = new HashMap<>();

    private final AuctionRepository auctionRepository;
    private final AuctionStatusRepository auctionStatusRepository;
    private final UserRepository userRepository;

    private final LotService lotService;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository,
                              AuctionStatusRepository auctionStatusRepository,
                              UserRepository userRepository,
                              LotService lotService) {
        this.auctionRepository = auctionRepository;
        this.auctionStatusRepository = auctionStatusRepository;
        this.userRepository = userRepository;
        this.lotService = lotService;

        List<AuctionStatus> auctionStatuses = this.auctionStatusRepository.findAll();
        for (AuctionStatus auctionStatus : auctionStatuses) {
            auctionStatusCache.put(auctionStatus.getId(), auctionStatus);
        }
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
    public void updateAuctions(List<Auction> auctions) {
        auctionRepository.save(auctions);
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
                try {
                    lotService.deleteLot(auction.getLots());
                    auctionRepository.delete(auctionId);
                } catch (EmptyResultDataAccessException e) {
                    return true;
                }
                return true;
            }
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        for (Auction tempAuction : user.getAuctions()) {
            if (auctionId == tempAuction.getId()) {
                try {
                    lotService.deleteLot(auction.getLots());
                    auctionRepository.delete(auctionId);
                } catch (EmptyResultDataAccessException e) {
                    return true;
                }
                return true;
            }
        }
        return false;
    }


    // TODO
    @Override
    public void changeAuctionStatus(int statusId, int auctionId) {
        Auction temp = auctionRepository.findOne(auctionId);
        temp.setAuctionStatus(auctionStatusCache.get(statusId));
        auctionRepository.save(temp);
    }

    @Override
    public List<Auction> getUsersAuctions(String username) {
        return auctionRepository.getAuctionsByTraderUsername(username);
    }

    @Override
    public List<Auction> getPlannedAndRunningAuctions() {
        return auctionRepository.getAuctionsByAuctionStatusIsNot(auctionStatusCache.get(3));
    }

    @Override
    public List<Auction> getStoppedAuctions() {
        return auctionRepository.getAuctionsByAuctionStatus(auctionStatusCache.get(3));
    }

    @Override
    public List<Auction> getOpenedAuctions(Date date) {
        return auctionRepository.getAuctionsByStartDateIs(date);
    }

    @Override
    public List<Auction> getClosedAuctions(Date date) {
        return auctionRepository.getAuctionsByTerminationDateIs(date);
    }
}
