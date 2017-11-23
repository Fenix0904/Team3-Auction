package auction.domain;

import javax.persistence.*;
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
    private Date bidTime;

    public int getId() { return id; }

    public int getBidValue() { return bidValue; }

    public void setBidValue(int bidValue) { this.bidValue = bidValue; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Lot getLot() { return lot; }

    public void setLot(Lot lot) { this.lot = lot; }

    public Date getBidTime() { return bidTime; }

    public void setBidTime(Date bidTime) { this.bidTime = bidTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (bidValue != bid.bidValue) return false;
        return bidTime != null ? bidTime.equals(bid.bidTime) : bid.bidTime == null;
    }

    @Override
    public int hashCode() {
        int result = bidValue;
        result = 31 * result + (bidTime != null ? bidTime.hashCode() : 0);
        return result;
    }
}
