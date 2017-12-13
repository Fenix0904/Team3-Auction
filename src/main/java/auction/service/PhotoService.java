package auction.service;

import auction.domain.Lot;
import auction.domain.Photo;

import java.util.List;

public interface PhotoService {
    void addPhotos(List<Photo> photos, Lot lot);
}
