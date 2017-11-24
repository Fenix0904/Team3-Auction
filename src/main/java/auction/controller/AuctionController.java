package auction.controller;

import auction.domain.Auction;
import auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping(value = "/{auctionId}/{lotId}/makeBid")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Auction> getAllAuctions(@PathVariable int auctionId, @PathVariable int lotId ) {
        return auctionService.getAllAuctions();
    }
}
