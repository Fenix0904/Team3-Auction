package auction.controller;

import auction.domain.Lot;
import auction.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/lot")
public class LotController {

    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping(value = "/create")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void createLot(@RequestBody Lot lot) {
        lotService.createLot(lot);
    }

    @PostMapping(value = "/update")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void updateLot(@RequestBody Lot lot) {
        lotService.updateLot(lot);
    }

    @PostMapping(value = "/delete/{lotId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteLot(@PathVariable int lotId) {
        lotService.deleteLot(lotId);
    }

    @GetMapping(value = "/getAll")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Lot> getAllLots() {
        return lotService.getAllLots();
    }
}
