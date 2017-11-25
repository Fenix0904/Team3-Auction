package auction.dto;

import auction.domain.Bid;
import auction.dto.shortdto.UserShortDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BidDTO {

    private int id;

    private int bidValue;

    private UserShortDTO user;

    private Date bidTime;

    public static List<BidDTO> fromModel(List<Bid> bids) {
        List<BidDTO> bidDTOS = new ArrayList<>();
        for (BidDTO bidDTO : bidDTOS) {
            for (Bid bid : bids) {
                bidDTO.setId(bid.getId());
                bidDTO.setBidValue(bid.getBidValue());
                bidDTO.setUser(UserShortDTO.fromMode(bid.getUser()));
                bidDTO.setBidTime(bid.getBidTime());
            }
        }
        return bidDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBidValue() {
        return bidValue;
    }

    public void setBidValue(int bidValue) {
        this.bidValue = bidValue;
    }

    public UserShortDTO getUser() {
        return user;
    }

    public void setUser(UserShortDTO user) {
        this.user = user;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }
}
