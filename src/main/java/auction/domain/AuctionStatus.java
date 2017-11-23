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

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionStatus status = (AuctionStatus) o;

        return name.equals(status.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
