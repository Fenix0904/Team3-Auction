package auction.service;

import auction.domain.Auction;
import auction.domain.Lot;
import auction.domain.User;
import auction.repository.AuctionRepository;
import auction.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class AuctionServiceImpl implements AuctionService {

    // Map<Integer, Integer> represents Map<Auction id, Auction hashCode>
    private Map<Integer, Integer> cache = new ConcurrentHashMap<>();
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LotService lotService;

    private static final Logger log = LoggerFactory.getLogger(AuctionServiceImpl.class);

    public AuctionServiceImpl() {

    }

    @Override
    public void createAuction(Auction auction) {
        ZoneId zoneId = ZoneId.systemDefault();
        Auction result;
        if (zoneId.getRules().getOffset(ZonedDateTime.now().toInstant()).
                equals(auction.getStartDate().getZone())) {
            result = auctionRepository.save(auction);
        } else {
            auction.setStartDate(auction.getStartDate().toLocalDateTime().atZone(zoneId));
            auction.setTerminationDate(auction.getTerminationDate().toLocalDateTime().atZone(zoneId));
            result = auctionRepository.save(auction);
        }
        for (Lot lot : auction.getLots()) {
            lot.setAuction(result);
            lotService.createLot(lot);
        }
        cache.put(result.getId(), result.hashCode());
        log.info("createAuction method executed");
    }

    @Override
    public void updateAuction(Auction auction) {
        Auction storedAuction = auctionRepository.findOne(auction.getId());
        Auction result;
        if (storedAuction.getLots().size() == auction.getLots().size()) {
            lotService.updateLot(auction.getLots());
            result = auctionRepository.save(auction);
        }
        else {
            for (Lot lot : storedAuction.getLots()) {
                if (!auction.getLots().contains(lot))
                    lotService.deleteLot(lot);
            }
            lotService.updateLot(auction.getLots());
            result = auctionRepository.save(auction);
        }
        cache.put(result.getId(), result.hashCode());
        log.info("updateAuction method executed");
    }

    @Override
    public void updateAuctions(List<Auction> auctions) {
        List<Auction> result = auctionRepository.save(auctions);
        for (Auction auction : result) {
            if (cache.containsKey(auction.getId()))
                cache.put(auction.getId(), auction.hashCode());
        }
        log.info("updateAuctions method executed");
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
        log.info("deleteAuction method executed");
        return false;
    }

    @Override
    public void changeAuctionStatus(int statusId, int auctionId) {
        Auction temp = auctionRepository.findOne(auctionId);
        //temp.setAuctionStatus();
        auctionRepository.save(temp);
        log.info("changeAuction method executed");
    }

    @Override
    public List<Auction> getUsersAuctions(String username) {
        return auctionRepository.getAuctionsByTraderUsername(username);
    }

    @Override
    public List<Auction> getPlannedAndRunningAuctions() {
        return auctionRepository.getAuctionsByAuctionStatusIsNot(Auction.Status.CLOSED);
    }

    @Override
    public List<Auction> getStoppedAuctions() {
        return auctionRepository.getAuctionsByAuctionStatus(Auction.Status.CLOSED);
    }

    @Override
    public List<Auction> getOpenedAuctions(ZonedDateTime date) {
        return auctionRepository.getAuctionsByStartDateIs(date);
    }

    @Override
    public List<Auction> getClosedAuctions(ZonedDateTime date) {
        List<Auction> auctions = auctionRepository.getAuctionsByTerminationDateIs(date);
        log.info("getClosedAuctions method executed");
        return auctions;
    }

    @Override
    public List<Auction> getAuctionsByCustomParameters(ZonedDateTime dateFist,
                                                       ZonedDateTime dateSecond,
                                                       Auction.Status auctionStatus) {
        List<Auction> auctions = auctionRepository
                .getAuctionsByStartDateIsBeforeAndTerminationDateIsAfterAndAuctionStatusIs
                        (dateFist,
                                dateSecond,
                                auctionStatus);
        log.info("getAuctionsByCustomParameters executed");
        return auctions;
    }

    @Override
    public List<Auction> getAuctionsByCustomParameters(ZonedDateTime date, Auction.Status status) {
        List<Auction> auctions = auctionRepository
                .getAuctionsByTerminationDateIsBeforeAndAuctionStatusIsNot(date, status);
        log.info("getAuctionsByCustomParameters executed");
        return auctions;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Auction> auctions = auctionRepository.getAuctionsByAuctionStatusIsNot(Auction.Status.CLOSED);
        for (Auction auction : auctions) {
            cache.put(auction.getId(), auction.hashCode());
        }
    }
}
