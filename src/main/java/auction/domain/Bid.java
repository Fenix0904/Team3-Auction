package auction.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bid_value")
    int bidValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    Lot lot;

    @Column(name = "bid_time")
    Date bidTime;

    public int getId() { return id; }

    public int getBidValue() { return bidValue; }

    public void setBidValue(int bidValue) { this.bidValue = bidValue; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Lot getLot() { return lot; }

    public void setLot(Lot lot) { this.lot = lot; }

    public Date getBidTime() { return bidTime; }

    public void setBidTime(Date bidTime) { this.bidTime = bidTime; }
}
