package repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import auction.Application;
import auction.domain.Bid;
import auction.repository.BidRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class BidReporitoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BidRepository repository;

    @Test
    public void should_find_bids_if_repository_is_not_empty() {
        entityManager.persist(new Bid());
        entityManager.flush();
        List<Bid> bids = repository.findAll();
        assertThat(bids).isNotEmpty();
    }

    @Test
    public void should_delete_all_bids() {
        entityManager.persist(new Bid());
        entityManager.persist(new Bid());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_bids() {
        Bid bidOne = new Bid();
        entityManager.persist(bidOne);

        Bid bidTwo = new Bid();
        entityManager.persist(bidTwo);

        Bid bidThree = new Bid();
        entityManager.persist(bidThree);

        List<Bid> bids = repository.findAll();
        assertThat(bids).hasSize(3).contains(bidOne, bidTwo, bidThree);
    }

    @Test
    public void should_find_bid_by_id() {
        Bid bidOne = new Bid();
        entityManager.persist(bidOne);

        Bid bidTwo = new Bid();
        entityManager.persist(bidTwo);

        Bid foundBid = repository.findOne(bidTwo.getId());
        assertThat(foundBid).isEqualTo(bidTwo);
    }
}
