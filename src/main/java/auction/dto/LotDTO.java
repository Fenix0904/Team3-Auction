package auction.dto;

import auction.domain.Lot;
import auction.dto.shortdto.AuctionShortDTO;

import java.util.ArrayList;
import java.util.List;

public class LotDTO {

    private int id;

    private String title;

    private int initialPrice;

    private int currentPrice;

    private int lotQuantity;

    private int bidStep;

    private String description;

    private List<PhotoDTO> photos;

    private List<BidDTO> bids;

    public static LotDTO fromModel(Lot lot) {
        LotDTO lotDTO = new LotDTO();
        lotDTO.setId(lot.getId());
        lotDTO.setTitle(lot.getTitle());
        lotDTO.setInitialPrice(lot.getInitialPrice());
        lotDTO.setCurrentPrice(lot.getCurrentPrice());
        lotDTO.setLotQuantity(lot.getLotQuantity());
        lotDTO.setBidStep(lot.getBidStep());
        lotDTO.setDescription(lot.getDescription());
        lotDTO.setPhotos(PhotoDTO.fromModel(lot.getPhotos()));
        lotDTO.setBids(BidDTO.fromModel(lot.getBids()));
        return lotDTO;
    }

    public static List<LotDTO> fromModel(List<Lot> lots) {
        List<LotDTO> lotDTOS = new ArrayList<>();
        for (Lot lot : lots) {
            lotDTOS.add(LotDTO.fromModel(lot));
        }
        return lotDTOS;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getLotQuantity() {
        return lotQuantity;
    }

    public void setLotQuantity(int lotQuantity) {
        this.lotQuantity = lotQuantity;
    }

    public int getBidStep() {
        return bidStep;
    }

    public void setBidStep(int bidStep) {
        this.bidStep = bidStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }

    public List<BidDTO> getBids() {
        return bids;
    }

    public void setBids(List<BidDTO> bids) {
        this.bids = bids;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }
}
