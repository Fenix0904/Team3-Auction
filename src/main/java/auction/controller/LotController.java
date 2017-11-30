package auction.controller;

import auction.domain.Lot;
import auction.dto.LotDTO;
import auction.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(value = "/update")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void updateLot(@RequestBody Lot lot) {
        lotService.updateLot(lot);
    }

    @DeleteMapping(value = "/delete/{lotId}")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity deleteLot(@PathVariable int lotId) {
        boolean result = lotService.deleteLot(lotId);
        if (result) {
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You haven't rights to delete this lot!");
        }
    }

    @GetMapping(value = "/getAll")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<LotDTO> getAllLots() {
        return LotDTO.fromModel(lotService.getAllLots());
    }
}
