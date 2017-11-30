package repositoryTest;

import auction.Application;
import auction.domain.AuctionStatus;
import auction.repository.AuctionStatusRepository;
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
public class AuctionStatusRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AuctionStatusRepository repository;

    @Test
    public void should_find_auction_status_if_repository_is_not_empty() {
        entityManager.persist(new AuctionStatus());
        entityManager.flush();
        List<AuctionStatus> auctionStatus = repository.findAll();
        assertThat(auctionStatus).isNotEmpty();
    }

    @Test
    public void should_delete_all_auction_statuses() {
        entityManager.persist(new AuctionStatus());
        entityManager.persist(new AuctionStatus());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_auction_status_by_id() {
        AuctionStatus auctionStatusOne = new AuctionStatus();
        entityManager.persist(auctionStatusOne);

        AuctionStatus auctionStatusTwo = new AuctionStatus();
        entityManager.persist(auctionStatusTwo);

        AuctionStatus foundAuctionStatus = repository.findOne(auctionStatusTwo.getId());
        assertThat(foundAuctionStatus).isEqualTo(auctionStatusTwo);
    }
}
