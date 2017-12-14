package auction.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bid_value")
    private int bidValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(name = "bid_time")
    private ZonedDateTime bidTime;

    public int getId() { return id; }

    public int getBidValue() { return bidValue; }

    public void setBidValue(int bidValue) { this.bidValue = bidValue; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Lot getLot() { return lot; }

    public void setLot(Lot lot) { this.lot = lot; }

    public ZonedDateTime getBidTime() { return bidTime; }

    public void setBidTime(ZonedDateTime bidTime) { this.bidTime = bidTime; }
}
