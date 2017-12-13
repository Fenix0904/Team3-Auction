package auction.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name = "auction")
public class Auction {

    public enum Status {
        PLANNED, RUNNING, CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private Status auctionStatus;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "termination_date")
    private ZonedDateTime terminationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User trader;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "auction")
    private List<Lot> lots;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auction_subscribers",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> subscribers;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(Status auctionStatus) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public User getTrader() {
        return trader;
    }

    public void setTrader(User trader) {
        this.trader = trader;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }
}
