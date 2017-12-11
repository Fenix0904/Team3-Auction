package auction.controller;

import auction.domain.Auction;
import auction.domain.User;
import auction.dto.AuctionDTO;
import auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(value = "/update")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void updateLot(@RequestBody Auction auction) {
        auctionService.updateAuction(auction);
    }

    @DeleteMapping(value = "/delete/{auctionId}")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity deleteLot(@PathVariable int auctionId) {
        boolean result = auctionService.deleteAuction(auctionId);
        if (result) return new ResponseEntity(HttpStatus.OK);
        else return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("You haven't rights to delete this auction!");
    }

    @PutMapping(value = "/changeStatus/{auctionId}/{statusId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void changeStatus(@PathVariable int statusId, @PathVariable int auctionId) {
        auctionService.changeAuctionStatus(statusId, auctionId);
    }

    @GetMapping(value = "/")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<AuctionDTO> getAuctions() {
        return AuctionDTO.fromModel(auctionService.getPlannedAndRunningAuctions());
    }

    @GetMapping(value = "/closed")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<AuctionDTO> getClosedAuctions() {
        return AuctionDTO.fromModel(auctionService.getStoppedAuctions());
    }

    @GetMapping(value = "/{username}/my")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<AuctionDTO> getUsersAuctions(@PathVariable String username) {
        return AuctionDTO.fromModel(auctionService.getUsersAuctions(username));
    }

}

