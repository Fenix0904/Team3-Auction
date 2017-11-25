package auction.dto.shortdto;

import auction.domain.Auction;
import auction.domain.AuctionStatus;
import auction.domain.Category;

import java.util.*;

public class AuctionShortDTO {

    private int id;

    private AuctionStatus auctionStatus;

    private Date startDate;

    private Date terminationDate;

    private UserShortDTO trader;

    private Category category;

    public static AuctionShortDTO fromModel(Auction auction) {
        AuctionShortDTO auctionShortDTO = new AuctionShortDTO();
        auctionShortDTO.setId(auction.getId());
        auctionShortDTO.setAuctionStatus(auction.getAuctionStatus());
        auctionShortDTO.setStartDate(auction.getStartDate());
        auctionShortDTO.setTerminationDate(auction.getTerminationDate());
        auctionShortDTO.setTrader(UserShortDTO.fromMode(auction.getTrader()));
        auctionShortDTO.setCategory(auction.getCategory());
        return auctionShortDTO;
    }

    public static List<AuctionShortDTO> fromModel(List<Auction> auctions) {
        List<AuctionShortDTO> auctionShortDTOS = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionShortDTOS.add(AuctionShortDTO.fromModel(auction));
        }
        return auctionShortDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public UserShortDTO getTrader() {
        return trader;
    }

    public void setTrader(UserShortDTO trader) {
        this.trader = trader;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
