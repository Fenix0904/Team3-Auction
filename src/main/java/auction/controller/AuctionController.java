package auction.controller;

import auction.domain.Auction;
import auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuctionController {


    private final UserService userService;

    @Autowired
    public AuctionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public void createAuction(@RequestBody Auction auction) {
        userService.createAuction(auction);
    }

    @GetMapping(value = "/all")
    public List<Auction> getAllAuctions() {
        return userService.getAllAuctions();
    }
}
