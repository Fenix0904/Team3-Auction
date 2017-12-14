package auction.dto;

import auction.domain.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoDTO {
    private String url;

    public static List<PhotoDTO> fromModel(List<Photo> photos) {
        List<PhotoDTO> photoDTOS = new ArrayList<>();
            for (Photo photo : photos) {
                PhotoDTO temp = new PhotoDTO();
                temp.setUrl(photo.getUrl());
                photoDTOS.add(temp);
            }
        return photoDTOS;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
