package auction.service;

import auction.domain.Photo;
import auction.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PhotoServiceImpl implements PhotoService {

    PhotoRepository photoRepository;

    @Autowired
    PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }
    @Override
    public void createPhoto(Photo photo) {
        photoRepository.save(photo);
    }
}
