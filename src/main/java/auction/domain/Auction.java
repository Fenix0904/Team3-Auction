package auction.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity(name = "auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private AuctionStatus auctionStatus;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "termination_date")
    private Date terminationDate;

    @Column(name = "bid_step")
    private int bidStep;

    /**
     * Id of user, who created auction.
     */
    @OneToOne(targetEntity = User.class)
    private int user;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "auction")
    private List<Lot> lots;

    public int getId() {
        return id;
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

    public int getBidStep() {
        return bidStep;
    }

    public void setBidStep(int bidStep) {
        this.bidStep = bidStep;
    }

    public int getTraiderId() {
        return user;
    }

    public void setTraiderId(int traiderId) {
        this.user = traiderId;
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
}
