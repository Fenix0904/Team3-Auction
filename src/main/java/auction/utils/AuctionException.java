package auction.utils;

import auction.domain.Auction;

public class AuctionException extends Throwable {

    private final ErrorCode errorCode;
    private Auction auction;

    public AuctionException(ErrorCode errorCode, Auction auction) {
        this.errorCode = errorCode;
        this.auction = auction;
    }

    public Auction getAuction() {
        return auction;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public enum ErrorCode {
        AUCTION_IS_CLOSED
    }
}
