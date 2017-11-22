package auction.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "initial_price")
    private int initialPrice;

    @Column(name = "lot_quantity")
    private int lotQuantity;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToMany(mappedBy = "lot")
    private List<Photo> photos;

    @OneToMany(mappedBy = "lot")
    private List<Bid> bids;

    public int getId() { return id; }

    public int getInitialPrice() { return initialPrice; }

    public void setInitialPrice(int initialPrice) { this.initialPrice = initialPrice; }

    public int getLotQuantity() { return lotQuantity; }

    public void setLotQuantity(int lotQuantity) { this.lotQuantity = lotQuantity; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Photo> getPhotos() { return photos; }

    public void setPhotos(List<Photo> photos) { this.photos = photos; }

    public Auction getAuction() { return auction; }

    public void setAuction(Auction auction) { this.auction = auction; }

    public List<Bid> getBids() { return bids; }

    public void setBids(List<Bid> bids) { this.bids = bids; }
}
