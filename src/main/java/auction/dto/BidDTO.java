package auction.dto;

import auction.domain.Bid;
import auction.dto.shortdto.UserShortDTO;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class BidDTO {

    private int id;

    private int bidValue;

    private UserShortDTO user;

    private ZonedDateTime bidTime;

    public static List<BidDTO> fromModel(List<Bid> bids) {
        List<BidDTO> bidDTOS = new ArrayList<>();
            for (Bid bid : bids) {
                BidDTO temp = new BidDTO();
                temp.setId(bid.getId());
                temp.setBidValue(bid.getBidValue());
                temp.setUser(UserShortDTO.fromMode(bid.getUser()));
                temp.setBidTime(bid.getBidTime());
                bidDTOS.add(temp);
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

    public ZonedDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(ZonedDateTime bidTime) {
        this.bidTime = bidTime;
    }
}
