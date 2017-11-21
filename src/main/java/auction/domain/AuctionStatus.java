package auction.domain;

import javax.persistence.*;

/**
 * Auction can be in next states: Planned, Open, Closed.
 * Transition between states: Planned --> Open --> Closed.
 * When in Closed state, Auction cannot be transited into any other state.
 */

@Entity(name = "status")
public class AuctionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public String getStatusName() {
        return name;
    }
}
