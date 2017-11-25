package auction.controller;

import auction.domain.Auction;
import auction.dto.AuctionDTO;
import auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping(value = "/create")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void createAuction(@RequestBody Auction auction) {
        auctionService.createAuction(auction);
    }

    @PostMapping(value = "/update")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void updateLot(@RequestBody Auction auction) {
        auctionService.updateAuction(auction);
    }

    @PostMapping(value = "/delete/{auctionId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteLot(@PathVariable int auctionId) {
        auctionService.deleteAuction(auctionId);
    }

    @PostMapping(value = "/changeStatus/{auctionId}/{statusId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void changeStatus(@PathVariable int statusId, @PathVariable int auctionId) {
        auctionService.changeAuctionStatus(statusId, auctionId);
    }

    @GetMapping(value = "/getAll")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<AuctionDTO> getAllAuctions() {
        return AuctionDTO.fromModel(auctionService.getAllAuctions());
    }
}
