package auction.controller;

import auction.domain.Bid;
import auction.domain.Lot;
import auction.domain.User;
import auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bid")
public class BidController {

    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping(value = "/makeBid")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void makeBid(@RequestBody Bid bid, @RequestBody User user, @RequestBody Lot lot) {
        bidService.makeBid(bid, user, lot);
    }
}
