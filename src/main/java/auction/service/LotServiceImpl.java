package auction.service;

import auction.domain.Auction;
import auction.domain.Lot;
import auction.domain.Photo;
import auction.domain.User;
import auction.repository.LotRepository;
import auction.repository.PhotoRepository;
import auction.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;
    @Autowired
    private BidService bidService;
    private static final Logger log = LoggerFactory.getLogger(LotServiceImpl.class);


    @Autowired
    public LotServiceImpl(LotRepository lotRepository, UserRepository userRepository, PhotoService photoService) {
        this.lotRepository = lotRepository;
        this.userRepository = userRepository;
        this.photoService = photoService;
    }

    @Override
    public void createLot(Lot lot) {
        Lot result = lotRepository.save(lot);
        photoService.addPhotos(lot.getPhotos(), result);
        log.info("createLot method executed");
    }

    @Override
    public void updateLot(List<Lot> lot) {
        lotRepository.save(lot);
        log.info("updateLot method executed");
    }

    /**
     * Only Admin have rights to delete any lot.
     * Trader (User) can delete only lot than belongs to auction he created.
     *
     * @return true if user have rights to delete lot, else - false.
     */
    @Override
    public boolean deleteLot(Lot lot) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                bidService.deleteBids(lot.getBids());
                lotRepository.delete(lot);
                return true;
            }
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        for (Auction tempAuction : user.getAuctions()) {
            for (Lot item : tempAuction.getLots()) {
                if (lot.getId() == item.getId()) {
                    bidService.deleteBids(lot.getBids());
                    lotRepository.delete(lot);
                    return true;
                }
            }
        }
        log.info("deleteLot method executed");
        return false;
    }

    @Override
    public void deleteLot(List<Lot> lots) {
        for (Lot lot : lots) {
            bidService.deleteBids(lot.getBids());
        }
        lotRepository.delete(lots);
        log.info("deleteLot method executed");

    }

    @Override
    public List<Lot> getAllLots() {
        log.info("getAllLots method executed");
        return lotRepository.findAll();
    }
}
