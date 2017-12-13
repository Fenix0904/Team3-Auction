package auction.service;

import auction.domain.Auction;
import auction.domain.Lot;
import auction.domain.User;
import auction.repository.AuctionRepository;
import auction.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


@Service
public class AuctionServiceImpl implements AuctionService {


    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    private final LotService lotService;
    private static final Logger log = LoggerFactory.getLogger(AuctionServiceImpl.class);

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository,
                              UserRepository userRepository,
                              LotService lotService) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.lotService = lotService;
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
        log.info("createAuction method executed");
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionRepository.save(auction);
        log.info("updateAuction method executed");

    }

    @Override
    public void updateAuctions(List<Auction> auctions) {
        auctionRepository.save(auctions);
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
    public List<Auction> getAuctionsByCustomParameters(ZonedDateTime dateFist, ZonedDateTime dateSecond, Auction.Status auctionStatus) {
        List<Auction> auctions = auctionRepository.getAuctionsByStartDateIsBeforeAndTerminationDateIsAfterAndAuctionStatusIs(dateFist, dateSecond, auctionStatus);
        log.info("getAuctionsByCustomParameters executed");
        return auctions;
    }

    @Override
    public List<Auction> getAuctionsByCustomParameters(ZonedDateTime date, Auction.Status firstStatus, Auction.Status secondStatus) {
        List<Auction> auctions = auctionRepository.getAuctionsByTerminationDateIsBeforeAndAuctionStatusIsOrAuctionStatusIs(date, firstStatus, secondStatus);
        log.info("getAuctionsByCustomParameters executed");
        return auctions;
    }
}
