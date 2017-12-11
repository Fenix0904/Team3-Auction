package auction.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "initial_price")
    private int initialPrice;

    @Column(name = "current_price")
    private int currentPrice;

    @Column(name = "lot_quantity")
    private int lotQuantity;

    @Column(name = "bid_step")
    private int bidStep;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToMany(mappedBy = "lot")
    private List<Photo> photos;

    @OneToMany(mappedBy = "lot")
    private List<Bid> bids;

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInitialPrice() { return initialPrice; }

    public void setInitialPrice(int initialPrice) { this.initialPrice = initialPrice; }

    public int getLotQuantity() { return lotQuantity; }

    public void setLotQuantity(int lotQuantity) { this.lotQuantity = lotQuantity; }

    public int getBidStep() {
        return bidStep;
    }

    public void setBidStep(int bidStep) {
        this.bidStep = bidStep;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Photo> getPhotos() { return photos; }

    public void setPhotos(List<Photo> photos) { this.photos = photos; }

    public Auction getAuction() { return auction; }

    public void setAuction(Auction auction) { this.auction = auction; }

    public List<Bid> getBids() { return bids; }

    public void setBids(List<Bid> bids) { this.bids = bids; }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lot lot = (Lot) o;

        if (initialPrice != lot.initialPrice) return false;
        if (lotQuantity != lot.lotQuantity) return false;
        if (description != null ? !description.equals(lot.description) : lot.description != null) return false;
        if (photos != null ? !photos.equals(lot.photos) : lot.photos != null) return false;
        return bids != null ? bids.equals(lot.bids) : lot.bids == null;
    }

    @Override
    public int hashCode() {
        int result = initialPrice;
        result = 31 * result + lotQuantity;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + (bids != null ? bids.hashCode() : 0);
        return result;
    }
}
