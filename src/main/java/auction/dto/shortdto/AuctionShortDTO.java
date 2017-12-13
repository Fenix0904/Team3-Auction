package auction.dto.shortdto;

import auction.domain.Auction;
import auction.domain.Category;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionShortDTO {

    private int id;

    private String title;

    private Auction.Status auctionStatus;

    private ZonedDateTime startDate;

    private ZonedDateTime terminationDate;

    private UserShortDTO trader;

    private Category category;

    public static AuctionShortDTO fromModel(Auction auction) {
        AuctionShortDTO auctionShortDTO = new AuctionShortDTO();
        auctionShortDTO.setId(auction.getId());
        auctionShortDTO.setTitle(auction.getTitle());
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Auction.Status getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(Auction.Status auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(ZonedDateTime terminationDate) {
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
