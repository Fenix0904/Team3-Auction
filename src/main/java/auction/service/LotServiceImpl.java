package auction.service;

import auction.domain.Auction;
import auction.domain.Lot;
import auction.domain.User;
import auction.repository.LotRepository;
import auction.repository.UserRepository;
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

    @Autowired
    public LotServiceImpl(LotRepository lotRepository, UserRepository userRepository) {
        this.lotRepository = lotRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createLot(Lot lot) {
        lotRepository.save(lot);
    }

    @Override
    public void updateLot(Lot lot) {
        lotRepository.save(lot);
    }

    /**
     * Only Admin have rights to delete any lot.
     * Trader (User) can delete only lot than belongs to auction he created.
     *
     * @param lotId - identifier of lot.
     * @return true if user have rights to delete lot, else - false.
     */
    @Override
    public boolean deleteLot(int lotId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                lotRepository.delete(lotId);
                return true;
            }
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        for (Auction tempAuction : user.getAuctions()) {
            for (Lot lot : tempAuction.getLots()) {
                if (lotId == lot.getId()) {
                    lotRepository.delete(lotId);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void deleteLot(List<Lot> lots) {
        lotRepository.delete(lots);
    }

    @Override
    public List<Lot> getAllLots() {
        return lotRepository.findAll();
    }
}
