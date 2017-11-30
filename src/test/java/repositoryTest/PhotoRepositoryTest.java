package repositoryTest;

import auction.Application;
import auction.domain.Photo;
import auction.repository.PhotoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class PhotoRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PhotoRepository repository;

    @Test
    public void should_find_photos_if_repository_is_not_empty() {
        entityManager.persist(new Photo());
        entityManager.flush();
        List<Photo> photos = repository.findAll();
        assertThat(photos).isNotEmpty();
    }

    @Test
    public void should_delete_all_photos() {
        entityManager.persist(new Photo());
        entityManager.persist(new Photo());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_photos() {
        Photo photoOne = new Photo();
        entityManager.persist(photoOne);

        Photo photoTwo = new Photo();
        entityManager.persist(photoTwo);

        Photo photoThree = new Photo();
        entityManager.persist(photoThree);

        List<Photo> photos = repository.findAll();
        assertThat(photos).hasSize(3).contains(photoOne, photoTwo, photoThree);
    }

    @Test
    public void should_find_photo_by_id() {
        Photo photoOne = new Photo();
        entityManager.persist(photoOne);

        Photo photoTwo = new Photo();
        entityManager.persist(photoTwo);

        Photo foundPhoto = repository.findOne(photoTwo.getId());
        assertThat(foundPhoto).isEqualTo(photoTwo);
    }
}
