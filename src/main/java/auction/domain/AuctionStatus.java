package auction.domain;

import javax.persistence.*;

/**
 * Auction can be in next states: Planned, Open, Closed.
 * Transition between states: Planned --> Open --> Closed.
 * When in Closed state, Auction cannot be transited into any other state.
 */

@Entity(name = "status")
public class AuctionStatus {

    public enum Status {
        PLANNED, RUNNING, CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column
    private Status status;

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
