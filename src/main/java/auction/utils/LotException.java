package auction.utils;

import auction.domain.Lot;

public class LotException extends Throwable {

    private final ErrorCode errorCode;
    private Lot lot;

    public LotException(ErrorCode errorCode, Lot lot) {
        super();
        this.lot = lot;
        this.errorCode = errorCode;
    }

    public Lot getLot() {
        return lot;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public enum ErrorCode {
        NO_ITEM, ANOTHER_CURRENT_PRICE
    }
}
