package repositoryTest;


import auction.Application;
import auction.domain.Lot;
import auction.repository.LotRepository;
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
public class LotRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LotRepository repository;

    @Test
    public void should_find_lots_if_repository_is_not_empty() {
        entityManager.persist(new Lot());
        entityManager.flush();
        List<Lot> lots = repository.findAll();
        assertThat(lots).isNotEmpty();
    }

    @Test
    public void should_delete_all_lots() {
        entityManager.persist(new Lot());
        entityManager.persist(new Lot());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_lots() {
        Lot lotOne = new Lot();
        entityManager.persist(lotOne);

        Lot lotTwo = new Lot();
        entityManager.persist(lotTwo);

        Lot lotThree = new Lot();
        entityManager.persist(lotThree);

        List<Lot> lots = repository.findAll();
        assertThat(lots).hasSize(3).contains(lotOne, lotTwo, lotThree);
    }

    @Test
    public void should_find_lot_by_id() {
        Lot lotOne = new Lot();
        entityManager.persist(lotOne);

        Lot lotTwo = new Lot();
        entityManager.persist(lotTwo);

        Lot foundLot = repository.findOne(lotTwo.getId());
        assertThat(foundLot).isEqualTo(lotTwo);
    }
}
