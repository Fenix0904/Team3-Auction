package auction.dto;

import auction.domain.Auction;
import auction.domain.Category;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionDTO {

    private int id;

    private String title;

    private Auction.Status auctionStatus;

    private ZonedDateTime startDate;

    private ZonedDateTime terminationDate;

    private UserDTO trader;

    private Category category;

    private List<LotDTO> lots;

    private List<UserDTO> subscribers;

    public static AuctionDTO fromModel(Auction auction) {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auction.getId());
        auctionDTO.setTitle(auction.getTitle());
        auctionDTO.setAuctionStatus(auction.getAuctionStatus());
        auctionDTO.setStartDate(auction.getStartDate());
        auctionDTO.setTerminationDate(auction.getTerminationDate());
        auctionDTO.setTrader(UserDTO.fromModel(auction.getTrader()));
        auctionDTO.setCategory(auction.getCategory());
        auctionDTO.setLots(LotDTO.fromModel(auction.getLots()));
        auctionDTO.setSubscribers(UserDTO.fromModel(auction.getSubscribers()));

        return auctionDTO;
    }

    public static List<AuctionDTO> fromModel(List<Auction> auctions) {
        List<AuctionDTO> auctionDTOS = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionDTOS.add(AuctionDTO.fromModel(auction));
        }
        return auctionDTOS;
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

    public UserDTO getTrader() {
        return trader;
    }

    public void setTrader(UserDTO trader) {
        this.trader = trader;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<LotDTO> getLots() {
        return lots;
    }

    public void setLots(List<LotDTO> lots) {
        this.lots = lots;
    }

    public List<UserDTO> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<UserDTO> subscribers) {
        this.subscribers = subscribers;
    }
}
